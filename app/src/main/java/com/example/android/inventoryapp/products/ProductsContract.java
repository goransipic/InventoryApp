package com.example.android.inventoryapp.products;

import com.example.android.inventoryapp.BasePresenter;
import com.example.android.inventoryapp.BaseView;
import com.example.android.inventoryapp.products.model.Product;

/**
 * Created by goransi on 1.7.2016..
 */
public interface ProductsContract {


    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void loadProducts();

        void addNewProduct();

        void openProductDetail(Product clickedProduct);
    }

}
