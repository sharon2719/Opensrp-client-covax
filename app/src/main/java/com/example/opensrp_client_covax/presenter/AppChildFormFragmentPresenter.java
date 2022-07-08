package com.example.opensrp_client_covax.presenter;

import com.example.opensrp_client_covax.fragment.AppChildFormFragment;
import com.vijay.jsonwizard.interactors.JsonFormInteractor;
import com.vijay.jsonwizard.presenters.JsonFormFragmentPresenter;


public class AppChildFormFragmentPresenter extends JsonFormFragmentPresenter {
    public static final String TAG = AppChildFormFragmentPresenter.class.getName();
    private final AppChildFormFragment formFragment;

    public AppChildFormFragmentPresenter(AppChildFormFragment appChildFormFragment, JsonFormInteractor childInteractorInstance) {
        super(appChildFormFragment,childInteractorInstance);
        formFragment = new AppChildFormFragment();
    }

}
