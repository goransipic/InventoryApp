package com.example.android.inventoryapp.data.source;

import android.support.annotation.NonNull;

import com.example.android.inventoryapp.products.model.Product;

import java.util.List;

/**
 * Created by goransi on 1.7.2016..
 */
public interface ProductsDataSource {

    interface LoadProductsCallback {

        void onProductsLoaded(List<Product> products);

        void onDataNotAvailable();
    }

    interface GetProductCallback {

        void onTaskLoaded(Product product);

        void onDataNotAvailable();

    }

    List<Product> getProdutcs();

//    void getProduct(@NonNull String productId, @NonNull GetProductCallback callback);

    void saveProduct(@NonNull Product product);

    void updateProduct(Product product);

    void deleteProduct(Product product);

    boolean findByProductName(String product);
}
