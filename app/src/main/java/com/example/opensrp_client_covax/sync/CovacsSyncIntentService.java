package com.example.opensrp_client_covax.sync;

import android.content.Context;

import androidx.annotation.NonNull;

import org.smartregister.sync.intent.SyncIntentService;

public class CovacsSyncIntentService extends SyncIntentService {


    @Override
    protected void init(@NonNull Context context) {
        super.init(context);
    }

    @Override
    public int getEventPullLimit() {
        return 1000;
    }


    @Override
    protected Integer getEventBatchSize() {
        return 50;
    } // Should this be configurable?
}
