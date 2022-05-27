package com.example.opensrp_client_covax.util;

import androidx.appcompat.app.AppCompatDelegate;

import com.evernote.android.job.JobManager;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.greenrobot.eventbus.EventBus;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.child.ChildLibrary;
import org.smartregister.child.util.ChildAppProperties;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.helper.JsonSpecHelper;
import org.smartregister.growthmonitoring.GrowthMonitoringConfig;
import org.smartregister.growthmonitoring.GrowthMonitoringLibrary;
import org.smartregister.immunization.ImmunizationLibrary;
import org.smartregister.location.helper.LocationHelper;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import org.smartregister.reporting.ReportingLibrary;
import org.smartregister.stock.StockLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class AppConstants {
    public static final class KeyConstants {
        public static final String VIEW_CONFIGURATION_PREFIX = "ViewConfiguration_";
        public static final String REGISTRATION_LOCATION_ID = "registration_location_id";
        public static final String REGISTRATION_LOCATION_NAME = "registration_location_name";
        public static final String KEY = "key";
        public static final String CHILD_ZONE = "child_zone";

        public static final String LAST_INTERACTED_WITH = "last_interacted_with";

    }
    public static class ConfigurationConstants {
        public static final String LOGIN = "login";

    }

    public interface IntentKeyUtil {
        String IS_REMOTE_LOGIN = "is_remote_login";
    }
    public interface JsonForm {
        String CHILD_ENROLLMENT = "child_enrollment";

    }


    }
