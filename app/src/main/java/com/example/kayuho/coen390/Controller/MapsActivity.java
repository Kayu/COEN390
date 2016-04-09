package com.example.kayuho.coen390.Controller;

import com.google.android.gms.maps.OnMapReadyCallback;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;



import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kayuho.coen390.R;
import com.example.kayuho.coen390.Service.OutgoingCallReceiver;
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
    private ArrayList<String> directionsList;

    @Override
    protected void onPause() {
        super.onPause();
        OutgoingCallReceiver newCall = new OutgoingCallReceiver();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        markerPoints = (ArrayList<LatLng>)bundle.getSerializable("LatLng");
        directionsList = (ArrayList<String>)bundle.getSerializable("Directions");
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final ListView directions = (ListView)findViewById(R.id.DirectionListView);
        String fontPathButton = "fonts/ostrich-regular.ttf";
       final Typeface tf_Button = Typeface.createFromAsset(getAssets(),fontPathButton);

        //directionsTextView.setTypeface(tf_Button);
        //Replace this with directions to display in list view
        ArrayAdapter<String> directionsAdapter = new ArrayAdapter<String>(this,R.layout.directions_list_text_view,directionsList){
            @Override

            public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView directionsTextView = (TextView) view;
            directionsTextView.setTypeface(tf_Button);


            return view;
        }
        };
        //CustomListAdapter directionsAdapter = new CustomListAdapter(MapsActivity.this, R.id.DirectionListView,directionsList);
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
        Polyline polylineToAdd = mMap.addPolyline(new PolylineOptions().addAll(markerPoints).width(3).color(Color.RED));
        LatLng begin = markerPoints.get(0);
        LatLng end = markerPoints.get(markerPoints.size()-1);
        LatLng middle = markerPoints.get(markerPoints.size()/2);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.addMarker(new MarkerOptions().position(begin).title("Start"));
        mMap.addMarker(new MarkerOptions().position(end).title("Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(middle,11));

    }
}
