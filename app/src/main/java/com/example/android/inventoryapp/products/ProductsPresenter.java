package com.example.android.inventoryapp.products;

import com.example.android.inventoryapp.data.source.ProductsDataSource;
import com.example.android.inventoryapp.products.model.Product;

/**
 * Created by goransi on 1.7.2016..
 */
public class ProductsPresenter implements ProductsContract.Presenter {

    ProductsDataSource mDataSource;
    ProductsContract.View mView;

    public ProductsPresenter(ProductsDataSource dataSource, ProductsContract.View view) {
        this.mDataSource = dataSource;
        this.mView = view;
    }

    @Override
    public void loadProducts(ProductsDataSource.LoadProductsCallback loadProductsCallback) {

        loadProductsCallback.onProductsLoaded(mDataSource.getProdutcs());
    }

    @Override
    public void addNewProduct(Product product) {

        mDataSource.saveProduct(product);

    }

    @Override
    public void start() {

    }

    @Override
    public void openProductDetail(Product clickedProduct) {

    }

    @Override
    public void onBuyButtonClicked(Product clickedProduct) {
        mDataSource.updateProduct(clickedProduct);
    }
}
