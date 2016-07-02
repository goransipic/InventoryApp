package com.example.android.inventoryapp.products;

import android.support.annotation.NonNull;

import com.example.android.inventoryapp.BasePresenter;
import com.example.android.inventoryapp.BaseView;
import com.example.android.inventoryapp.data.source.ProductsDataSource;
import com.example.android.inventoryapp.products.model.Product;

/**
 * Created by goransi on 1.7.2016..
 */
public interface ProductsContract {


    interface View extends BaseView<Presenter> {

        void addNewProduct();


    }

    interface Presenter extends BasePresenter {

        void loadProducts(@NonNull ProductsDataSource.LoadProductsCallback callback);

        void openProductDetail(Product clickedProduct);

        void onBuyButtonClicked(Product clickedProduct);

        void addNewProduct(Product product);
    }

}
