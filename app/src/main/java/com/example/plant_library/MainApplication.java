package com.example.plant_library;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;

public class MainApplication extends Application {
    private static final String TAG = "tag";

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
        Log.d(TAG, "Firebase initialized successfully");

    }
}
