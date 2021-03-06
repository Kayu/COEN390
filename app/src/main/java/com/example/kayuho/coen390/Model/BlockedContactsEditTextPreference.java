package com.example.kayuho.coen390.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kayuho.coen390.Service.ContactsDBHelper;

/**
 * Created by Mathew on 28/03/2016.
 */

public class BlockedContactsEditTextPreference extends EditTextPreference {


    private ContactsDBHelper db;
    protected Context mContext;
    //This constructor instanciated the Dbhelper obejct and store the context

    public BlockedContactsEditTextPreference(Context context) {
        super(context);
        mContext = context;
        db = new ContactsDBHelper(context);
    }

    public BlockedContactsEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        db = new ContactsDBHelper(context);
    }

    public BlockedContactsEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        db = new ContactsDBHelper(context);
    }

    //This function is use when user clicks either the cancel button or the ok button for the edit text preference
    @Override
    public void onClick(DialogInterface dialog, int option) {
        // This if statement checks the clicked button is OK or cancel
        //In this case it is checking to see if the Ok button was clicked
        if (option == DialogInterface.BUTTON_POSITIVE) {

            Cursor getContactCursor = db.getAllData_contacts();
            boolean result = true;

            //create an EditTextPreference object
            EditTextPreference pref_blocked_number = (EditTextPreference) findPreferenceInHierarchy("blocked_contacts");

            EditText blockEditText = pref_blocked_number.getEditText();
            //Get the text from the edit text
            //String BlockedContactNum = blockEditText.getText().toString();
            //First check to see if there is space in the database for another contact

            try {
                String BlockedContactNum = blockEditText.getText().toString();


                for(int i = 0; i < BlockedContactNum.length();i++) {
                    char a = BlockedContactNum.charAt(i);
                    if (!Character.isDigit(a)) {
                        throw new BlockedContactInvalidLetters();
                    }
                }

                if(BlockedContactNum.length()!=10){
                    throw new PhoneNumberWrongLengthException();
                }

                if (getContactCursor.getCount() == 5) {
                    Toast.makeText(mContext, "Too Many Blocked Contacts. Please Delete to enter a new one", Toast.LENGTH_LONG).show();
                    return;
                }

                // If there is space, incrementally check the database to ensure the contact is not already there
                else if (getContactCursor.getCount() < 5) {
                    while (getContactCursor.moveToNext()) {
                        if (getContactCursor.getString(2).equals(BlockedContactNum)) {
                            Toast.makeText(mContext, "This Contact is Already Blocked", Toast.LENGTH_LONG).show();
                            result = false;
                            break;
                        }
                    }
                    if (result) {
                        db.insert_contact(BlockedContactNum);
                        Toast.makeText(mContext, "Contact Inserted", Toast.LENGTH_LONG).show();
                    }

                }
                //If there is space and no reccurance, add contact to dB
                else {
                    Log.i("Blocked Number: ", BlockedContactNum);
                    //call the insert address method in the ContactDbHelper
                    db.insert_contact(BlockedContactNum);
                }
                super.onClick(dialog, option);
            } catch (BlockedContactInvalidLetters e) {
                Toast.makeText(mContext, "Please enter a ten digit phone number with only numbers 0-9", Toast.LENGTH_LONG).show();
            } catch (PhoneNumberWrongLengthException e){
                Toast.makeText(mContext,"Please enter a ten digit phone number.", Toast.LENGTH_LONG).show();
            }
/*
                if (getContactCursor.getCount() == 5)
                {
                    Toast.makeText(mContext,"Too Many Blocked Contacts. Please Delete to enter a new one", Toast.LENGTH_LONG).show();
                    return;
                }

                // If there is space, incrementally check the database to ensure the contact is not already there
                else if (getContactCursor.getCount() < 5) {
                    while (getContactCursor.moveToNext()) {
                        if (getContactCursor.getString(2).equals(BlockedContactNum)) {
                            Toast.makeText(mContext, "This Contact is Already Blocked", Toast.LENGTH_LONG).show();
                            result = false;
                            break;
                        }
                    }
                    if(result){
                        db.insert_contact(BlockedContactNum);
                        Toast.makeText(mContext,"Contact Inserted", Toast.LENGTH_LONG).show();
                    }

                }
                //If there is space and no reccurance, add contact to dB
                else {Log.i("Blocked Number: ", BlockedContactNum);
                //call the insert address method in the ContactDbHelper
                db.insert_contact(BlockedContactNum);}
            }*/
        super.onClick(dialog, option);
    }
        }
    }



