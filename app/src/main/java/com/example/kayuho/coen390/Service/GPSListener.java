package com.example.kayuho.coen390.Service;

import android.Manifest;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zhou on 2016/3/9.
 * fixed and modified to work by Ka Yu
 */
public class GPSListener extends Service implements LocationListener {

    private Context context;
    private boolean GPSEnabled = false;
    private boolean NetworkEnabled = false;
    private boolean accessLocation = false;

    private boolean hasPermission = false;

    private Location location;
    private double latitude;
    private double longitude;

    private static final long MIN_DISTANCE = 10;
    private static final long MIN_TIME_UPDATE = 1000 * 60 * 1;
    private LocationManager locationManager;

    public GPSListener(Context context, boolean gpsPermission){
        this.context = context;
        hasPermission = gpsPermission;
        getLocation();
    }

    public Location getLocation(){
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        NetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!hasPermission){
            Toast.makeText(context, "GPS permission required", Toast.LENGTH_LONG).show();
            return null;
        }

        if(!NetworkEnabled && !GPSEnabled){
            Toast.makeText(context, "GPS or Network not available", Toast.LENGTH_LONG).show();
            return null;
        }
        else
        {
            this.accessLocation = true;
            if(NetworkEnabled){
                try {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                catch(SecurityException e){
                    e.printStackTrace();
                }
            }

            if(GPSEnabled){
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                catch(SecurityException e){
                    e.printStackTrace();
                }
            }
        }

        return location;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public boolean ableToAccessLocation(){
        return accessLocation;
    }

    public void setPermission( boolean permission){
        hasPermission = permission;
    }

    public void showAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Enabled GPS?");
        alertDialog.setMessage("GPS not enabled. Do you wish to enable it now?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface settingDialog, int destination) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface settingDialog, int destination) {
                settingDialog.cancel();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}





