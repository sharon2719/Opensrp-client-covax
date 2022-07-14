package com.example.opensrp_client_covax.interactor;

import com.example.opensrp_client_covax.contract.ChildRegisterContract;

import org.smartregister.util.AppExecutors;

public class AppChildRegisterInteractor implements ChildRegisterContract.Interactor {
    private final AppExecutors appExecutors;

    public AppChildRegisterInteractor() {
        this(new AppExecutors());
    }

    public AppChildRegisterInteractor(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }
    @Override
    public void onDestroy(boolean isChangingConfiguration) {

    }
}
