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
public class TaxiDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    //query for Taxi table
    private String TaxiTable = "create table if not exists "
            + DbContract.TaxiEntry.TABLE_NAME + " ( "
            + BaseColumns._ID + " integer primary key autoincrement, "
            + DbContract.TaxiEntry.COLUMN_NUM + " text);";

    private String DROP_TAXI = "DROP TABLE IF EXISTS " + DbContract.TaxiEntry.TABLE_NAME;

    public TaxiDBHelper(Context context){
        super(context, DbContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //execute create table queries when first creating an object for this class.
    public void onCreate(SQLiteDatabase db){
        db.execSQL(TaxiTable);   //create address table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_TAXI); //drops the table to put in the updated one
        onCreate(db);
    }

    //insert taxi into database
    public boolean insert_taxi(String num){

        db = this.getWritableDatabase();
        onCreate(db);
        ContentValues values = new ContentValues(1); //amount of items trying to input into the DB
        values.put(DbContract.TaxiEntry.COLUMN_NUM, num);
        long successful = db.insert(DbContract.TaxiEntry.TABLE_NAME, null, values);
        db.close(); // close the connection to the database -- opened during getWritabableDatabase();
        return (successful > 0);

    }

    //get the taxi number
    public Cursor getTaxiNum(){

        db = this.getWritableDatabase(); //open connection to DB
        onCreate(db);
        String retrieveQuery="SELECT * FROM "+ DbContract.TaxiEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery, null);
        return data;
    }

    //drop the taxi table
    public void deleteAll_taxi(){

        db = this.getWritableDatabase();
        onCreate(db);
        long successs = db.delete(DbContract.TaxiEntry.TABLE_NAME, null, null);
        db.close();

    }

}
