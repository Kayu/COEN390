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


    public TaxiCaller(Context context) {
        mContext = context;
        db = new TaxiDBHelper(context);
    }

    public void makeCall(){

        db = new TaxiDBHelper(mContext);
        Cursor taxiCursor = db.getTaxiNum();
        String phone_num = null;
        if(taxiCursor.moveToFirst()) {
            phone_num = taxiCursor.getString(1);
        }
        else
        {
            phone_num = "#TAXI";
        }


        //int count = taxiCursor.getCount();

        String phone_num_test = "5143776583";

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:1" + phone_num));
        try {
            mContext.startActivity(callIntent);
        }
        catch(SecurityException e){
            e.printStackTrace();
        }
    }

}
