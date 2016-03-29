package com.example.kayuho.coen390.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

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
            //create an EditTextPreference object
            EditTextPreference pref_address = (EditTextPreference)findPreferenceInHierarchy("set_address");
            //Get the text from the edit text
            String address = pref_address.getText().toString();
            Log.i("address: ",address);
            //call the insert address method in the DbHelper
            boolean inserted = db.insert_address(address);
            //launches a message to the user if data was added or if already exists
            if(inserted){
                Toast.makeText(mContext, "Data Inserted", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(mContext, "You already have an address", Toast.LENGTH_LONG).show();

            }
        }

        super.onClick(dialog, option);
    }

}
