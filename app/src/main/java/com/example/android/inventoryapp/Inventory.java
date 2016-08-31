package com.example.android.inventoryapp;

/**
 * Created by BOX on 8/29/2016.
 */
public class Inventory {

    private int id;
    private String productName;
    private int quantity;
    private double price;

    public Inventory() {
        super();
    }

    public Inventory(String productName, int quantity, double price) {
        super();
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void quantitySale(){
        quantity-=1;
        if (quantity<0){
            quantity=0;
        }
    }

    @Override
    public String toString() {
        return "\n"+ getProductName()+ " "+ getPrice() + " " + getQuantity();
    }

}
