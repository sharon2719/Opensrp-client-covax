package com.example.opensrp_client_covax.activity;


import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.domain.UpdateRegisterParams;
import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;
import com.example.opensrp_client_covax.listener.ChildBottomNavigationListener;
import com.example.opensrp_client_covax.model.AppChildRegisterModel;
import com.example.opensrp_client_covax.presenter.AppChildRegisterPresenter;
import com.example.opensrp_client_covax.util.AppConstants;
import com.example.opensrp_client_covax.util.AppJsonFormUtils;
import com.example.opensrp_client_covax.util.Utils;
import com.example.opensrp_client_covax.views.NavDrawerActivity;
import com.example.opensrp_client_covax.views.NavigationMenu;

//import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.vijay.jsonwizard.constants.JsonFormConstants;

import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.client.utils.domain.Form;
import org.smartregister.helper.BottomNavigationHelper;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class ChildRegisterActivity extends BaseRegisterActivity implements com.example.opensrp_client_covax.contract.ChildRegisterContract.View, NavDrawerActivity {

    private int disabledMenuId;

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
    public void startFormActivity(JSONObject jsonForm) {
        Intent intent = new Intent(this, com.example.opensrp_client_covax.util.Utils.metadata().childFormActivity);
        intent.putExtra(AppConstants.INTENT_KEY.JSON, jsonForm.toString());

        Form form = new Form();
        form.setWizard(false);
        form.setHideSaveLabel(true);
        form.setNextLabel("");
        form.setName(getFormTitle());
        form.setActionBarBackground(R.color.tab_indicator_color);
        form.setNavigationBackground(R.color.toolbar_background);
        form.setHomeAsUpIndicator(R.drawable.ic_action_clear);


        intent.putExtra(JsonFormConstants.JSON_FORM_KEY.FORM, form);
        intent.putExtra(JsonFormConstants.PERFORM_FORM_TRANSLATION, true);
        startActivityForResult(intent, AppJsonFormUtils.REQUEST_CODE_GET_JSON);
    }

    private String getFormTitle() {
        return null;
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
        return Arrays.asList();
    }

    @Override
    public void startRegistration() {
        startFormActivity(getFormJson(getRegistrationForm()));
    }

    @Override
    public String getRegistrationForm() {
        return AppConstants.JSON_FORM.CHILD_ENROLLMENT;
    }

    @Override
    protected void registerBottomNavigation() {

        bottomNavigationHelper = new BottomNavigationHelper();
        bottomNavigationView = findViewById(org.smartregister.R.id.bottom_navigation);

        if(bottomNavigationView != null) {
            bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            bottomNavigationView.getMenu().removeItem(R.id.action_clients);
            bottomNavigationView.getMenu().removeItem(R.id.action_register);
            bottomNavigationView.getMenu().removeItem(R.id.action_search);
            bottomNavigationView.getMenu().removeItem(R.id.action_library);

            bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);

            bottomNavigationHelper.disableShiftMode(bottomNavigationView);

        }

        ChildBottomNavigationListener childBottomNavigationListener = getChildBottomNavigationListener();
        bottomNavigationView.setOnNavigationItemSelectedListener(childBottomNavigationListener);
    }

    protected ChildBottomNavigationListener getChildBottomNavigationListener() {
        return new ChildBottomNavigationListener(this);
    }

    @Override
    public com.example.opensrp_client_covax.contract.ChildRegisterContract.Presenter presenter() {
        return (com.example.opensrp_client_covax.contract.ChildRegisterContract.Presenter) presenter;
    }

    @Override
    public void onRegistrationSaved() {
        Intent intent = new Intent(this, ChildRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }
    public JSONObject getFormJson(String formIdentity) {
        try {
            InputStream inputStream = getApplicationContext().getAssets()
                    .open("json.form/" + formIdentity + ".json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
                    "UTF-8"));
            String jsonString;
            StringBuilder stringBuilder = new StringBuilder();
            while ((jsonString = reader.readLine()) != null) {
                stringBuilder.append(jsonString);
            }
            inputStream.close();

            return new JSONObject(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void saveForm(String jsonString, UpdateRegisterParams updateRegisterParam) {

    }


    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void openDrawer() {

    }
    @Override
    public void onResume() {
        super.onResume();
        onResumption();
    }

    @Override
    protected void onResumption() {
        reEnableMenuItem();
        setSelectedBottomBarMenuItem(R.id.action_home);
    }

    private void reEnableMenuItem() {
        if (disabledMenuId != 0)
            bottomNavigationView.getMenu().findItem(disabledMenuId).setEnabled(true);
    }

    @Override
    public void setActiveMenuItem(int menuItemId) {
        disabledMenuId = menuItemId;
    }
    @Override
    public void onBackPressed() {
        if (currentPage == 0) {
            super.onBackPressed();
        } else {
            switchToBaseFragment();
            setSelectedBottomBarMenuItem(R.id.action_home);
        }
    }

}
