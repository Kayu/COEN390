package com.example.kayuho.coen390.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kayuho.coen390.Controller.MainActivity;
import com.example.kayuho.coen390.Service.DbHelper;

/**
 * Created by Kai on 3/27/2016.
 * This class extends from the basic EditTextPreference to access the Ok and Cancel button functionality
 *
 */
public class AddressEditTextPreference extends EditTextPreference {
    private DbHelper db;
    protected Context mContext;
    //This constructor instanciated the Dbhelper obejct and store the context
    public AddressEditTextPreference(Context context) {
        super(context);
        mContext = context;
        db = new DbHelper(context);
    }


    public AddressEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        db = new DbHelper(context);
    }

    public AddressEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        db = new DbHelper(context);
    }

    //This function is use when user clicks either the cancel button or the ok button for the edit text preference
    @Override
    public void onClick(DialogInterface dialog, int option) {
        // THis if statement checks the clicked button is OK or cancel
        //In this case it is checking to see if the Ok button was clicked
        if (option == DialogInterface.BUTTON_POSITIVE) {


            Cursor getAddressCursor = db.getAddress();

            //create an EditTextPreference object
            final EditTextPreference pref_address = (EditTextPreference) findPreferenceInHierarchy("set_address");
            //Create EditText Object to Get Text
            EditText getAddress = pref_address.getEditText();
            //GetText
            String address = getAddress.getText().toString();

            //If Address Already in DB
            if(getAddressCursor.moveToFirst()){

                //Delete Current Address From DB
                db.deleteAll_address();
                //Add New Address to DB
                db.insert_address(address);
                Log.i("address", address);


            }
            //If DB Address is Empty
            else{
                //Insert Address Into DB
                db.insert_address(address);
                Log.i("address", address);
            }

        }

        super.onClick(dialog, option);
    }

}


