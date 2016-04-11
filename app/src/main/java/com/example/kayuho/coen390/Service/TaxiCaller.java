package com.example.kayuho.coen390.Service;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by kayuho on 2016-04-09.
 */
public class TaxiCaller {


    private TaxiDBHelper db;
    protected Context mContext;

    /*
    public TaxiCaller() {

        db = new TaxiDBHelper(mContext);
    }*/

    public void makeCall(Context context){


        db = new TaxiDBHelper(context);
        String phone_num;
        Cursor taxiCursor = db.getTaxiNum();


        //int count = taxiCursor.getCount();




        String phone_num_test = "5143776583";

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:1" + phone_num_test));
        try {
            context.startActivity(callIntent);
        }
        catch(SecurityException e){
            e.printStackTrace();
        }
    }

}
