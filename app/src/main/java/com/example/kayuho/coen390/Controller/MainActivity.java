package com.example.kayuho.coen390.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kayuho.coen390.Model.Direction;
import com.example.kayuho.coen390.Model.ApiRequest;

import com.example.kayuho.coen390.Service.AddressDBHelper;
import com.example.kayuho.coen390.Service.GPSListener;

import com.example.kayuho.coen390.Model.UrlStringBuilder;
import com.example.kayuho.coen390.R;
import com.example.kayuho.coen390.Service.TaxiCaller;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //Testing
    Button DatabaseTest;  //Testing the database
    Double latitude,longitude;
    private LocationManager locationManager;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;
    private GPSListener gpsListener;
    Button btn_getHome;
    Button btn_callTaxi;


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
        String fontPath = "fonts/Capture_it.ttf";

        TextView getHomeTV = (TextView) findViewById(R.id.GetHomeButton);
        TextView callTaxiTV = (TextView) findViewById(R.id.TaxiButton);
        Typeface tf = Typeface.createFromAsset(getAssets(),fontPath);
        getHomeTV.setTypeface(tf);
        callTaxiTV.setTypeface(tf);
        btn_getHome = (Button) findViewById(R.id.GetHomeButton);
        getHome();



        btn_callTaxi = (Button) this.findViewById(R.id.TaxiButton);
        callTaxi();

    }

    private void getHome(){
        final boolean gpsPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        btn_getHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gpsListener = new GPSListener(MainActivity.this, gpsPermission);
                if (gpsListener.ableToAccessLocation()) {
                    latitude = gpsListener.getLatitude();
                    longitude = gpsListener.getLongitude();
                } else {
                    gpsListener.showAlert();
                }

                //Get Home Address From DB
                AddressDBHelper getAddressDB = new AddressDBHelper(MainActivity.this);
                Cursor getAddressCursor = getAddressDB.getAddress();
                Direction direction;

                //If an Address is in the DB
                if (getAddressCursor.moveToFirst()) {
                    //Create Arrival String
                    String arrival = getAddressCursor.getString(0);
                    arrival = arrival.replace(" ", "");
                    //arrival = "1087Duguay";
                    String depart = latitude.toString() + "," + longitude.toString();

                    UrlStringBuilder url = new UrlStringBuilder(MainActivity.this, depart, arrival);
                    ApiRequest directionResult = new ApiRequest(MainActivity.this);

                    try {
                        directionResult.execute(url.makeDirectionsURL("transit")).get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    direction = directionResult.getDirection();

                    Bundle mBundle = new Bundle();
                    mBundle.putParcelable("transit", direction);
                    //intent.putExtra("bundle", mBundle);

                    //ApiRequest getWalkingDirection = new ApiRequest((MainActivity.this));
                    directionResult = new ApiRequest(MainActivity.this);
                    try {
                        directionResult.execute(url.makeDirectionsURL("walking")).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    direction = directionResult.getDirection();

                    //Bundle wBundle = new Bundle();
                    mBundle.putParcelable("walking", direction);
                    Intent intent = new Intent(MainActivity.this, TransitOptionsActivity.class);
                    intent.putExtra("bundle", mBundle);
                    startActivity(intent);
                }
                //If no Address is in DB
                else
                    Toast.makeText(MainActivity.this, "PLEASE ADD ADDRESS IN SETTINGS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callTaxi(){
        btn_callTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    new TaxiCaller().makeCall(MainActivity.this);

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                }
            }

        });
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new TaxiCaller().makeCall(MainActivity.this);
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
*/
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

