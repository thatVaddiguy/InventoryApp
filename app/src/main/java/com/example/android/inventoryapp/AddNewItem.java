package com.example.android.inventoryapp;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by BOX on 8/29/2016.
 */
public class AddNewItem extends AppCompatActivity{

    public String price;
    public String countItem;
    public String name;
    public long nextId;

    public Inventory inventoryObj = new Inventory();
    DBHandler dbHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        nextId = dbHandler.rowCount()+1;
        Intent intent = getIntent();
        String message = intent.getStringExtra("HEADER");
        setTitle(message);
    }

    public void OnClickSubmit(View view){

        EditText nameText = (EditText)findViewById(R.id.productName);
        EditText priceText = (EditText)findViewById(R.id.productPrice);
        EditText quantityText = (EditText)findViewById(R.id.productQuantity);
        ImageView img = (ImageView)findViewById(R.id.imageSelected);
        ImageView imgError = (ImageView)findViewById(R.id.imageError);
        name = nameText.getText().toString();
        price = priceText.getText().toString();
        countItem = quantityText.getText().toString();

        if (name.length()==0){
            Toast.makeText(getApplicationContext(),"Name cannot be empty",Toast.LENGTH_SHORT).show();
            nameText.setError("Invalid input");
            return;
        }
        else if (price.length()==0){
            Toast.makeText(getApplicationContext(),"Price cannot be empty",Toast.LENGTH_SHORT).show();
            priceText.setError("Invalid input");
            return;
        }
        else if (countItem.length()==0){
            Toast.makeText(getApplicationContext(),"Quantity cannot be empty",Toast.LENGTH_SHORT).show();
            quantityText.setError("Invalid input");
        }else if (img.getDrawable()==null){
            Toast.makeText(getApplicationContext(),"Image cannot be empty",Toast.LENGTH_LONG).show();
            imgError.setVisibility(View.VISIBLE);
        }
        else {
            dbHandler.addItem(new Inventory(name,Integer.parseInt(countItem),Double.parseDouble(price)));
            Toast.makeText(this,"Item added",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);

        }
    }

    public void btnImageOnClick(View view){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1&& resultCode==RESULT_OK&& data!=null){

            Toast.makeText(this,"Uploading...",Toast.LENGTH_LONG).show();
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);

                ImageView imageView = (ImageView)findViewById(R.id.imageSelected);
                imageView.setImageBitmap(bitmap);

                String filename = Long.toString(nextId);
                saveToStorage(bitmap,filename);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,"Could not get image",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToStorage(Bitmap bitmap , String filename){

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File appDirectory = contextWrapper.getFilesDir();

        File currentPath = new File(appDirectory,filename);

        FileOutputStream fileOutputStream;
        try{
            fileOutputStream = new FileOutputStream(currentPath);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
