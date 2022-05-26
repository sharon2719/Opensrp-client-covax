package com.example.opensrp_client_covax.activity;

import static org.smartregister.child.util.Constants.*;

import android.app.Activity;
import android.content.Intent;

import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;
import com.example.opensrp_client_covax.model.AppChildRegisterModel;
import com.example.opensrp_client_covax.presenter.AppChildRegisterPresenter;
import com.example.opensrp_client_covax.util.AppConstants;

import org.json.JSONObject;
import org.smartregister.child.activity.BaseChildRegisterActivity;
import org.smartregister.child.util.Constants;
import org.smartregister.client.utils.domain.Form;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.Map;

import timber.log.Timber;

public class ChildRegisterActivity extends BaseChildRegisterActivity {

    @Override
    protected void initializePresenter() {
        presenter = new AppChildRegisterPresenter(this, new AppChildRegisterModel());
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new ChildRegisterFragment();
    }

    @Override
    public String getRegistrationForm() {
        return AppConstants.JsonForm.CHILD_ENROLLMENT;
    }

    @Override
    public void startFormActivity(JSONObject jsonForm) {
        Form form = new Form();
        form.setWizard(false);
        form.setHideSaveLabel(true);
        form.setNextLabel("");
    }
}