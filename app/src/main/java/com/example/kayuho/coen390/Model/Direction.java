package com.example.kayuho.coen390.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;

/**
 * Created by Kai on 3/9/2016.
 */
public class Direction implements Parcelable {
    private ArrayList<LatLng> points;
    private ArrayList<String> directions;
    private String distance, duration;

    public Direction(Parcel parcel){
        this.points = parcel.readArrayList(LatLng.class.getClassLoader());
        this.directions = parcel.readArrayList(String.class.getClassLoader());
        this.duration = parcel.readString();
        this.distance = parcel.readString();
    }
    public Direction(ArrayList<LatLng> points,ArrayList<String>directions, String duration, String distance){
        this.points = points;
        this.directions = directions;
        this.distance = distance;
        this.duration = duration;
    }
    public ArrayList<LatLng> getPoints(){
        return points;
    }
    public ArrayList<String> getDirections(){return directions;}
    public String getDuration(){
        return duration;
    }
    public String getDistance(){
        return distance;
    }

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(points);
        dest.writeList(directions);
        dest.writeString(duration);
        dest.writeString(distance);
    }
    public static Creator<Direction> CREATOR = new Creator<Direction>() {
        @Override
        public Direction createFromParcel(Parcel source) {
            Direction mDirection = new Direction(source);
            return mDirection;
        }

        @Override
        public Direction[] newArray(int size) {
            return new Direction[size];
        }
    };
}
