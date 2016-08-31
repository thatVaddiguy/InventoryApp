package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by BOX on 8/29/2016.
 */
public class ItemFullActivity extends AppCompatActivity {

    public static double priceProduct;
    public int id;
    public Inventory inventory = new Inventory();
    int quantity = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_full);
        TextView name = (TextView)findViewById(R.id.productName);
        TextView price = (TextView)findViewById(R.id.productPrice);
        TextView quantity = (TextView)findViewById(R.id.productQuantity);
        quantity.setText("0");

        Intent details = getIntent();
        setTitle(details.getStringExtra("productName"));

        name.setText(details.getStringExtra("productName"));
        int id = details.getIntExtra("id",0);
        ContextWrapper contextWrapper = new ContextWrapper(this);
        File dir = contextWrapper.getFilesDir();

        DBHandler database = new DBHandler(this);
        String imageLocationDir = dir.toString();
        id = details.getIntExtra("id",0) -1;
        String imagePath = imageLocationDir + "/" + id;

        ImageView imageView = (ImageView)findViewById(R.id.imageIcon);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        imageView.setImageBitmap(bitmap);

        quantity.setText(" "+details.getIntExtra("productQuantity",0));
        priceProduct = details.getDoubleExtra("productPrice",0.0);
        price.setText("" + priceProduct);
    }


    public void quantityIncrement(View view){

        if (quantity==100){
            return;
        }
        quantity+=1;
        displayQuantity(quantity);
    }

    public void quantityDecrement(View view){

        if (quantity==0){
            return;
        }

        quantity-=1;
        displayQuantity(quantity);
    }


    public void onClickDelete(final View view){

        new AlertDialog.Builder(this).setTitle("Hold Up!").setMessage("Are you sure you want to delete?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteItem(id);
                Toast.makeText(view.getContext(),"Record Deleted",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),MainActivity.class);
                view.getContext().startActivity(intent);
            }
        }).setNegativeButton("No",null).show();
    }

    public void deleteItem(int id){
        DBHandler database = new DBHandler(this);
        database.deleteRow(id);
    }

    public void onSubmitMore(View view){

        String subject = "Order more items";
        String message = "Product Name: "+inventory.getProductName()+"\nProduct Price: "+inventory.getPrice()+"\nQuantity to be ordered: "+inventory.getQuantity();
        String[] email = {"yourBoss@gmail.com"};

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,email);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }

    private void displayQuantity(int count){
        TextView quantity = (TextView)findViewById(R.id.quantity);
        quantity.setText(""+count);
    }
}
