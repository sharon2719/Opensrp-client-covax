package com.example.opensrp_client_covax.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.activity.ChildRegisterActivity;
import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.contract.AppNavigationContract;
import com.example.opensrp_client_covax.presenter.AppNavigationPresenter;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.smartregister.domain.FetchStatus;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import org.smartregister.util.LangUtils;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class NavigationMenu implements AppNavigationContract.View, SyncStatusBroadcastReceiver.SyncStatusListener {

    private static NavigationMenu instance;
    private static WeakReference<Activity> activityWeakReference;
    private LinearLayout registerView;
    private View parentView;
    private Toolbar toolbar;
    private static String[] langArray;
    private DrawerLayout drawer;
    private ImageButton cancelButton;
    private Spinner languageSpinner;
    private AppNavigationContract.Presenter mPresenter;
    private TextView syncTextView;
    private TextView logoutButton;
    private TextView loggedInUserTextView;
    private LinearLayout syncMenuItem;
    private TextView userInitialsTextView;

    @Nullable
    public static NavigationMenu getInstance(@NonNull Activity activity) {
        SyncStatusBroadcastReceiver.getInstance().removeSyncStatusListener(instance);
        int orientation = activity.getResources().getConfiguration().orientation;
        activityWeakReference = new WeakReference<>(activity);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (instance == null) {
                instance = new NavigationMenu();
                langArray = activity.getResources().getStringArray(R.array.languages);

            }
            instance.init(activity);
            SyncStatusBroadcastReceiver.getInstance().addSyncStatusListener(instance);
            return instance;
        } else {
            return null;
        }

    }



    private void init(Activity activity) {
        try {

            mPresenter = new AppNavigationPresenter(this);
            registerDrawer(activity);
            setParentView(activity);
            prepareViews(activity);
            appLogout(activity);
            syncApp(activity);
            attachCloseDrawer();
            goToRegister();
            attachLanguageSpinner(activity);
        } catch (Exception e) {
            Timber.e(e.toString());
        }
    }

    private void attachLanguageSpinner(Activity activity) {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activityWeakReference.get(), R.array.languages, R.layout.app_spinner_item);

        adapter.setDropDownViewResource(R.layout.item_spinner);

        languageSpinner.setAdapter(adapter);
        languageSpinner.setOnItemSelectedListener(null);
        String langPref = LangUtils.getLanguage(activity);
        for (int i = 0; i < langArray.length; i++) {
            if (langPref != null && langArray[i].toLowerCase().startsWith(langPref)) {
                languageSpinner.setSelection(i);
                break;
            } else {
                languageSpinner.setSelection(0);
            }
        }

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int count = 0;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (count >= 1) {
                    String lang = adapter.getItem(i).toString().toLowerCase();
                    Locale LOCALE;
                    switch (lang) {
                        case "swahili":
                            LOCALE = Locale.FRENCH;
                            languageSpinner.setSelection(i);
                            break;
                        case "english":
                        default:
                            LOCALE = Locale.ENGLISH;
                            break;
                    }

                    // save language
                    LangUtils.saveLanguage(activity.getApplicationContext(), LOCALE.getLanguage());

                    // refresh activity
                    refresh(activity);
                }
                count++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });
    }
    private void refresh(Context activity){

        Intent intent = new Intent(activity, activity.getClass());
        activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    private void goToRegister() {
        registerView.setOnClickListener(v -> {
            if (activityWeakReference.get() instanceof ChildRegisterActivity) {
                drawer.closeDrawer(GravityCompat.START);
                return;
            }
            Intent intent = new Intent(activityWeakReference.get(), ChildRegisterActivity.class);
            activityWeakReference.get().startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        });
    }

    private void syncApp(Activity parentActivity) {
        syncMenuItem.setOnClickListener(v -> {
            mPresenter.sync(parentActivity);
            Timber.i("Sync service started");
        });
    }

    private void attachCloseDrawer() {
        cancelButton.setOnClickListener(v -> {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void appLogout(Activity parentActivity) {
        mPresenter.displayCurrentUser();
        logoutButton.setOnClickListener(v -> logout(parentActivity));
    }

    private void setParentView(Activity activity) {
        ViewGroup current = (ViewGroup) ((ViewGroup) (activity.findViewById(android.R.id.content))).getChildAt(0);
        if (!(current instanceof DrawerLayout)) {
            if (current.getParent() != null) {
                ((ViewGroup) current.getParent()).removeView(current);
            }

            LayoutInflater mInflater = LayoutInflater.from(activity);
            ViewGroup contentView = (ViewGroup) mInflater.inflate(R.layout.navigation_drawer, null);
            activity.setContentView(contentView);

            RelativeLayout relativeLayout = activity.findViewById(R.id.navigation_content);

            if (current.getParent() != null) {
                ((ViewGroup) current.getParent()).removeView(current);
            }

            if (current instanceof RelativeLayout) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                current.setLayoutParams(params);
            }
            relativeLayout.addView(current);
        }
    }

    private void registerDrawer(Activity parentActivity) {
        if (drawer != null) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    parentActivity, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    @Override
    public void prepareViews(Activity activity) {
        drawer = activity.findViewById(R.id.drawer_layout);
        logoutButton = activity.findViewById(R.id.logout_button);
        syncMenuItem = activity.findViewById(R.id.sync_menu);
        registerView = activity.findViewById(R.id.register_view);
        loggedInUserTextView = activity.findViewById(R.id.logged_in_user_text_view);
        userInitialsTextView = activity.findViewById(R.id.user_initials_text_view);
        syncTextView = activity.findViewById(R.id.sync_text_view);
        cancelButton = drawer.findViewById(R.id.cancel_button);
        languageSpinner = activity.findViewById(R.id.language_spinner);
        mPresenter.refreshLastSync();


    }

    @Override
    public void refreshLastSync(Date lastSync) {
        if (syncTextView != null) {
            String lastSyncTime = getLastSyncTime();
            if (lastSync != null && !TextUtils.isEmpty(lastSyncTime)) {
                lastSyncTime = " " + String.format(activityWeakReference.get().getResources().getString(R.string.last_sync), lastSyncTime);
                syncTextView.setText(lastSyncTime);
            }
        }
    }

    private String getLastSyncTime() {
        String lastSync = "";
        long milliseconds = CovacsApplication.getInstance().getEcSyncHelper().getLastCheckTimeStamp();
        if (milliseconds > 0) {
            DateTime lastSyncTime = new DateTime(milliseconds);
            DateTime now = new DateTime(Calendar.getInstance());
            Minutes minutes = Minutes.minutesBetween(lastSyncTime, now);
            if (minutes.getMinutes() < 1) {
                Seconds seconds = Seconds.secondsBetween(lastSyncTime, now);
                lastSync = activityWeakReference.get().getString(R.string.x_seconds, seconds.getSeconds());
            } else if (minutes.getMinutes() >= 1 && minutes.getMinutes() < 60) {
                lastSync = activityWeakReference.get().getString(R.string.x_minutes, minutes.getMinutes());
            } else if (minutes.getMinutes() >= 60 && minutes.getMinutes() < 1440) {
                Hours hours = Hours.hoursBetween(lastSyncTime, now);
                lastSync = activityWeakReference.get().getString(R.string.x_hours, hours.getHours());
            } else {
                Days days = Days.daysBetween(lastSyncTime, now);
                lastSync = activityWeakReference.get().getString(R.string.x_days, days.getDays());
            }
        }
        return lastSync;
    }
    public DrawerLayout getDrawer() {
        return drawer;
    }

    @Override
    public void refreshCurrentUser(String name) {
        if (loggedInUserTextView != null) {
            loggedInUserTextView.setText(name);
        }
        if (userInitialsTextView != null && mPresenter.getLoggedInUserInitials() != null) {
            userInitialsTextView.setText(mPresenter.getLoggedInUserInitials());
        }
    }

    @Override
    public void logout(Activity activity) {
        Toast.makeText(activity, activity.getResources().getText(R.string.action_log_out),
                Toast.LENGTH_SHORT).show();
        CovacsApplication.getInstance().logoutCurrentUser();
    }


    @Override
    public void onSyncStart() {
//        Do nothing
    }

    @Override
    public void onSyncInProgress(FetchStatus fetchStatus) {
//        Do nothing
    }

    @Override
    public void onSyncComplete(FetchStatus fetchStatus) {
        if (!fetchStatus.equals(FetchStatus.fetchedFailed) && !fetchStatus.equals(FetchStatus.noConnection)) {
            mPresenter.refreshLastSync();
        }
    }
        public void openDrawer() {
            drawer.openDrawer(GravityCompat.START);
        }


}
