package com.example.android.inventoryapp.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.inventoryapp.R;
import com.example.android.inventoryapp.products.adapter.ProductsAdapter;
import com.example.android.inventoryapp.products.model.Product;

import java.util.ArrayList;

/**
 * Created by goransi on 1.7.2016..
 */
public class ProductsFragment extends Fragment implements ProductsContract.View {

    private ProductsContract.Presenter mPresenter;

    private ProductsAdapter mProductsAdapter;

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductsAdapter = new ProductsAdapter(new ArrayList<Product>(0), mProductItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.product_frag, container, false);

        ListView listView = (ListView) root.findViewById(R.id.products_list);
        listView.setAdapter(mProductsAdapter);

        return root;
    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter) {
        mPresenter = presenter;
    }


    public interface ProductItemListener {

        void onProductClick(Product clickedProduct);

    }

    ProductItemListener mProductItemListener = new ProductItemListener() {
        @Override
        public void onProductClick(Product clickedProduct) {
            mPresenter.openProductDetail(clickedProduct);
        }
    };

}
