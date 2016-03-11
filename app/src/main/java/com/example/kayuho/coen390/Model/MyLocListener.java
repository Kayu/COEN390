package com.example.kayuho.coen390.Model;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by zhou on 2016/3/9.
 */
public class MyLocListener implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {
        if (location != null)
        {

            Double longitude = location.getLongitude();
            Double latitude = location.getLatitude();

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
