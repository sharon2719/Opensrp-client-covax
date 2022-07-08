package com.example.opensrp_client_covax.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;
import com.example.opensrp_client_covax.listener.ChildBottomNavigationListener;
import com.example.opensrp_client_covax.model.AppChildRegisterModel;
import com.example.opensrp_client_covax.presenter.AppChildRegisterPresenter;
import com.example.opensrp_client_covax.util.AppConstants;
import com.example.opensrp_client_covax.util.AppJsonFormUtils;
import com.example.opensrp_client_covax.views.NavDrawerActivity;
import com.example.opensrp_client_covax.views.NavigationMenu;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.vijay.jsonwizard.constants.JsonFormConstants;

import org.json.JSONObject;
import org.smartregister.child.domain.UpdateRegisterParams;
import org.smartregister.child.util.Utils;
import org.smartregister.client.utils.domain.Form;
import org.smartregister.helper.BottomNavigationHelper;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class ChildRegisterActivity extends BaseRegisterActivity implements com.example.opensrp_client_covax.contract.ChildRegisterContract.View, NavDrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationMenu.getInstance(this);
    }

    @Override
    public void initializePresenter() {
        presenter = new AppChildRegisterPresenter(this, new AppChildRegisterModel());
    }

    @Override
    public BaseRegisterFragment getRegisterFragment() {
        WeakReference<ChildRegisterFragment> weakReference = new WeakReference<>(new ChildRegisterFragment());
        return weakReference.get();
    }

    @Override
    public Fragment[] getOtherFragments() {
        return new Fragment[0];
    }

    @Override
    public void startFormActivity(String s, String s1, Map<String, String> map) {
//        do nothing
    }

    @Override
    public void setActiveMenuItem(int menuItemId) {

    }

    @Override
    public void startFormActivity(JSONObject jsonForm) {
        Intent intent = new Intent(this, Utils.metadata().childFormActivity);
        intent.putExtra(AppConstants.JSON_FORM_EXTRA.JSON, jsonForm.toString());

        Form form = new Form();
        form.setHideSaveLabel(true);
        intent.putExtra(JsonFormConstants.JSON_FORM_KEY.FORM, form);
        startActivityForResult(intent, AppJsonFormUtils.REQUEST_CODE_GET_JSON);
    }


    @Override
    public void onActivityResultExtended(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppJsonFormUtils.REQUEST_CODE_GET_JSON && resultCode == RESULT_OK) {
            try {
                String jsonString = data.getStringExtra(AppConstants.JSON_FORM_EXTRA.JSON);
                Timber.d("JSONResult : %s", jsonString);

                JSONObject form = new JSONObject(jsonString);
                if (form.getString(AppJsonFormUtils.ENCOUNTER_TYPE).equals(Utils.metadata().childRegister.registerEventType)
                        || form.getString(AppJsonFormUtils.ENCOUNTER_TYPE).equals(AppConstants.EVENT_TYPE.CHILD_REGISTRATION)
                ) {
                    presenter().saveForm(jsonString, false);
                }
            } catch (Exception e) {
                Timber.e(e);
            }

        }
    }

    @Override
    public List<String> getViewIdentifiers() {
        return Arrays.asList(CovacsApplication.getInstance().getMetadata().childRegister.config);
    }

    @Override
    public void startRegistration() {
//TODO implementation
    }

    @Override
    public String getRegistrationForm() {
        return AppConstants.JSON_FORM.CHILD_ENROLLMENT;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void registerBottomNavigation() {
        bottomNavigationHelper = new BottomNavigationHelper();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            bottomNavigationView.getMenu().removeItem(R.id.action_clients);
            bottomNavigationView.getMenu().removeItem(R.id.action_register);
            bottomNavigationView.getMenu().removeItem(R.id.action_search);
            bottomNavigationView.getMenu().removeItem(R.id.action_library);


            bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);

            bottomNavigationHelper.disableShiftMode(bottomNavigationView);

            ChildBottomNavigationListener childBottomNavigationListener = new ChildBottomNavigationListener(this);
            bottomNavigationView.setOnNavigationItemSelectedListener(childBottomNavigationListener);

        }
    }

    @Override
    public com.example.opensrp_client_covax.contract.ChildRegisterContract.Presenter presenter() {
        return (com.example.opensrp_client_covax.contract.ChildRegisterContract.Presenter) presenter;
    }

    @Override
    public void onRegistrationSaved() {
        Intent intent = new Intent(this, getActivityClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

    @Override
    public void saveForm(String jsonString, UpdateRegisterParams updateRegisterParam) {

    }


    private Class<? extends BaseRegisterActivity> getActivityClass() {
        return ChildRegisterActivity.class;
    }


    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void openDrawer() {

    }

    @Override
    protected void onResumption() {

    }
}
