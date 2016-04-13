package com.example.kayuho.coen390.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.widget.Toast;

import com.example.kayuho.coen390.Service.ContactsDBHelper;

/**
 * Created by Mathew on 10/04/2016.
 */

public class ListBlockedContactsTextPref extends ListPreference {

    private ContactsDBHelper db;
    protected Context mContext;

    public ListBlockedContactsTextPref(Context context){
        super(context);
        mContext = context;
        db = new ContactsDBHelper(context);
    }

    public ListBlockedContactsTextPref(Context context, AttributeSet attrs){
        super (context, attrs);
        mContext = context;
        db = new ContactsDBHelper(context);
    }



    @Override
    public void onPrepareDialogBuilder(AlertDialog.Builder builder ) {

            Cursor getListCursor = db.getAllData_contacts();

            StringBuilder placeHolder = new StringBuilder();
            if (getListCursor.getCount() == 0)
            {
                Toast.makeText(mContext, "The blocked contacts list is currently empty", Toast.LENGTH_LONG).show();
                return;
            }


            if (getListCursor.getCount() <= 5){
                int i = 0;

                while (getListCursor.moveToNext()){

                    placeHolder.append(Integer.toString(i+1)+". "+getListCursor.getString(2)+"\n");

                    i++;
                }

                builder.setMessage(placeHolder);
            }

        }
    }




