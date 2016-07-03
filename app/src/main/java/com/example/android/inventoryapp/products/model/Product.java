package com.example.android.inventoryapp.products.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by goransi on 1.7.2016..
 */
public class Product implements Parcelable {

    private byte[] mImage;
    private String mProduct;
    private int mQuantity;
    private double mPrice;

    public Product(String product, int quantity, double price, byte[] image) {
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

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        this.mQuantity = quantity;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(this.mImage);
        dest.writeString(this.mProduct);
        dest.writeInt(this.mQuantity);
        dest.writeDouble(this.mPrice);
    }

    protected Product(Parcel in) {
        this.mImage = in.createByteArray();
        this.mProduct = in.readString();
        this.mQuantity = in.readInt();
        this.mPrice = in.readDouble();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public String toString() {
        return "Product: " + mProduct + "\n" +
                "Quantity: " + mQuantity + " items" +"\n" +
                "Price: " + String.format(Locale.US,"%.2f $",mPrice);
    }
}
