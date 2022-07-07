package com.example.opensrp_client_covax.interactor;

import com.example.opensrp_client_covax.contract.AppNavigationContract;

import java.util.Date;

public class AppNavigationInteractor implements AppNavigationContract.Interactor {
    private static AppNavigationInteractor instance;

    public static AppNavigationInteractor getInstance() {
        if (instance == null)
            instance = new AppNavigationInteractor();

        return instance;
    }

    @Override
    public Date sync() {
        return null;
    }
}
