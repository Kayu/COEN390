package com.example.kayuho.coen390.Service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by ryanpoitras on 16-03-21.
 */
public class OutgoingCallReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //Get the phone number trying to be called
        String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Log.d(OutgoingCallReceiver.class.getSimpleName(), intent.toString());
        //Toast to signify outgoing message
        Toast.makeText(context, "OUTGOING!", Toast.LENGTH_SHORT).show();
        //Check if the number is in DB
        if (outgoingNumber.equals("5142918375")) {
            //Drop the call
            setResultData(null);
            //Show Toast to warn user why call was dropped
            Toast.makeText(context, "INTOXIC-AID: CONTACT ON BLOCK LIST", Toast.LENGTH_LONG).show();

        }


    }

    public boolean checkIfNeedBlocking(String outgoingNumber, Context context) {

        boolean result = false;
        String contactInDB;

        //Create DB Helper
        //Get all contact data from DB
        DbHelper getContacts = new DbHelper(context);
        Cursor dbEntries = getContacts.getAllData_contacts();

        //If move is successful
        if (dbEntries.moveToFirst()) {

            //Get data from "phone_num" column
            for (int i = 0; i < 4; i++) {

                //Access entry and convert to string
                contactInDB = dbEntries.getString(dbEntries.getColumnIndex("phone_num"));

                //Compare the outgoing number to DB contact numbers
                if (outgoingNumber.equals(contactInDB)) {
                    result = true;
                    break;
                }
                //Increment Cursor from DB
                //If the move returns "false"
                if (!(dbEntries.moveToNext())) {
                    //Break from loop since end of table
                    break;
                }


            }
        }

        return result;

    }
}
