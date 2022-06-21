package com.example.opensrp_client_covax.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.fragment.app.Fragment;

import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;
import com.example.opensrp_client_covax.listener.ChildBottomNavigationListener;
import com.example.opensrp_client_covax.model.ChildRegisterModel;
import com.example.opensrp_client_covax.presenter.ChildRegisterPresenter;
import com.example.opensrp_client_covax.util.AppConstants;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;

import org.json.JSONObject;
import org.smartregister.child.contract.ChildRegisterContract;
import org.smartregister.child.model.BaseChildRegisterModel;
import org.smartregister.child.presenter.BaseChildRegisterPresenter;
import org.smartregister.helper.BottomNavigationHelper;
import org.smartregister.listener.BottomNavigationListener;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.contract.BaseRegisterContract;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import timber.log.Timber;


public class ChildRegisterActivity extends BaseRegisterActivity implements BaseRegisterContract.View, com.example.opensrp_client_covax.contract.ChildRegisterContract {

    protected String BASE_ENTITY_ID;
    protected String ACTION;
    protected String TABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BASE_ENTITY_ID = getIntent().getStringExtra(AppConstants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID);
        ACTION = getIntent().getStringExtra(AppConstants.ACTIVITY_PAYLOAD.ACTION);
        TABLE = getIntent().getStringExtra(AppConstants.ACTIVITY_PAYLOAD.TABLE_NAME);
        onStartActivityWithAction();
    }

    private void onStartActivityWithAction() {
        if (ACTION != null && ACTION.equals(AppConstants.ACTIVITY_PAYLOAD_TYPE.REGISTRATION)) {
            startFormActivity(getRegistrationForm(), BASE_ENTITY_ID, (String)null);
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new ChildRegisterPresenter( this, new ChildRegisterModel());
    }
    public Class getRegisterActivity(String register) {
        return BaseRegisterActivity.class;
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new ChildRegisterFragment();
    }

    @Override
    protected Fragment[] getOtherFragments() {
        return new Fragment[0];
    }

    @Override
    public void startFormActivity(String s, String s1, Map<String, String> map) {
//        do nothing
    }

    @Override
    public void startFormActivity(JSONObject jsonForm) {
        Intent intent = new Intent(this, getChildFormActivity());
        intent.putExtra(AppConstants.JSON_FORM_EXTRA.JSON, jsonForm.toString());

        if (getFormConfig() != null) {
            intent.putExtra(JsonFormConstants.JSON_FORM_KEY.FORM, getFormConfig());
        }

        startActivityForResult(intent, AppConstants.REQUEST_CODE_GET_JSON);
    }

    @Override
    public Form getFormConfig() {
        return null;
    }

    public Class getChildFormActivity() {
        return BaseRegisterActivity.class;
    }

    protected void startRegisterActivity(Class registerClass) {
        Intent intent = new Intent(this, registerClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        this.finish();
    }
    @Override
    public BaseRegisterContract.Presenter presenter() {
        return (BaseRegisterContract.Presenter) presenter;
    }
    @Override
    protected void onActivityResultExtended(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == AppConstants.REQUEST_CODE_GET_JSON) {
//            process the form
            try {
                String jsonString = data.getStringExtra(AppConstants.JSON_FORM_EXTRA.JSON);
                Timber.d("JSONResult %s", jsonString);
                JSONObject form = new JSONObject(jsonString);
                String encounter_type = form.optString(AppConstants.JSON_FORM_EXTRA.ENCOUNTER_TYPE);
                String table = data.getStringExtra(AppConstants.ACTIVITY_PAYLOAD.TABLE_NAME);
//                if (encounter_type.equalsIgnoreCase(getRegisterEventType())) {
//
//                    presenter().saveForm(jsonString, false, table);
//                } else if (encounter_type.startsWith(AppConstants.EVENT_TYPE.UPDATE_EVENT_CONDITION)) {
//
//                presenter().saveForm(form.toString(), true, TABLE);
//            }
            } catch (Exception e) {
                Timber.e(e);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public List<String> getViewIdentifiers() {
        return Arrays.asList(AppConstants.CONFIGURATION.CHILD_REGISTER);
    }

    @Override
    public void startRegistration() {
        startFormActivity(getRegistrationForm(), null, (String) null);

    }

    @Override
    public String getRegistrationForm() {
        return AppConstants.FORMS.CHILD_REGISTRATION;
    }

    @Override
    public void saveForm(String jsonString, boolean isEditMode, String table) {

    }

    public String getRegisterEventType() {
        return AppConstants.EVENT_TYPE.CHILD_REGISTRATION;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void registerBottomNavigation(){
        bottomNavigationHelper = new BottomNavigationHelper();
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (bottomNavigationView != null) {
            bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            bottomNavigationView.getMenu().removeItem(R.id.action_register);
            bottomNavigationView.getMenu().removeItem(R.id.action_search);

            bottomNavigationView.inflateMenu(getMenuResource());

            bottomNavigationHelper.disableShiftMode(bottomNavigationView);
        }
    }

    @MenuRes
    public int getMenuResource(){
        return R.menu.bottom_nav_menu;
    }

    public BottomNavigationListener getBottomNavigation(Activity activity) {
        return new ChildBottomNavigationListener(activity);
    }
}



