package com.example.kayuho.coen390.Model;

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




    public void onCreate(DialogInterface dialog, int option) {


            Cursor getListCursor = db.getAllData_contacts();

            ListPreference listPreferenceCategory = (ListPreference)findPreferenceInHierarchy("blocked_list");

            if (getListCursor.getCount() == 0)
            {
                Toast.makeText(mContext, "The blocked contacts list is currently empty", Toast.LENGTH_LONG).show();
                return;
            }


            if (getListCursor.getCount() < 5){
                int i = 0;
                CharSequence entries[] = new String[5];
                CharSequence entryValues[] = new String[5];
                while (getListCursor.moveToNext()){
                    entries[i] = getListCursor.getString(2);
                    entryValues[i] = Integer.toString(i);
                    i++;
                }

                listPreferenceCategory.setEntries(entries);
                listPreferenceCategory.setEntryValues(entryValues);

            }

        }


    }



