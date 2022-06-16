package com.example.opensrp_client_covax.contract;

public interface LoginJobScheduler {
    void scheduleJobsPeriodically();

    void scheduleJobsImmediately();

    long getFlexValue(int value);
}
