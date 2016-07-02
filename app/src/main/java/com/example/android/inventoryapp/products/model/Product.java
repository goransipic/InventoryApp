package com.example.android.inventoryapp.products.model;

/**
 * Created by goransi on 1.7.2016..
 */
public class Product {

    private byte[] mImage;
    private String mProduct;
    private String mQuantity;
    private String mPrice;

    public Product(String product, String quantity, String price, byte[] image) {
        this.mProduct = product;
        this.mQuantity = quantity;
        this.mPrice = price;
        this.mImage = image;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        this.mImage = image;
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

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        this.mPrice = price;
    }
}
