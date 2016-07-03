package com.example.android.inventoryapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 2.7.2016..
 */
public class ProductDatabase extends SQLiteOpenHelper {
    /**
     * Schema version.
     */
    public static final int DATABASE_VERSION = 1;
    /**
     * Filename for SQLite file.
     */
    public static final String DATABASE_NAME = "Product.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_BLOB = " BLOB";
    private static final String COMMA_SEP = ",";
    private static final String TYPE_INT = " INTEGER";

    /**
     * SQL statement to create "HABIT TRACKER" table.
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProductContract.Products.TABLE_NAME + " (" +
                    ProductContract.Products._ID + " INTEGER PRIMARY KEY," +
                    ProductContract.Products.COLUMN_IMAGE + TYPE_BLOB + COMMA_SEP +
                    ProductContract.Products.COLUMN_PRODUCT + TYPE_TEXT + COMMA_SEP +
                    ProductContract.Products.COLUMN_QUANTITY + TYPE_INT + COMMA_SEP +
                    ProductContract.Products.COLUMN_PRICE + TYPE_TEXT + ")";
    /**
     * SQL statement to drop "entry" table.
     */
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductContract.Products.TABLE_NAME;

    public ProductDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
