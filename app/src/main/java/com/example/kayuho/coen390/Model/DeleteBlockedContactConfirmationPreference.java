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
            EditTextPreference pref_blocked_number = (EditTextPreference) findPreferenceInHierarchy("blocked_contacts");

            EditText blockEditText = pref_blocked_number.getEditText();
            //Get the text from the edit text
            String BlockedContactNum = blockEditText.getText().toString();
            //First check to see if there are any contacts in the DB
            if (getContactCursor.getCount() == 0) {
                Toast.makeText(mContext, "There are no contacts Currently Blocked", Toast.LENGTH_LONG).show();
                return;
            }

            // If there are contacts, incrementally check the database to ensure the contact to be deleted is in the db
            else if (getContactCursor.getCount() < 5) {
                while (getContactCursor.moveToNext()) {
                    if (getContactCursor.getString(2).equals(BlockedContactNum)) {
                        String ContactID = getContactCursor.getString(0);
                        db.delete_contact(ContactID);
                        Toast.makeText(mContext, "Contact Deleted from Blocked List", Toast.LENGTH_LONG).show();

                    }

                }


            }
            super.onClick(dialog, option);
        }
    }
}
