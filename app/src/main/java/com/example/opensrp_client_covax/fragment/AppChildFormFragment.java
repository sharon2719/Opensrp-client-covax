package com.example.opensrp_client_covax.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.VisibleForTesting;

import com.example.opensrp_client_covax.activity.ChildFormActivity;
import com.example.opensrp_client_covax.interactor.AppChildFormInteractor;
import com.example.opensrp_client_covax.presenter.AppChildFormFragmentPresenter;
import com.vijay.jsonwizard.activities.JsonFormActivity;
import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;
import com.vijay.jsonwizard.fragments.JsonWizardFormFragment;
import com.vijay.jsonwizard.presenters.JsonFormFragmentPresenter;
import com.vijay.jsonwizard.utils.ValidationStatus;
import com.vijay.jsonwizard.viewstates.JsonFormFragmentViewState;


public class AppChildFormFragment extends JsonWizardFormFragment {
    public static final String TAG = AppChildFormFragment.class.getName();
    private ChildFormActivity childFormActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        childFormActivity = (ChildFormActivity) requireActivity();
    }

    public static  AppChildFormFragment getFormFragment(String stepName){
        AppChildFormFragment appChildFormFragment = new AppChildFormFragment();
        Bundle bundle = new Bundle();
        bundle.putString(JsonFormConstants.JSON_FORM_KEY.STEPNAME,stepName);
        appChildFormFragment.setArguments(bundle);
        return appChildFormFragment;
    }

    @Override
    protected AppChildFormFragmentPresenter createPresenter() {
        return new AppChildFormFragmentPresenter(this, AppChildFormInteractor.getChildInteractorInstance());
    }

    public AppChildFormFragmentPresenter getPresenter(){
        return (AppChildFormFragmentPresenter) presenter;
    }

    @Override
    protected JsonFormFragmentViewState createViewState() {
        return new JsonFormFragmentViewState();
    }

    private Form getForm() {
        return this.getActivity() != null && this.getActivity() instanceof JsonFormActivity ?
                ((JsonFormActivity) this.getActivity()).getForm() : null;
    }

    @VisibleForTesting
    protected ValidationStatus validateView(View dataView) {
        return JsonFormFragmentPresenter.validate(this, dataView, false);
    }
}
