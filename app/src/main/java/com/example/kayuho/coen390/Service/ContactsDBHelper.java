package com.example.kayuho.coen390.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Kai on 4/10/2016.
 */
public class ContactsDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    //query for contacts table
    private String ContactsTable = "create table if not exists "
            + DbContract.ContactsEntry.TABLE_NAME + " ( "
            + BaseColumns._ID + " integer primary key autoincrement, "
            + DbContract.ContactsEntry.COLUMN_NAME + " text, "
            + DbContract.ContactsEntry.COLUMN_NUM + " text);";   //Integer for phone numbers?

    private String DROP_CONTACTS = "DROP TABLE IF EXISTS " + DbContract.ContactsEntry.TABLE_NAME;

    public ContactsDBHelper(Context context){
        super(context, DbContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //execute create table queries when first creating an object for this class.
    public void onCreate(SQLiteDatabase db){
        db.execSQL(ContactsTable);   //create address table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_CONTACTS); //drops the table to put in the updated one
        onCreate(db);
    }

    //insert contact into database (max 5) && tried eliminating use of name. In my file in only uses phone number
    public boolean insert_contact(String num){
        db = this.getWritableDatabase();
        onCreate(db);
        ContentValues values = new ContentValues(2);
        values.put(DbContract.ContactsEntry.COLUMN_NUM, num);
        long successful = db.insert(DbContract.ContactsEntry.TABLE_NAME, null, values);
        db.close();
        return (successful > 0);
    }


    //Select specific contact number
    public Cursor getContact_num(int num){
        db = this.getWritableDatabase();
        onCreate(db);
        final String retrieveQuery= "SELECT phone_num, FROM"+ DbContract.ContactsEntry.TABLE_NAME
                + " WHERE id="+ num;
        Cursor data = db.rawQuery(retrieveQuery, null);
        return data;
    }

    //retrieve specific contact
    public Cursor getContact(int num){
        db = this.getReadableDatabase();
        onCreate(db);
        final String retrieveQuery= "SELECT * FROM"+ DbContract.ContactsEntry.TABLE_NAME
                + " WHERE id="+ num;
        Cursor data = db.rawQuery(retrieveQuery,null);
        return data;
    }

    // get all data CONTACTS
    public Cursor getAllData_contacts(){
        db = this.getReadableDatabase();  //open connection to DB
        onCreate(db);
        final String retrieveQuery="SELECT * FROM "+ DbContract.ContactsEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery, null);
        return data;
    }

    //drop the contacts table
    public void deleteAll_contacts(){
        db = this.getWritableDatabase();
        onCreate(db);
        int sucess = db.delete(DbContract.ContactsEntry.TABLE_NAME, null, null);
        db.close();

    }

    //deletes a specific contact from the table based on the ID
    public void delete_contact(String num){
        db = this.getWritableDatabase();
        onCreate(db);
        String query = BaseColumns._ID  + " = ?";
        String[] numbers = new String[] { String.valueOf(num) };
        int sucess = db.delete(DbContract.ContactsEntry.TABLE_NAME, query, numbers);
        db.close();
    }

}
