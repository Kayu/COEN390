package com.example.kayuho.coen390;

import android.provider.BaseColumns;

/**
 * Created by Alexei on 09/03/2016.
 */
public class DbContract {

    //first table declaration

    public static final class ContactsEntry implements BaseColumns {
        //table name
        public static final String TABLE_NAME = "contacts";
        //first column name
        public static final String COLUMN_NAME = "name";
        //second column name
        public static final String COLUMN_NUM = "phone_num";
    }

    //second table declaration
    public static final class AddressEntry implements BaseColumns {
        //table name
        public static final String TABLE_NAME = "address";
        //first column name
        public static final String COLUMN_FACT = "myaddress";


    }

    }
