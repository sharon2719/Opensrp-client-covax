package com.example.opensrp_client_covax.model;

import com.example.opensrp_client_covax.contract.AppNavigationContract;

import org.smartregister.util.Utils;

import timber.log.Timber;

public class AppNavigationModel implements AppNavigationContract.Model {
    private static AppNavigationModel instance;

    public static AppNavigationModel getInstance() {
        if (instance == null)
            instance = new AppNavigationModel();

        return instance;
    }

    @Override
    public String getCurrentUser() {
        String currentUser = "";
        try {
            currentUser = Utils.getPrefferedName();
        } catch (Exception e) {
            Timber.e(e);
        }

        return currentUser;
    }
}
