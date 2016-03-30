package com.example.kayuho.coen390.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;

import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import com.example.kayuho.coen390.Service.DbHelper;

/**
 * Created by kayuho on 2016-03-27.
 */
public class DeleteAddressConfirmationPreference extends DialogPreference {
    private DbHelper db;
    private Context mContext;


    public DeleteAddressConfirmationPreference(Context context, AttributeSet attrs) {

        super(context, attrs);
        db = new DbHelper(context);
        this.setDefaultValue("Delete stored address");
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
            db.deleteAll_address();
            Toast.makeText(mContext,"All Contacts Unblocked",Toast.LENGTH_LONG).show();
        }

        super.onClick(dialog, option);
    }
}
