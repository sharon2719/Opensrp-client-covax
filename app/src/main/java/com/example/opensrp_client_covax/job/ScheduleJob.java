package com.example.opensrp_client_covax.job;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.opensrp_client_covax.service.SchedulesIntentService;
import com.example.opensrp_client_covax.util.AppConstants;

import org.smartregister.job.BaseJob;

import timber.log.Timber;

public class ScheduleJob extends BaseJob {
    public static final String TAG = "ScheduleJob";
    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        Timber.v("%s started", TAG);
        getApplicationContext().startService(new Intent(getApplicationContext(), SchedulesIntentService.class));
        return params.getExtras().getBoolean(AppConstants.INTENT_KEY.TO_RESCHEDULE, false) ? Result.RESCHEDULE : Result.SUCCESS;
    }
}
