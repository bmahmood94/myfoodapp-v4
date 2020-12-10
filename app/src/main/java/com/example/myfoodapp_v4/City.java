package com.example.myfoodapp_v4;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class City extends AppCompatActivity implements OnSuccessListener<Location> {
    //global variables need to connect to other activities, toast messages, and getting location
    public static final int LOCATION_REQUEST_CODE = 11;
    private static final String TAG1 = City.class.getSimpleName();
    StringRequest stringRequest;
    RequestQueue requestQueue;
    //declaration of object of RestaurantInfo class to transfer data over to store
    public static ArrayList<RestaurantInfo> data = new ArrayList<RestaurantInfo>();
    private Context context;

    //yelp api_key
    public static final String ACCESS_TOKEN = "beKwCiGsiXFNcSneUc_Wz5WF2R-6q4GYVxWQzfMHWwtulKm4KojGk6I0OEtVMvVWxC6qmhkbJ_b87i8-R82e8kIt-qLyJk2wx_pIyoKGy1QFrv5xj-Mll5DZevvIX3Yx";
    public static final String TAG = "My Tag";
    //declaration of buttons and textviews and edit texts that connect to layout pages
    private Button gps, favorites, home, logout;
    private Button search;
    private EditText user_input;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        //local variables being connected to layout page
        context=this;
        favorites = findViewById(R.id.favorites_citybtn);


        search = findViewById(R.id.city_search_btn);

        gps = findViewById(R.id.gps_search_btn);
        home = findViewById(R.id.city_homebtn);
        logout = findViewById(R.id.city_logoutbtn);
        user_input = findViewById(R.id.user_input);
//if favorites button clicked transfer to favorites activity
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_favorites = new Intent(City.this, Favorites.class);
                startActivity(open_favorites);
            }
        });
        //if home is clicked go home
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_favorites = new Intent(City.this, MainActivity.class);
                startActivity(open_favorites);
            }
        });
        //logout button to logout of firebase
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
        //when you want to search via user input
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = user_input.getText().toString();
                getFoodbyCity(user);
            }
        });
        //search by gps
        gps.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                //debug point for developer to see where they are at
                Log.d(TAG1, "clicker1");
                //this checks if we get location
                if (ContextCompat.checkSelfPermission(City.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    //directly ask for the permission.
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                }
            }
        });

    }
    public static ArrayList<RestaurantInfo> getInfo()
    {
        return data;
    }

    public void getFoodbyCity(String x) {

        String user_in = x;//user input that is a string
        String url = "https://api.yelp.com/v3/businesses/search?term=food&location=";//url to connect to api
//api request call
        StringRequest request = new StringRequest(Request.Method.GET, url + user_in, new Response.Listener<String>() {
            @Override
            //if call successful return the string response
            public void onResponse(String response) {
                System.out.println(response);
                RestaurantInfo info;

                try {
                    JSONObject main = new JSONObject(response);
                    JSONArray businesses = main.getJSONArray("businesses");

                    //Loop through the businesses in the JSON Array
                    for(int i =0; i<businesses.length(); i++){
                        JSONObject object = businesses.getJSONObject(i);
                        JSONObject object2 = businesses.getJSONObject(i).getJSONObject("location");
                        String name = object.getString("name");
                        System.out.println(name);
                        String phone = object.getString("display_phone");
                        System.out.println(phone);
                        String rating = object.getString("rating");
                        System.out.println(rating);
                        String distance = object.getString("distance");
                        System.out.println(distance);
                        String location = object2.getString("address1");
                        System.out.println(location);
                        //String open_closed = object.getString("is_closed");

                        //Once we have the data, we will make a new RestaurantInfo
                        info = new RestaurantInfo();
                        info.setRating(object.getString("rating"));
                        info.setName(object.getString("name"));
                        info.setPhone(object.getString("phone"));
                        info.setDistance(object.getString("distance"));
                        info.setAddress(object.getString("location"));

                        //Then add it to the array List
                        data.add(info);

                    }
                    Intent intent = new Intent(context, DisplayActivity.class);
                    startActivity(intent);


                } catch (JSONException e) {
                    System.out.println("JSON EXPLOSION");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "bearer " + ACCESS_TOKEN);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        System.out.println("onRequestPermissionsResult Callback Entered");
        //Check that permission was granted
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getLocation();
        }


    }
    @SuppressLint("MissingPermission")
    private void getLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, this);


    }
    //onSuccess for Location Services
    public void onSuccess(Location location) {
        //Get Weather by Location
        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());
        System.out.println("Latitude = " + lat);
        System.out.println("Longitude = " + lon);
        Log.d(TAG1, lat + lon);


        getFoodByLocation(lat,lon);

    }
    public void getFoodByLocation(String lat, String lon){
        String latitude = lat;
        String longitude = lon;
        String url = "https://api.yelp.com/v3/businesses/search?latitude=";
        String url2="&longitude=";
        StringRequest request = new StringRequest(Request.Method.GET, url + latitude + url2 + longitude, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                RestaurantInfo info;

                try {
                    JSONObject main = new JSONObject(response);
                    JSONArray businesses= main.getJSONArray("businesses");

                    if(businesses.length()==0){
                        System.out.println("error");
                    }else{
                        for(int i =0; i<businesses.length(); i++) {
                            JSONObject object = businesses.getJSONObject(i);
                            JSONObject object2 = businesses.getJSONObject(i).getJSONObject("location");
                            String name = object.getString("name");
                            System.out.println(name);
                            String phone = object.getString("display_phone");
                            System.out.println(phone);
                            String rating = object.getString("rating");
                            System.out.println(rating);
                            String distance = object.getString("distance");
                            System.out.println(distance);
                            String location = object2.getString("address1");
                            System.out.println(location);
                            //String open_closed = object.getString("is_closed");

                            //Once we have the data, we will make a new RestaurantInfo
                            info = new RestaurantInfo();
                            info.setRating(object.getString("rating"));
                            info.setName(object.getString("name"));
                            info.setPhone(object.getString("phone"));
                            info.setDistance(object.getString("distance"));
                            info.setAddress(object.getString("location"));

                            //Then add it to the array List
                            data.add(info);



                        }
                        System.out.println(context);
                        System.out.println(this);
                        System.out.println(data);

                        Intent intent = new Intent(context, DisplayActivity.class);
                        startActivity(intent);
                    }



                } catch (JSONException e) {
                    System.out.println("JSON EXPLOSION");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            //This is for Headers If You Needed
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","bearer " + ACCESS_TOKEN);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}

