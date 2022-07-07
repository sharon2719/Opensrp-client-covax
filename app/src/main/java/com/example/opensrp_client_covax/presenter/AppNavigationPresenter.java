package com.example.opensrp_client_covax.presenter;

import android.app.Activity;

import com.example.opensrp_client_covax.contract.AppNavigationContract;
import com.example.opensrp_client_covax.interactor.AppNavigationInteractor;
import com.example.opensrp_client_covax.model.AppNavigationModel;

import java.lang.ref.WeakReference;

public class AppNavigationPresenter implements AppNavigationContract.Presenter {

    private final AppNavigationContract.Model model;
    private final AppNavigationContract.Interactor interactor;
    private final WeakReference<AppNavigationContract.View> view;

    public AppNavigationPresenter(AppNavigationContract.View view) {
        this.model = AppNavigationModel.getInstance();
        this.interactor = AppNavigationInteractor.getInstance();
        this.view = new WeakReference<>(view);
    }

    @Override
    public AppNavigationContract.View getNavigationView() {
        return null;
    }

    @Override
    public void refreshLastSync() {

    }

    @Override
    public void displayCurrentUser() {

    }

    @Override
    public void sync(Activity activity) {

    }

    @Override
    public String getLoggedInUserInitials() {
        return null;
    }
}
