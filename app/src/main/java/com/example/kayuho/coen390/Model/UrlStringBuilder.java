package com.example.kayuho.coen390.Model;

import android.content.Context;

import com.example.kayuho.coen390.R;

import java.util.Date;

/**
 * Created by Kayu on 2016-03-07.
 */
public class UrlStringBuilder {
    private String from , to;
    private String googleMapKey;
    private StringBuilder url;
    public UrlStringBuilder(Context context, String from, String to){
        this.from = from;
        this.to = to;
        this.googleMapKey = context.getResources().getString(R.string.google_maps_key);

    }

    public String makeDirectionsURL(String transportType){
        url = new StringBuilder();
        url.append("https://maps.googleapis.com/maps/api/directions/json?");
        url.append("origin=");
        url.append(from);
        url.append("&destination=");
        url.append(to);
        url.append("&mode=");
        url.append(transportType);
        url.append("&key=");
        url.append(googleMapKey);

        return url.toString();
    }
}
