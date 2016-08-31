package com.example.android.inventoryapp;

import android.provider.BaseColumns;

/**
 * Created by BOX on 8/29/2016.
 */
public class DBContract {

    private static final String TEXT = " TEXT ";
    private static final String COMMA = " , ";

    public DBContract() {
    }

    public static abstract class Table implements BaseColumns {

        public static final String NAME = "inventory";
        public static final String ID = "id";
        public static final String TITLE =  "title";
        public static final String QUANTITY = "quantity";
        public static final String PRICE = "price";
        public static final String IMAGE = "image";

        public static final String CREATE_TABLE = "CREATE TABLE "+NAME+" ("+ID+ " INTEGER PRIMARY KEY,"+ TITLE+ TEXT + COMMA+ QUANTITY+ " INTEGER " + COMMA + PRICE + " REAL"+COMMA+IMAGE+TEXT+" )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+ NAME;

    }
}
