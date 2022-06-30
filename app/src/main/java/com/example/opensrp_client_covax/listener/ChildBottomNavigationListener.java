package com.example.opensrp_client_covax.listener;

import android.app.Activity;
import android.view.MenuItem;

import androidx.annotation.NonNull;


import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.activity.ChildRegisterActivity;

import org.smartregister.listener.BottomNavigationListener;
import org.smartregister.view.activity.BaseRegisterActivity;

public class ChildBottomNavigationListener extends BottomNavigationListener {
    private Activity context;

    public ChildBottomNavigationListener(Activity context) {
        super(context);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        ChildRegisterActivity childRegisterActivity = (ChildRegisterActivity) context;

        if (item.getItemId() == R.id.action_child) {
            childRegisterActivity.switchToBaseFragment();
        } else if (item.getItemId() == R.id.action_register) {
            childRegisterActivity.startRegistration();
            return false;
        }

        return true;
    }
    }

