package com.example.android.inventoryapp.data.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.android.inventoryapp.App;
import com.example.android.inventoryapp.data.database.ProductContract;
import com.example.android.inventoryapp.data.database.ProductDatabase;
import com.example.android.inventoryapp.products.model.Product;

import java.util.ArrayList;

/**
 * Created by User on 2.7.2016..
 */
public class ProductsRepository implements ProductsDataSource {

    ProductDatabase mProductDatabase = new ProductDatabase(App.getApplication());


    @Override
    public void updateProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = mProductDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductContract.Products.COLUMN_QUANTITY, product.getQuantity());

        sqLiteDatabase.update(ProductContract.Products.TABLE_NAME, contentValues, ProductContract.Products.COLUMN_PRODUCT + "=?", new String[]{product.getProduct()});
    }

    @Override
    public void saveProduct(@NonNull Product product) {
        SQLiteDatabase sqLiteDatabase = mProductDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductContract.Products.COLUMN_IMAGE, product.getImage());
        contentValues.put(ProductContract.Products.COLUMN_PRODUCT, product.getProduct());
        contentValues.put(ProductContract.Products.COLUMN_PRICE, product.getPrice());
        contentValues.put(ProductContract.Products.COLUMN_QUANTITY, product.getQuantity());

        sqLiteDatabase.insert(ProductContract.Products.TABLE_NAME, null, contentValues);
    }

    @Override
    public ArrayList<Product> getProdutcs() {
        SQLiteDatabase sqLiteDatabase = mProductDatabase.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(ProductContract.Products.TABLE_NAME,
                new String[]{ProductContract.Products.COLUMN_IMAGE,
                        ProductContract.Products.COLUMN_PRODUCT,
                        ProductContract.Products.COLUMN_PRICE,
                        ProductContract.Products.COLUMN_QUANTITY},
                null,
                null,
                null,
                null,
                null);

        ArrayList<Product> products = new ArrayList<>();

        while (cursor.moveToNext()) {
            Product product = new Product(null, 0, 0, null);

            byte[] imageTemp = cursor.getBlob(cursor.getColumnIndex(ProductContract.Products.COLUMN_IMAGE));
            String productTemp = cursor.getString(cursor.getColumnIndex(ProductContract.Products.COLUMN_PRODUCT));
            Double priceTemp = cursor.getDouble(cursor.getColumnIndex(ProductContract.Products.COLUMN_PRICE));
            int quantityTemp = cursor.getInt(cursor.getColumnIndex(ProductContract.Products.COLUMN_QUANTITY));

            product.setImage(imageTemp);
            product.setProduct(productTemp);
            product.setPrice(priceTemp);
            product.setQuantity(quantityTemp);

            products.add(product);

        }

        cursor.close();

        return products;

    }

    @Override
    public void deleteProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = mProductDatabase.getWritableDatabase();

        sqLiteDatabase.delete(ProductContract.Products.TABLE_NAME,
                ProductContract.Products.COLUMN_PRODUCT + "=?",
                new String[]{product.getProduct()});
    }

    @Override
    public boolean findByProductName(String product) {
        SQLiteDatabase sqLiteDatabase = mProductDatabase.getReadableDatabase();

        Cursor cursor= sqLiteDatabase.query(ProductContract.Products.TABLE_NAME,
                new String[]{ProductContract.Products.COLUMN_PRODUCT},
                ProductContract.Products.COLUMN_PRODUCT + "=?",
                new String[]{product},
                null,
                null,
                null);

        if (cursor.moveToNext()){
            return true;
        }
        else {
            return false;
        }
    }
}
