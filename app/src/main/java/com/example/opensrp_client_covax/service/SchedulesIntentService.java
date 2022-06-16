package com.example.opensrp_client_covax.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.opensrp_client_covax.application.CovacsApplication;

import org.smartregister.CoreLibrary;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;

public class SchedulesIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SchedulesIntentService(String name) {
        super("ScheduleIntentService");
    }

    public boolean isSyncing() {
        return CoreLibrary.getInstance().isPeerToPeerProcessing() || SyncStatusBroadcastReceiver.getInstance().isSyncing();
    }

    public CovacsApplication getCovacsApplication() {
        return (CovacsApplication) CovacsApplication.getInstance();
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (isSyncing())
            return;

        if (getCovacsApplication().allowLazyProcessing())
            processLazyEvents();

    }
    private void processLazyEvents(){
        if (isSyncing())
            return;

    }
}
