package com.example.kayuho.coen390.Model;

/**
 * Created by Alexei on 2016-04-09.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kayuho.coen390.Service.TaxiDBHelper;

//This class will be constite the layout for entering a taxi number in the settings

public class TaxiTextPreference extends EditTextPreference {
    private TaxiDBHelper db;
    protected Context mContext;
    //This constructor instanciated the Dbhelper obejct and store the context
    public TaxiTextPreference(Context context) {
        super(context);
        mContext = context;
        db = new TaxiDBHelper(context);
    }


    public TaxiTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        db = new TaxiDBHelper(context);
    }

    public TaxiTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        db = new TaxiDBHelper(context);
    }
    //test
    //This function is use when user clicks either the cancel button or the ok button for the edit text preference
    @Override
    public void onClick(DialogInterface dialog, int option) {
        // THis if statement checks the clicked button is OK or cancel
        //In this case it is checking to see if the Ok button was clicked
        if (option == DialogInterface.BUTTON_POSITIVE) {

            //Cursor getTaxiCursor = db.getTaxiNum();
            //create an EditTextPreference object
            EditTextPreference pref_Taxi = (EditTextPreference)findPreferenceInHierarchy("set_taxi");
            //Used to get text from text field, instead of the one stored in Preferences
            EditText taxi_text = pref_Taxi.getEditText();
            //Get the text from the edit text
            String taxi_num = taxi_text.getText().toString();
            Log.i("TaxiNum: ", taxi_num);
            db.deleteAll_taxi();
            db.insert_taxi(taxi_num);
            Toast.makeText(mContext, "Data Inserted", Toast.LENGTH_LONG).show();
        }

        super.onClick(dialog, option);
    }

}
