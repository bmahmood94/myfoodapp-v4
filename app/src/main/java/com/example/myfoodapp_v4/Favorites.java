package com.example.myfoodapp_v4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.auth.FirebaseAuth;

public class Favorites extends AppCompatActivity {
    private Button city, home, logout;
    private Button search;
    private EditText user_input;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        city = findViewById(R.id.favorites_citybtn);

        search = findViewById(R.id.city_search_btn);

        home = findViewById(R.id.city_homebtn);
        logout = findViewById(R.id.city_logoutbtn);


        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_city = new Intent(Favorites.this, City.class);
                startActivity(open_city);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_favorites = new Intent(Favorites.this, MainActivity.class);
                startActivity(open_favorites);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}