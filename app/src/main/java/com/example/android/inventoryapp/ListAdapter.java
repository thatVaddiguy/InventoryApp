package com.example.android.inventoryapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by BOX on 8/29/2016.
 */
public class ListAdapter extends BaseAdapter {

    private static final String LOG_TAG = ListAdapter.class.getSimpleName();
    private ArrayList<Inventory> listArray;

    public ListAdapter(ArrayList<Inventory> listArray){
        this.listArray = new ArrayList<Inventory>(listArray);
    }

    @Override
    public int getCount() {
        return listArray.size();
    }

    @Override
    public Object getItem(int i) {
        return listArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {

        if (view==null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view=inflater.inflate(R.layout.list_item,viewGroup,false);
        }

        final Inventory inventory = listArray.get(i);

        final TextView productName = (TextView) view.findViewById(R.id.productName);
        productName.setText(inventory.getProductName());

        final TextView productAvailable = (TextView)view.findViewById(R.id.productAvailable);
        productAvailable.setText(""+inventory.getQuantity());

        final TextView productPrice = (TextView)view.findViewById(R.id.productPrice);
        productPrice.setText("$"+inventory.getPrice());

        Button button = (Button)view.findViewById(R.id.listItemButton);

        this.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler database = new DBHandler(view.getContext());
                inventory.quantitySale();
                if (inventory.getQuantity()==0){
                    Toast.makeText(viewGroup.getContext(),"Get more of "+inventory.getProductName(),Toast.LENGTH_SHORT).show();
                }
                database.updateRow(inventory.getId(),inventory);
                productAvailable.setText("" + inventory.getQuantity());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(viewGroup.getContext(),ItemFullActivity.class);
                details.putExtra("productName",inventory.getProductName());
                details.putExtra("productQuantity",inventory.getQuantity());
                details.putExtra("id",inventory.getId());
                viewGroup.getContext().startActivity(details);
            }
        });

        return view;

    }
}
