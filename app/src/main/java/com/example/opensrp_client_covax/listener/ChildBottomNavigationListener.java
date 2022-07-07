package com.example.opensrp_client_covax.listener;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.activity.ChildRegisterActivity;

import org.smartregister.listener.BottomNavigationListener;

import java.lang.ref.WeakReference;

public class ChildBottomNavigationListener extends BottomNavigationListener {
    private WeakReference<ChildRegisterActivity> baseRegisterActivity;

    public ChildBottomNavigationListener(ChildRegisterActivity context) {
        super(context);
        this.baseRegisterActivity = new WeakReference<>(context);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setEnabled(false);
        if (item.getItemId() == R.id.action_home) {
            getBaseRegisterActivityReference().switchToBaseFragment();
        } else if (item.getItemId() == R.id.action_register) {
            getBaseRegisterActivityReference().startRegistration();
        }
        if (this.baseRegisterActivity.get() != null)
            this.baseRegisterActivity.get().setActiveMenuItem(item.getItemId());

        return true;
    }

    public ChildRegisterActivity getBaseRegisterActivityReference() {
        return baseRegisterActivity != null ? baseRegisterActivity.get() : null;
    }
}


