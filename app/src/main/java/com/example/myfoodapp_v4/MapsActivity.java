package com.example.myfoodapp_v4;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public ArrayList<RestaurantInfo> data;
    ArrayList<LatLng> arrayList= new ArrayList<LatLng>();
    ArrayList<String> arrayname = new ArrayList<String>();

    LatLng sydney = new LatLng(-34, 151);
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        data = City.getInfo();
        RestaurantInfo d;

        if(data.size() >= 5) {
            for (int i = 0; i < 10; i++) {
                //Set d to the current data index in the array list
                d = data.get(i);
                arrayname.add(d.getName());
                String latl = d.getLat();
                String lonl = d.getLon();
                double latitude = Double.parseDouble(latl);
                double longitude = Double.parseDouble(lonl);
                LatLng location = new LatLng(latitude, longitude);
                arrayList.add(location);
                System.out.println(arrayList);
            }
        }else{
            Toast.makeText(MapsActivity.this, "error",Toast.LENGTH_SHORT).show();
            // Name.setText("error");
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(int i=0;i<arrayList.size();i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(arrayname.get(i)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
}