package com.example.myfoodapp_v4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    public ArrayList<RestaurantInfo> data;
   // public TextView Name;
    public String[] rName= new String[10];
    public String[] rPhone= new String[10];
    public String[] rRating= new String[10];
    public String[] rAddress= new String[10];
    public String[] rDistance= new String[10];
    RecyclerView recycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
       // Name= findViewById(R.id.food_name);
        recycle = findViewById(R.id.recycleview);

        data = City.getInfo();
        RestaurantInfo d;

        for(RestaurantInfo i : data){
            System.out.println(i);

        }

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

                //Not sure why this is in the loop?


            }
        }else{
            Toast.makeText(DisplayActivity.this, "error",Toast.LENGTH_SHORT).show();

            // Name.setText("error");
        }
        MyAdapter adapter = new MyAdapter(this, rName, rPhone, rAddress, rDistance, rRating);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));


    }
}