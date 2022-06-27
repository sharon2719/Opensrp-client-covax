package com.example.opensrp_client_covax.interactor;

import com.example.opensrp_client_covax.contract.LoginJobScheduler;

import org.smartregister.login.interactor.BaseLoginInteractor;
import org.smartregister.view.contract.BaseLoginContract;


public class LoginInteractor extends BaseLoginInteractor implements BaseLoginContract.Interactor {

    private LoginJobScheduler scheduler = new LoginJobSchedulerProvider();

    public LoginInteractor(BaseLoginContract.Presenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    protected void scheduleJobsPeriodically() {
        scheduler.scheduleJobsPeriodically();
    }

    @Override
    protected void scheduleJobsImmediately() {
        super.scheduleJobsImmediately();
        scheduler.scheduleJobsImmediately();
    }
}
