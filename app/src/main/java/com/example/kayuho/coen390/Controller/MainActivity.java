package com.example.kayuho.coen390.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.kayuho.coen390.Model.Direction;
import com.example.kayuho.coen390.Model.JsonParser;

import com.example.kayuho.coen390.Service.MyLocListener;
import com.example.kayuho.coen390.Service.DbHelper;

import com.example.kayuho.coen390.Model.UrlString;
import com.example.kayuho.coen390.R;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //Testing
    Button DatabaseTest;  //Testing the database
    Double latitude,longitude;
    private LocationManager locationManager;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;
    private MyLocListener gpsListener;
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
        //openGPSSettings();
        //GPSServicelistner();
        final boolean gpsPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        Button btn_getHome = (Button) findViewById(R.id.GetHomeButton);
        btn_getHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gpsListener = new MyLocListener(MainActivity.this, gpsPermission);
                if (gpsListener.ableToAccessLocation()) {
                    latitude = gpsListener.getLatitude();
                    longitude = gpsListener.getLongitude();
                } else {
                    gpsListener.showAlert();
                }

                //Get Home Address From DB
                DbHelper getAddressDB = new DbHelper(MainActivity.this);
                Cursor getAddressCursor = getAddressDB.getAddress();

                //If an Address is in the DB
                if (getAddressCursor.moveToFirst()) {
                    //Create Arrival String
                    String arrival = getAddressCursor.getString(0);
                    arrival = arrival.replace(" ", "");
                    //arrival = "1087Duguay";
                    String depart = latitude.toString() + "," + longitude.toString();

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



        Button button = (Button) this.findViewById(R.id.TaxiButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                } else {
                    callPhone();
                }
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                }
                else{
                    Toast.makeText(MainActivity.this, "PHONE_CALL Denied", Toast.LENGTH_LONG).show();
                }
                break;
            }
            default:super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void callPhone()
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:15146272011"));
        try {
            startActivity(callIntent);
        }
        catch(SecurityException e){
            e.printStackTrace();
        }

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


}

