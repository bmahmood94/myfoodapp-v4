package com.example.myfoodapp_v4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    public static RestaurantInfo d = new RestaurantInfo();
    public TextView Name;
    public String [] rName;
    public String [] rPhone;
    public String [] rRating;
    public String [] rAddress;
    public String [] rDistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Name= findViewById(R.id.food_name);

        if(d.getName()!=null) {
            for (int i = 0; i < 10; i++) {
                rName[i] = d.getName();
                rPhone[i] = d.getNumber();
                rRating[i] = d.getRating();
                rAddress[i] = d.getAddress();
                rDistance[i] = d.getDistance();
                Name.setText(rName[i]);

            }
        }else{
             Name.setText("error");
            }
        }
    }
