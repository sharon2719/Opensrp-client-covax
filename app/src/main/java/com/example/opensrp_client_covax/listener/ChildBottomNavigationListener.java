package com.example.opensrp_client_covax.listener;

import android.app.Activity;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.opensrp_client_covax.R;

import org.smartregister.listener.BottomNavigationListener;
import org.smartregister.view.activity.BaseRegisterActivity;

public class ChildBottomNavigationListener extends BottomNavigationListener {

    private final Activity context;

    public ChildBottomNavigationListener(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        super.onNavigationItemSelected(item);

        BaseRegisterActivity baseRegisterActivity = (BaseRegisterActivity) context;

        if (item.getItemId() == R.id.action_child) {
            baseRegisterActivity.switchToBaseFragment();
        }
//        } else if (item.getItemId() == R.id.action_register) {
//            baseRegisterActivity.startQrCodeScanner();
//        }

        return true;
    }
}
