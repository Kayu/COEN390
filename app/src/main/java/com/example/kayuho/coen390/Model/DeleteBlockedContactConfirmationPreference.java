package com.example.kayuho.coen390.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kayuho.coen390.Service.ContactsDBHelper;

/**
 * Created by Mathew on 28/03/2016.
 */

public class DeleteBlockedContactConfirmationPreference extends EditTextPreference {


    private ContactsDBHelper db;
    protected Context mContext;
    //This constructor instanciated the Dbhelper obejct and store the context

    public DeleteBlockedContactConfirmationPreference(Context context) {
        super(context);
        mContext = context;
        db = new ContactsDBHelper(context);
    }

    public DeleteBlockedContactConfirmationPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        db = new ContactsDBHelper(context);
    }

    public DeleteBlockedContactConfirmationPreference(Context context, AttributeSet attrs, int defStyle) {
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
            EditTextPreference blockedContactTextField = (EditTextPreference)findPreferenceInHierarchy("delete_blocked_contact");
            EditText pref_blocked_number = blockedContactTextField.getEditText();

            //First check to see if there are any contacts in the DB

            try {
                String BlockedContactNum = pref_blocked_number.getText().toString();


                for (int i = 0; i < BlockedContactNum.length(); i++) {
                    char a = BlockedContactNum.charAt(i);
                    if (!Character.isDigit(a)) {
                        throw new BlockedContactInvalidLetters();
                    }
                }

                if (BlockedContactNum.length() != 10) {
                    throw new PhoneNumberWrongLengthException();
                } else if (getContactCursor.getCount() == 0) {
                    Toast.makeText(mContext, "There are no contacts Currently Blocked", Toast.LENGTH_LONG).show();
                    return;
                }

                // If there are contacts, incrementally check the database to ensure the contact to be deleted is in the db
                else if (getContactCursor.getCount() <= 5) {
                    while (getContactCursor.moveToNext()) {
                        if (getContactCursor.getString(2).equals(BlockedContactNum)) {
                            String ContactID = getContactCursor.getString(0);
                            db.delete_contact(ContactID);
                            Toast.makeText(mContext, "Contact Deleted from Blocked List", Toast.LENGTH_LONG).show();
                            break;
                        } else if (result) {
                            Toast.makeText(mContext, "This contact was not blocked.", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }catch (BlockedContactInvalidLetters e){
                Toast.makeText(mContext, "Please enter a ten digit phone number with only numbers 0-9", Toast.LENGTH_LONG).show();
            }catch (PhoneNumberWrongLengthException e){
                Toast.makeText(mContext,"Please enter a ten digit phone number.", Toast.LENGTH_LONG).show();
            }
            super.onClick(dialog, option);
        }
    }
}
