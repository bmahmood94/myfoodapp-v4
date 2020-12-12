package com.example.myfoodapp_v4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    public ArrayList<RestaurantInfo> data;
    ImageButton addto_favbtn;
    DatabaseReference dbref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
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
       // Name= findViewById(R.id.food_name);
        recycle = findViewById(R.id.recycleview);
        map_butn=findViewById(R.id.display_map);

        data = City.getInfo();
        RestaurantInfo d;



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



                //Not sure why this is in the loop?


            }
        }else{
            Toast.makeText(DisplayActivity.this, "error",Toast.LENGTH_SHORT).show();

            // Name.setText("error");
        }
        MyAdapter adapter = new MyAdapter(this, rName, rPhone, rAddress, rDistance, rRating);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));

        map_butn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_map = new Intent(DisplayActivity.this, MapsActivity.class);
                startActivity(open_map);
            }
        });
    }
}