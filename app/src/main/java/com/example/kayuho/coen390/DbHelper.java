package com.example.kayuho.coen390;

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
    private String DROP_TABLE = "DROP TABLE IF EXISTS " + DbContract.ContactsEntry.TABLE_NAME;
    /*
    SQL query that create the table
    "create table if not exists facts ( ID integer primary key autoincrement, fact text, type text);"
     */
    private String ContactsTable = "create table if not exists "
            + DbContract.ContactsEntry.TABLE_NAME + " ( "
            + BaseColumns._ID + " integer primary key autoincrement, "
            + DbContract.ContactsEntry.COLUMN_NAME + " text, "
            + DbContract.ContactsEntry.COLUMN_NUM + " text);";   //Integer for phone numbers?

    //database connection object
    private SQLiteDatabase db;
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    //execute create table querie when first creating an object for this class.
    public void onCreate(SQLiteDatabase db){
        db.execSQL(ContactsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public boolean insert(String name, String num){

        ContentValues values = new ContentValues(2);
        values.put(DbContract.ContactsEntry.COLUMN_NAME, name);
        values.put(DbContract.ContactsEntry.COLUMN_NUM,num);
        db = this.getWritableDatabase();
        long successful = db.insert(DbContract.ContactsEntry.TABLE_NAME, null, values);
        db.close();
        return (successful > 0);
    }

    public Cursor getAllData(){
        db = this.getWritableDatabase();
        final String retrieveQuery="SELECT * FROM "+ DbContract.ContactsEntry.TABLE_NAME;
        Cursor data = db.rawQuery(retrieveQuery, null);
        return data;
    }

    public Cursor getSpecificFactsType(String type){
        db = this.getWritableDatabase();
        final String retrieveQuery= "SELECT "+ DbContract.ContactsEntry.COLUMN_NAME
                + " FROM "+ DbContract.ContactsEntry.TABLE_NAME
                + " WHERE "+ DbContract.ContactsEntry.COLUMN_NUM
                + " = '" + type+"';";
        Cursor data = db.rawQuery(retrieveQuery,null);
        return data;
    }

    public void deleteAll(){
        db = this.getWritableDatabase();
        db.execSQL(DROP_TABLE);

        db.execSQL(ContactsTable);
        db.close();

    }

    public int getDBVersion(){
        return DATABASE_VERSION;
    }
}
