package com.example.kayuho.coen390.Service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by kayuho on 2016-04-09.
 */
public class TaxiCaller {

    public static void makeCall(Context context){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:15146272011"));
        try {
            context.startActivity(callIntent);
        }
        catch(SecurityException e){
            e.printStackTrace();
        }
    }

}
