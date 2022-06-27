package com.example.opensrp_client_covax.activity;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;

import org.json.JSONObject;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class ChildRegisterActivity extends BaseRegisterActivity {
    @Override
    protected void initializePresenter() {

    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        WeakReference<ChildRegisterFragment> weakReference = new WeakReference<>(new ChildRegisterFragment());
        return weakReference.get();
    }

    @Override
    protected Fragment[] getOtherFragments() {
        return new Fragment[0];
    }

    @Override
    public void startFormActivity(String s, String s1, Map<String, String> map) {

    }

    @Override
    public void startFormActivity(JSONObject jsonObject) {

    }

    @Override
    protected void onActivityResultExtended(int i, int i1, Intent intent) {

    }

    @Override
    public List<String> getViewIdentifiers() {
        return null;
    }

    @Override
    public void startRegistration() {

    }

    @Override
    protected void registerBottomNavigation() {
        super.registerBottomNavigation();
    }
}
