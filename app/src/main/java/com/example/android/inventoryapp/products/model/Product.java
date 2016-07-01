package com.example.android.inventoryapp.products.model;

/**
 * Created by goransi on 1.7.2016..
 */
public class Product {

    private String mProduct;
    private String mQuantity;
    private int mPrice;

    public Product(String mProduct, String mQuantity, int mPrice) {
        this.mProduct = mProduct;
        this.mQuantity = mQuantity;
        this.mPrice = mPrice;
    }

    public String getProduct() {
        return mProduct;
    }

    public void setProduct(String product) {
        this.mProduct = product;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        this.mQuantity = quantity;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        this.mPrice = price;
    }
}
