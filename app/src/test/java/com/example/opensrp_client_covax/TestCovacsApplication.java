package com.example.opensrp_client_covax;

import android.util.Log;

import androidx.work.Configuration;
import androidx.work.testing.SynchronousExecutor;
import androidx.work.testing.WorkManagerTestInitHelper;

import com.example.opensrp_client_covax.application.CovacsApplication;
import com.google.firebase.FirebaseApp;

public class TestCovacsApplication extends CovacsApplication {
    @Override
    public void onCreate() {

        final Configuration config = new Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setExecutor(new SynchronousExecutor())
                .build();
        WorkManagerTestInitHelper.initializeTestWorkManager(getApplicationContext(), config);

        FirebaseApp.initializeApp(this);
        super.onCreate();
    }

}
