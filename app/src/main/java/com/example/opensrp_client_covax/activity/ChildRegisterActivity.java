package com.example.opensrp_client_covax.activity;


import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;
import com.example.opensrp_client_covax.model.ChildRegisterModel;
import com.example.opensrp_client_covax.presenter.ChildRegisterPresenter;
import com.example.opensrp_client_covax.util.AppConstants;

import org.smartregister.child.activity.BaseChildRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;


public class ChildRegisterActivity extends BaseChildRegisterActivity {


    @Override
    public String getRegistrationForm() {
        return AppConstants.JSON_FORM.CHILD_ENROLLMENT;
    }

    @Override
    protected void initializePresenter() {
        presenter = new ChildRegisterPresenter(this, new ChildRegisterModel());
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new ChildRegisterFragment();
    }
}



