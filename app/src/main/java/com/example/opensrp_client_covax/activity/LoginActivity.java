package com.example.opensrp_client_covax.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.presenter.LoginPresenter;
import com.example.opensrp_client_covax.util.AppConstants;

import org.smartregister.growthmonitoring.service.intent.WeightForHeightIntentService;
import org.smartregister.location.helper.LocationHelper;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.view.activity.BaseLoginActivity;
import org.smartregister.view.contract.BaseLoginContract;

public class LoginActivity extends BaseLoginActivity implements BaseLoginContract.View {
    private static final String WFH_CSV_PARSED = "WEIGHT_FOR_HEIGHT_CSV_PARSED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.processViewCustomizations();
        if (!mLoginPresenter.isUserLoggedOut()) {
            goToHome(false);
        }
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_login;    }

    @Override
    protected void initializePresenter() {
        mLoginPresenter = new LoginPresenter(this);

    }

    @Override
    public void goToHome(boolean remote) {
        if (remote) {
            LocationHelper.getInstance().locationIdsFromHierarchy();
            processWeightForHeightZScoreCSV();
        }

        if (mLoginPresenter.isServerSettingsSet()) {
            Intent intent = new Intent(this, ChildRegisterActivity.class);
            intent.putExtra(AppConstants.IntentKeyUtil.IS_REMOTE_LOGIN, remote);
            startActivity(intent);
        }

        finish();
    }
    private void processWeightForHeightZScoreCSV() {
        AllSharedPreferences allSharedPreferences = CovacsApplication.getInstance().getContext().allSharedPreferences();
        if (!allSharedPreferences.getPreference(WFH_CSV_PARSED).equals("true")) {
            WeightForHeightIntentService.startParseWFHZScores(this);
            allSharedPreferences.savePreference(WFH_CSV_PARSED, "true");
        }
    }
}