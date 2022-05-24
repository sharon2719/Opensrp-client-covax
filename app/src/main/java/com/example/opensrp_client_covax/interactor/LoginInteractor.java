package com.example.opensrp_client_covax.interactor;

import org.smartregister.login.interactor.BaseLoginInteractor;
import org.smartregister.view.contract.BaseLoginContract;

import java.lang.ref.WeakReference;

public class LoginInteractor extends BaseLoginInteractor implements BaseLoginContract.Interactor {

    @Override
    public void login(WeakReference<BaseLoginContract.View> view, String userName, char[] password) {
        //change case to lowercase before login attempt
        super.login(view, userName.toLowerCase(), password);
    }

    public LoginInteractor(BaseLoginContract.Presenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    protected void scheduleJobsPeriodically() {

    }
}
