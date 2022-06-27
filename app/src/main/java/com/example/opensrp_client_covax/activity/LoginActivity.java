package com.example.opensrp_client_covax.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.presenter.LoginPresenter;
import com.example.opensrp_client_covax.util.AppConstants;

import org.smartregister.task.SaveTeamLocationsTask;
import org.smartregister.util.Utils;
import org.smartregister.view.activity.BaseLoginActivity;
import org.smartregister.view.contract.BaseLoginContract;

public class LoginActivity extends BaseLoginActivity implements BaseLoginContract.View {


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
        return R.layout.activity_login;
    }

    @Override
    protected void initializePresenter() {
        mLoginPresenter = new LoginPresenter(this);

    }

    @Override
    public void goToHome(boolean remote) {
        if (remote) {
            Utils.startAsyncTask(new SaveTeamLocationsTask(), null);
        }

        getToClientList(remote);

        finish();
    }

    private void getToClientList(boolean remote) {
        Intent intent = new Intent(this, ChildRegisterActivity.class);
        intent.putExtra(AppConstants.IntentKeyUtil.IS_REMOTE_LOGIN, remote);
        startActivity(intent);
    }

}