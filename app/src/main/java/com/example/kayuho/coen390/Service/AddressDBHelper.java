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
public class AddressDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    //query for address table
    private String AddressTable = "create table if not exists "
            + DbContract.AddressEntry.TABLE_NAME + " ( "
            + BaseColumns._ID + " integer primary key autoincrement, "
            + DbContract.AddressEntry.COLUMN_ADDRESS + " text);";

    private String DROP_ADDRESS = "DROP TABLE IF EXISTS " + DbContract.AddressEntry.TABLE_NAME;

    public AddressDBHelper(Context context){
        super(context, DbContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //execute create table queries when first creating an object for this class.
    public void onCreate(SQLiteDatabase db){
        db.execSQL(AddressTable);   //create address table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_ADDRESS); //drops the table to put in the updated one
        onCreate(db);
    }

    //insert the address into database
    public boolean insert_address(String address){
        db = this.getWritableDatabase(); //get permission to write into the database
        onCreate(db);
        ContentValues values = new ContentValues(1); //amount of items trying to input into the DB
        values.put(DbContract.AddressEntry.COLUMN_ADDRESS, address);
        long successful = db.insert(DbContract.AddressEntry.TABLE_NAME, null, values);
        db.close(); // close the connection to the database -- opened during getWritabableDatabase();
        return (successful > 0);
    }

    // may be useful -- possibly wipe later on
    public Cursor getAllData_address(){
        db = this.getReadableDatabase();  //open connection to DB
        onCreate(db);
        final String retrieveQuery="SELECT * FROM "+ DbContract.AddressEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery, null);
        db.close(); //close connection to DB
        return data;
    }

    public Cursor getAddress(){
        db = this.getWritableDatabase();
        onCreate(db);
        final String retrieveQuery= "SELECT "+ DbContract.AddressEntry.COLUMN_ADDRESS
                + " FROM "+ DbContract.AddressEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery,null);
        return data;
    }
    //drop address table all address table
    public void deleteAll_address(){
        db = this.getWritableDatabase();
        onCreate(db);
        db.delete(DbContract.AddressEntry.TABLE_NAME, null, null);
        db.close();

    }


}
