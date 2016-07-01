package com.example.android.inventoryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.inventoryapp.products.ProductsFragment;
import com.example.android.inventoryapp.util.ActivityUtils;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        if (savedInstanceState == null) {

            ProductsFragment productsFragment = ProductsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),productsFragment,R.id.contentFrame);

        }

    }
}
