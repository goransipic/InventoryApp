package com.example.android.inventoryapp.data.database;

import android.provider.BaseColumns;

/**
 * Created by User on 2.7.2016..
 */
public class ProductContract {

    private ProductContract() {
    }

    /**
     * Columns supported by "entries" records.
     */
    public static class Products implements BaseColumns {
        /**
         * Table name where records are stored for "entry" resources.
         */
        public static final String TABLE_NAME = "inventory_app";

        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_PRODUCT = "product";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRICE = "price";
    }
}
