package com.example.kayuho.coen390.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;

import android.util.AttributeSet;
import android.widget.Toast;

import com.example.kayuho.coen390.Service.AddressDBHelper;

/**
 * Created by kayuho on 2016-03-27.
 */
public class DeleteAddressConfirmationPreference extends DialogPreference {
    private AddressDBHelper db;
    private Context mContext;


    public DeleteAddressConfirmationPreference(Context context, AttributeSet attrs) {

        super(context, attrs);
        db = new AddressDBHelper(context);
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
            Toast.makeText(mContext,"Address Deleted",Toast.LENGTH_LONG).show();
        }

        super.onClick(dialog, option);
    }
}
