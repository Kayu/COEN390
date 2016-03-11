package com.example.kayuho.coen390.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kayuho.coen390.Model.Direction;
import com.example.kayuho.coen390.Model.GetDirection;
import com.example.kayuho.coen390.Model.UrlString;
import com.example.kayuho.coen390.Model.UrlString;
import com.example.kayuho.coen390.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> markerPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bunble = intent.getBundleExtra("bundle");
        markerPoints = (ArrayList<LatLng>)bunble.getSerializable("LatLng");
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ListView directions = (ListView)findViewById(R.id.DirectionListView);
        ArrayList<String> directionsList = new ArrayList<String>();

        //Replace this with directions to display in list view
        directionsList.add(0,"Hello");
        directionsList.add(1,"World");
        directionsList.add(2,"How");
        directionsList.add(3,"Are");
        directionsList.add(4,"You");
        directionsList.add(5,"Today");

        ArrayAdapter<String> directionsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,directionsList);
        directions.setAdapter(directionsAdapter);
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

        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
        Polyline polylineToAdd = mMap.addPolyline(new PolylineOptions().addAll(markerPoints).width(3).color(Color.RED));
        LatLng begin = markerPoints.get(0);
        LatLng end = markerPoints.get(markerPoints.size()-1);
        LatLng middle = markerPoints.get(markerPoints.size()/2);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.addMarker(new MarkerOptions().position(begin).title("Start"));
        mMap.addMarker(new MarkerOptions().position(end).title("Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(middle,11));

    }

    public void CalculatePosition(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();


    }

}
