package com.example.android.inventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler database = new DBHandler(this);

        ListView listView = (ListView)findViewById(R.id.listView);
        TextView empty = (TextView)findViewById(R.id.emptyView);
        empty.setText(R.string.empty_view);

        ArrayList<Inventory> listArray = database.readInventory();
        listView.setEmptyView(empty);

        ListAdapter adapter = new ListAdapter(listArray);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    public void addNewItem(View view){

        Intent intent = new Intent(this,AddNewItem.class);
        intent.putExtra("HEADER","ADD a new Item");
        startActivity(intent);
    }

}
