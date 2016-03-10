package com.example.kayuho.coen390.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Kai on 3/10/2016.
 */
public class LatitudeLongtitude extends ArrayList<LatLng> implements Parcelable {

    public LatitudeLongtitude(){
        super();
    }

    protected LatitudeLongtitude(Parcel in){
        in.readList(this, LatLng.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    }

    public static final Parcelable.Creator<LatitudeLongtitude> CREATOR = new Parcelable.Creator<LatitudeLongtitude>(){
        public LatitudeLongtitude createFromParcel(Parcel in){
            return new LatitudeLongtitude(in);
        }

        public LatitudeLongtitude[] newArray(int size){
            return new LatitudeLongtitude[size];
        }
    };
}
