package com.example.kayuho.coen390.Model;

import android.content.Context;

import com.example.kayuho.coen390.R;

import java.util.Date;

/**
 * Created by Kayu on 2016-03-07.
 */
public class Direction {
    private String from , to;
    private String googleMapKey;
    private Context currentContext;
    public Direction(Context context, String from, String to){
        this.currentContext = context;
        this.from = from;
        this.to = to;
        this.googleMapKey = currentContext.getResources().getString(R.string.google_maps_key);
    }

    public String makeDirectionsURL(String transportType){
        StringBuilder url = new StringBuilder();
        long epoch = (System.currentTimeMillis()/1000);
        url.append("https://maps.googleapis.com/maps/api/directions/json?");
        url.append("origin=" + from);
        url.append("&destination=" + to);

        if(transportType.equals("transit"))
            url.append("&departure_time=" + epoch);

        url.append("&mode=" + transportType);
        url.append("&key=" + googleMapKey);

        return url.toString();
    }


}
