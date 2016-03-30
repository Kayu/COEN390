package com.example.kayuho.coen390.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.example.kayuho.coen390.Service.DbHelper;

/**
 * Created by Mathew on 28/03/2016.
 */
public class DeleteBlockedContactConfirmationPreference extends DialogPreference {

    private DbHelper db;


    public DeleteBlockedContactConfirmationPreference(Context context, AttributeSet attrs) {

        super(context, attrs);
        db = new DbHelper(context);
        this.setDefaultValue("Delete Blocked Contacts");
    }


    public void onClick(DialogInterface dialog, int option) {

                if (option == DialogInterface.BUTTON_POSITIVE) {
                    db.deleteAll_contacts();
                }

        super.onClick(dialog, option);
    }
}