package com.example.myfoodapp_v4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    //instand of restaurantinfo to get data
    public ArrayList<RestaurantInfo> data;
    //variables that will be used to upload to firebase data
    ImageButton addto_favbtn;
    DatabaseReference dbref;
    FirebaseDatabase database;
    // public TextView Name;
    public Button map_butn;
    public String[] rName= new String[10];
    public String[] rPhone= new String[10];
    public String[] rRating= new String[10];
    public String[] rAddress= new String[10];
    public String[] rDistance= new String[10];
    public Double y, x=1609.344, z;

    RecyclerView recycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        //these connect to layout for input
        recycle = findViewById(R.id.recycleview);
        map_butn=findViewById(R.id.display_map);
        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("Favorites");//instance of firebase that should connect to the table in firebase
//declaration to connect to the variables/info object in city
        data = City.getInfo();
        RestaurantInfo d;


//this from line 61-82 it is the transfer of all data in restaurant info to the arrays here but also limiting the size of the array to 10 with the for loop
//the reason for the limit is just for developer purposes, below the transfer of array data is the trimming of the data so that way the address display looks nicer and the
//meters are converted to miles since we are in the USA.
        if(data.size() >= 10) {
            for (int i = 0; i < 10; i++) {
                //Set d to the current data index in the array list
                d = data.get(i);

                //Fill in the string arrays with the values
                rName[i] = d.getName();
                rPhone[i] = d.getNumber();
                rRating[i] = d.getRating();
                rAddress[i] = d.getAddress();
                rDistance[i] = d.getDistance();
                rAddress[i] = rAddress[i].replaceAll("\\[", "").replaceAll("\\]","");
                rAddress[i] = rAddress[i].replace("\"", "");
                rPhone[i] = rPhone[i].replace("+", "");

                y = Double.parseDouble(rDistance[i]);
                z=y/x;
                String s=Double.toString(z);
                s = s.substring(0, Math.min(s.length(), 4));
                rDistance[i]=s;

            }
        }else{
            Toast.makeText(DisplayActivity.this, "error",Toast.LENGTH_SHORT).show();

            // Name.setText("error");
        }
        //calls and sends to the adapter for display
        MyAdapter adapter = new MyAdapter(this, rName, rPhone, rAddress, rDistance, rRating);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));
//this button is to start the map activity so all the points/restaurants are displayed on the map
        map_butn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_map = new Intent(DisplayActivity.this, MapsActivity.class);
                startActivity(open_map);
            }
        });

    }
    @SuppressLint("WrongViewCast")
    public void favourtieChecker(String postkey)
    {
        RestaurantInfo data = new RestaurantInfo();
        addto_favbtn= findViewById(R.id.display_fav_btn);
        addto_favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbref.push().setValue(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
});
    }
}