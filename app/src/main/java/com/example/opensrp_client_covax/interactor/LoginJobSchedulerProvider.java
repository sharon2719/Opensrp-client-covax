package com.example.opensrp_client_covax.interactor;

import com.example.opensrp_client_covax.BuildConfig;
import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.contract.LoginJobScheduler;
import com.example.opensrp_client_covax.job.ScheduleJob;

import org.smartregister.immunization.job.VaccineServiceJob;
import org.smartregister.job.DocumentConfigurationServiceJob;
import org.smartregister.job.ImageUploadServiceJob;
import org.smartregister.job.PlanIntentServiceJob;
import org.smartregister.job.PullUniqueIdsServiceJob;
import org.smartregister.job.SyncLocationsByLevelAndTagsServiceJob;
import org.smartregister.job.SyncServiceJob;
import org.smartregister.job.SyncTaskServiceJob;

import java.util.concurrent.TimeUnit;

public class LoginJobSchedulerProvider implements LoginJobScheduler {
    private static final int MINIMUM_JOB_FLEX_VALUE = 5;

    @Override
    public void scheduleJobsPeriodically() {
        SyncServiceJob.scheduleJob(SyncServiceJob.TAG, TimeUnit.MINUTES.toMinutes(BuildConfig.DATA_SYNC_DURATION_MINUTES), getFlexValue(BuildConfig
                .DATA_SYNC_DURATION_MINUTES));
    }

    @Override
    public void scheduleJobsImmediately() {
        // Run initial job immediately on log in since the job will run a bit later (~ 15 mins +)
//        ScheduleJob.scheduleJobImmediately(ScheduleJob.TAG);
        SyncServiceJob.scheduleJobImmediately(SyncServiceJob.TAG);

    }

    @Override
    public long getFlexValue(int value) {
        int minutes = MINIMUM_JOB_FLEX_VALUE;

        if (value > MINIMUM_JOB_FLEX_VALUE) {

            minutes = (int) Math.ceil(value / 3);
        }

        return Math.max(minutes, MINIMUM_JOB_FLEX_VALUE);
    }
}
