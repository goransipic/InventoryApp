package com.example.android.inventoryapp;

import android.app.Application;

/**
 * Created by User on 2.7.2016..
 */
public class App extends Application {

    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
    }

    public static Application getApplication() {
        return mApplication;
    }
}
