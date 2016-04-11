package com.example.kayuho.coen390.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kayuho.coen390.Service.AddressDBHelper;

/**
 * Created by Kai on 3/27/2016.
 * This class extends from the basic EditTextPreference to access the Ok and Cancel button functionality
 *
 */
public class AddressEditTextPreference extends EditTextPreference {
    private AddressDBHelper db;
    protected Context mContext;
    private String mFont = "Brandon_light.otf";

    //This constructor instanciated the Dbhelper obejct and store the context
    public AddressEditTextPreference(Context context) {
        super(context);
        mContext = context;
        db = new AddressDBHelper(context);
    }


    public AddressEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        db = new AddressDBHelper(context);
    }

    public AddressEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        db = new AddressDBHelper(context);
    }
    //test
    //This function is use when user clicks either the cancel button or the ok button for the edit text preference
    @Override
    public void onClick(DialogInterface dialog, int option) {
        // THis if statement checks the clicked button is OK or cancel
        //In this case it is checking to see if the Ok button was clicked
        if (option == DialogInterface.BUTTON_POSITIVE) {

            Cursor getAddressCursor = db.getAddress();


            //create an EditTextPreference object
            EditTextPreference pref_address = (EditTextPreference)findPreferenceInHierarchy("set_address");
            //Used to get text from text field, instead of the one stored in Preferences
            EditText textField = pref_address.getEditText();
            //Get the text from the edit text
            String address = textField.getText().toString();


            Log.i("address: ",address);

            if(getAddressCursor.moveToFirst()){
                db.deleteAll_address();
                db.insert_address(address);
                Toast.makeText(mContext, "Data Inserted", Toast.LENGTH_LONG).show();
            }
            else{
                db.insert_address(address);
            }

        }

        super.onClick(dialog, option);
    }

}
