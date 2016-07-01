package com.example.android.inventoryapp.data.source;

import android.support.annotation.NonNull;

import com.example.android.inventoryapp.products.model.Product;

import java.util.List;

/**
 * Created by goransi on 1.7.2016..
 */
public interface ProductsDataSource {

    interface LoadProductsCallback {

        void onProdutcsLoaded(List<Product> products);

        void onDataNotAvailable();
    }

    interface GetProductCallback {

        void onTaskLoaded(Product product);

        void onDataNotAvailable();

    }

    void getProdutcs(@NonNull LoadProductsCallback callback);

    void getProduct(@NonNull String productId, @NonNull GetProductCallback callback);

}
