package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by BOX on 8/29/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Inventory";

    public DBHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DBContract.Table.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {

        database.execSQL(DBContract.Table.DELETE_TABLE);
        onCreate(database);
    }

    void addItem(Inventory newInventory){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.Table.TITLE,newInventory.getProductName());
        values.put(DBContract.Table.QUANTITY,newInventory.getQuantity());
        values.put(DBContract.Table.PRICE,newInventory.getPrice());

        database.insert(DBContract.Table.NAME,null,values);
        database.close();
    }

    public ArrayList<Inventory> readInventory(){

        ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
        String query = "SELECT * FROM " + DBContract.Table.NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query,null);

        if (cursor.moveToFirst()){

            do {
                Inventory inventory = new Inventory();
                inventory.setId(Integer.parseInt(cursor.getString(0)));
                inventory.setProductName(cursor.getString(1));
                inventory.setQuantity(cursor.getInt(2));
                inventory.setPrice(cursor.getDouble(3));
                inventoryList.add(inventory);
            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return inventoryList;
    }

    public void updateRow(double id , Inventory inventory){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.Table.TITLE,inventory.getProductName());
        values.put(DBContract.Table.PRICE,inventory.getPrice());
        values.put(DBContract.Table.QUANTITY,inventory.getQuantity());
        database.update(DBContract.Table.NAME,values,DBContract.Table.ID+ " = "+ id, null);

    }

    public void deleteRow(double id){

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DBContract.Table.NAME,DBContract.Table.ID+ " = "+id,null);
    }

    public long rowCount(){

        SQLiteDatabase database = this.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(database,DBContract.Table.NAME);
        database.close();
        return count;
    }
}
