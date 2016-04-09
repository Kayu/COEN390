package com.example.kayuho.coen390.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Alexei on 09/03/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    //Database name
    private static String DATABASE_NAME = "facts.db";
    //database version
    private static final int DATABASE_VERSION = 1;

    /*
     SQL query that delete the table
     "drop talbe if exist facts;"
    */
    private String DROP_ADDRESS = "DROP TABLE IF EXISTS " + DbContract.AddressEntry.TABLE_NAME;
    private String DROP_CONTACTS = "DROP TABLE IF EXISTS " + DbContract.ContactsEntry.TABLE_NAME;
    private String DROP_TAXI = "DROP TABLE IF EXISTS " + DbContract.TaxiEntry.TABLE_NAME;
    /*
    SQL query that create the table
    "create table if not exists facts ( ID integer primary key autoincrement, fact text, type text);"
     */
    //query for contacts table
    private String ContactsTable = "create table if not exists "
            + DbContract.ContactsEntry.TABLE_NAME + " ( "
            + BaseColumns._ID + " integer primary key autoincrement, "
            + DbContract.ContactsEntry.COLUMN_NAME + " text, "
            + DbContract.ContactsEntry.COLUMN_NUM + " text);";   //Integer for phone numbers?

    //query for address table
    private String AddressTable = "create table if not exists "
            + DbContract.AddressEntry.TABLE_NAME + " ( "
            + BaseColumns._ID + " integer primary key autoincrement, "
            + DbContract.AddressEntry.COLUMN_ADDRESS + " text);";

    //query for Taxi table
    private String TaxiTable = "create table if not exists "
            + DbContract.TaxiEntry.TABLE_NAME + " ( "
            + BaseColumns._ID + " integer primary key autoincrement, "
            + DbContract.TaxiEntry.COLUMN_NUM + " text);";


    //database connection object
    private SQLiteDatabase db;
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //execute create table queries when first creating an object for this class.
    public void onCreate(SQLiteDatabase db){
        db.execSQL(ContactsTable);  //create contacts table
        db.execSQL(AddressTable);   //create address table
        db.execSQL(TaxiTable); //create taxi table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_ADDRESS); //drops the table to put in the updated one
        db.execSQL(DROP_CONTACTS);
        db.execSQL(DROP_TAXI);
        onCreate(db);
    }

    //insert the address into database
    public boolean insert_address(String address){
        db = this.getWritableDatabase(); //get permission to write into the database
            ContentValues values = new ContentValues(1); //amount of items trying to input into the DB
            values.put(DbContract.AddressEntry.COLUMN_ADDRESS, address);
            long successful = db.insert(DbContract.AddressEntry.TABLE_NAME, null, values);
            db.close(); // close the connection to the database -- opened during getWritabableDatabase();
            return (successful > 0);
    }

    // may be useful -- possibly wipe later on
    public Cursor getAllData_address(){
        db = this.getReadableDatabase();  //open connection to DB
        final String retrieveQuery="SELECT * FROM "+ DbContract.AddressEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery, null);
        db.close(); //close connection to DB
        return data;
    }

    public Cursor getAddress(){
        db = this.getWritableDatabase();
        final String retrieveQuery= "SELECT "+ DbContract.AddressEntry.COLUMN_ADDRESS
                + " FROM "+ DbContract.AddressEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery,null);
        return data;
    }
    //drop address table all address table
    public void deleteAll_address(){
        db = this.getWritableDatabase();
        db.delete(DbContract.AddressEntry.TABLE_NAME, null, null);
        db.close();

    }
    //------------------------CONTACTS--------------------------------------

    //insert contact into database (max 5) && tried eliminating use of name. In my file in only uses phone number
    public boolean insert_contact(String num){
        db = this.getWritableDatabase();
                ContentValues values = new ContentValues(2);
                values.put(DbContract.ContactsEntry.COLUMN_NUM,num);
                long successful = db.insert(DbContract.ContactsEntry.TABLE_NAME, null, values);
                db.close();
                return (successful > 0);
    }


    //Select specific contact number
    public Cursor getContact_num(int num){
        db = this.getWritableDatabase();
        final String retrieveQuery= "SELECT phone_num, FROM"+ DbContract.ContactsEntry.TABLE_NAME
                + " WHERE id="+ num;
        Cursor data = db.rawQuery(retrieveQuery, null);
        return data;
    }



    //retrieve specific contact
    public Cursor getContact(int num){
        db = this.getReadableDatabase();
        final String retrieveQuery= "SELECT * FROM"+ DbContract.ContactsEntry.TABLE_NAME
                + " WHERE id="+ num;
        Cursor data = db.rawQuery(retrieveQuery,null);
        return data;
    }
    // get all data CONTACTS
    public Cursor getAllData_contacts(){
        db = this.getReadableDatabase();  //open connection to DB
        final String retrieveQuery="SELECT * FROM "+ DbContract.ContactsEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery, null);
        return data;
    }

    //drop the contacts table
    public void deleteAll_contacts(){
        db = this.getWritableDatabase();
        db.delete(DbContract.ContactsEntry.TABLE_NAME, null, null);
        db.close();

    }

    //deletes a specific contact from the table based on the ID
    public void delete_contact(String id_num){
        db = this.getWritableDatabase();
        String query = "id =";

        db.delete(DbContract.ContactsEntry.TABLE_NAME, (query + id_num), null);
        db.close();

    }

    //------------------- Taxi -----------------------------

    //insert taxi into database
    public boolean insert_taxi(String num){
        db = this.getWritableDatabase(); //get permission to write into the database
        final String retrieveQuery="SELECT * FROM "+ DbContract.AddressEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery,null);

            ContentValues values = new ContentValues(1); //amount of items trying to input into the DB
            values.put(DbContract.TaxiEntry.COLUMN_NUM, num);
            long successful = db.insert(DbContract.TaxiEntry.TABLE_NAME, null, values);
            db.close(); // close the connection to the database -- opened during getWritabableDatabase();
            return (successful > 0);

    }

    //get the taxi number
    public Cursor getTaxiNum(){
        db = this.getReadableDatabase();  //open connection to DB
        final String retrieveQuery="SELECT * FROM "+ DbContract.TaxiEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery, null);
        db.close(); //close connection to DB
        return data;
    }

    //drop the taxi table
    public void deleteAll_taxi(){
        db = this.getWritableDatabase();
        db.delete(DbContract.TaxiEntry.TABLE_NAME, null, null);
        db.close();

    }



    public int getDBVersion(){
        return DATABASE_VERSION;
    }
}
