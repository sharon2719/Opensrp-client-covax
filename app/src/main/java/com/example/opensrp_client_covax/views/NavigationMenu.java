package com.example.opensrp_client_covax.views;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.opensrp_client_covax.contract.AppNavigationContract;

import org.smartregister.domain.FetchStatus;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;

import java.lang.ref.WeakReference;
import java.util.Date;

public class NavigationMenu implements AppNavigationContract.View, SyncStatusBroadcastReceiver.SyncStatusListener {

    private static NavigationMenu instance;
    private static WeakReference<Activity> activityWeakReference;
    private View parentView;
    private Toolbar toolbar;

    public static NavigationMenu getInstance(@NonNull Activity activity, View parentView, Toolbar myToolbar) {
        SyncStatusBroadcastReceiver.getInstance().removeSyncStatusListener(instance);
        int orientation = activity.getResources().getConfiguration().orientation;
        activityWeakReference = new WeakReference<>(activity);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (instance == null) {
                instance = new NavigationMenu();
//                langArray = activity.getResources().getStringArray(R.array.languages);

            }
            instance.init(activity);
            SyncStatusBroadcastReceiver.getInstance().addSyncStatusListener(instance);
            return instance;
        } else {
            return null;
        }

    }

    private void init(Activity activity) {
    }

    @Override
    public void prepareViews(Activity activity) {

    }

    @Override
    public void refreshLastSync(Date lastSync) {

    }

    @Override
    public void refreshCurrentUser(String name) {

    }

    @Override
    public void logout(Activity activity) {

    }

    @Override
    public void onSyncStart() {

    }

    @Override
    public void onSyncInProgress(FetchStatus fetchStatus) {

    }

    @Override
    public void onSyncComplete(FetchStatus fetchStatus) {

    }
}
