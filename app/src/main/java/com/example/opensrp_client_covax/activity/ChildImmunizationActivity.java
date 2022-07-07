package com.example.opensrp_client_covax.activity;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.child.activity.BaseChildImmunizationActivity;
import org.smartregister.child.domain.RegisterClickables;
import org.smartregister.commonregistry.CommonPersonObjectClient;

import java.util.Map;

public class ChildImmunizationActivity extends BaseChildImmunizationActivity {
    @Override
    protected void goToRegisterPage() {

    }

    @Override
    protected int getDrawerLayoutId() {
        return 0;
    }

    @Override
    public void launchDetailActivity(Context context, CommonPersonObjectClient commonPersonObjectClient, RegisterClickables registerClickables) {

    }

    @Override
    protected Activity getActivity() {
        return null;
    }

    @Override
    public boolean isLastModified() {
        return false;
    }

    @Override
    public void setLastModified(boolean b) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onUniqueIdFetched(Triple<String, Map<String, String>, String> triple, String s) {

    }

    @Override
    public void onNoUniqueId() {

    }

    @Override
    public void onRegistrationSaved(boolean b) {

    }
}
