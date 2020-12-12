package com.example.myfoodapp_v4;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.appbar_scrolling_view_behavior, R.string.appbar_scrolling_view_behavior);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "You are Home",Toast.LENGTH_SHORT).show();break;

                    case R.id.city:
                        Intent open_city= new Intent(MainActivity.this, City.class);
                        startActivity(open_city);
                        Toast.makeText(MainActivity.this, "Loading Search",Toast.LENGTH_SHORT).show();break;

                    case R.id.favorites:
                        Intent open_favorites = new Intent(MainActivity.this, Favorites.class);
                        startActivity(open_favorites);
                        Toast.makeText(MainActivity.this, "Loading Favorites",Toast.LENGTH_SHORT).show();break;
                    case R.id.map:
                        Intent open_map =new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(open_map);
                        Toast.makeText(MainActivity.this, "Loading Map based of user location",Toast.LENGTH_SHORT).show();break;

                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();

                    default:
                        return true;
                }


                return true;

            }
        });


    }
    public void getCity(){
        //EditText editText1 = (EditText) findViewById(R.id.city_in);
        //String text = editText1.getText().toString();
        // EditText editText2 = (EditText) findViewById(R.id.edittext2);
        //  int number = Integer.parseInt(editText2.getText().toString());
        //  Intent intent = new Intent(this, City.class);
        //  intent.putExtra(EXTRA_TEXT, text);
        //intent.putExtra(EXTRA_NUMBER, number);
        //   startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}