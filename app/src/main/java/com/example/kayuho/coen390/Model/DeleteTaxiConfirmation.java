package com.example.kayuho.coen390.Model;

/**
 * Created by Alexei on 2016-04-09.
 */


import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;

import android.util.AttributeSet;
import android.widget.Toast;

import com.example.kayuho.coen390.Service.TaxiDBHelper;


public class DeleteTaxiConfirmation extends DialogPreference {
    private TaxiDBHelper db;
    private Context mContext;


    public DeleteTaxiConfirmation(Context context, AttributeSet attrs) {

        super(context, attrs);
        db = new TaxiDBHelper(context);
        this.setDefaultValue("Delete stored Taxi #");
        mContext = context;
    }

    /*
        @Override
        protected void onDialogClosed(boolean positive) {
            super.onDialogClosed(positive);
            if (callChangeListener(positive)) {
                db.deleteAll_address();
            }
        }
    */
    public void onClick(DialogInterface dialog, int option) {
        if (option == DialogInterface.BUTTON_POSITIVE) {
            db.deleteAll_taxi();
            Toast.makeText(mContext,"Taxi number removed",Toast.LENGTH_LONG).show();
        }

        super.onClick(dialog, option);
    }
}
