package com.example.kayuho.coen390.Controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.GpsSatellite;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.kayuho.coen390.Model.Direction;
import com.example.kayuho.coen390.Model.JsonParser;
import com.example.kayuho.coen390.Model.MyLocListener;
import com.example.kayuho.coen390.Service.DbHelper;
import com.example.kayuho.coen390.Service.OutgoingCallReceiver;
import com.example.kayuho.coen390.Model.UrlString;
import com.example.kayuho.coen390.R;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //Testing
    Button DatabaseTest;  //Testing the database
    Double lat,lon;
    private LocationManager locationManager;

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        openGPSSettings();
        GPSServicelistner();
        Button btn_test = (Button)findViewById(R.id.GetHomeButton);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Home Address From DB
                DbHelper getAddressDB = new DbHelper(MainActivity.this);
                Cursor getAddressCursor = getAddressDB.getAddress();

                //If an Address is in the DB
                if(getAddressCursor.moveToFirst()){
                    //Create Arrival String
                    String arrival = getAddressCursor.getString(0);
                    arrival = arrival.replace(" ","");
                    String depart = lat.toString() + "," + lon.toString();

                    UrlString url = new UrlString(MainActivity.this, depart, arrival);
                    JsonParser getTransitDirection = new JsonParser(MainActivity.this);
                    Direction transitDirection;
                    try {
                        getTransitDirection.execute(url.makeDirectionsURL("transit")).get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    transitDirection = getTransitDirection.getPoints();
                    Intent intent = new Intent(MainActivity.this, TransitOptionsActivity.class);

                    Bundle mBundle = new Bundle();
                    mBundle.putParcelable("transit", transitDirection);
                    intent.putExtra("bundle", mBundle);

                    JsonParser getWalkingDirection = new JsonParser((MainActivity.this));

                    Direction walkingDirection;
                    try {
                        getWalkingDirection.execute(url.makeDirectionsURL("walking")).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    walkingDirection = getWalkingDirection.getPoints();

                    Bundle wBundle = new Bundle();
                    wBundle.putParcelable("walking", walkingDirection);
                    intent.putExtra("walkBundle", wBundle);
                    startActivity(intent);
                }
                //If no Address is in DB
                else
                    Toast.makeText(MainActivity.this, "PLEASE ADD ADDRESS IN SETTINGS", Toast.LENGTH_LONG).show();


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // check if GPS is turned on

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is on", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Please open the GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent,0);
        //after setting


    }
//after setting

    protected void GPSServicelistner() {
        new MyLocListener();
        LocationManager myManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //Location location =  myManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        boolean test = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if ( test) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details

            Location location =  myManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            MyLocListener loc = new MyLocListener();
            updateWithNewLocation(location);
            myManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, new MyLocListener());


            lat = location.getLatitude();
            lon = location.getLongitude();

            Log.i("latitude", lat.toString());
            Log.i("longitude", lon.toString());



        }

    }

    private void updateWithNewLocation(Location location) {
    }
    public class MyLocListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null)
            {

                lon = location.getLongitude();
                lat = location.getLatitude();
                Log.i("latitude", lat.toString());
                Log.i("longitude", lon.toString());


            }
        }



        @Override
        public void onProviderEnabled(String provider){
        }

        @Override
        public void onProviderDisabled(String provider){
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){
        }


    }
}

