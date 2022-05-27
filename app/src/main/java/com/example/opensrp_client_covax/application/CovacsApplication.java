package com.example.opensrp_client_covax.application;

import static org.smartregister.child.util.Utils.context;

import android.content.Intent;
import android.util.Pair;

import androidx.appcompat.app.AppCompatDelegate;

import com.evernote.android.job.JobManager;
import com.example.opensrp_client_covax.activity.LoginActivity;
import com.example.opensrp_client_covax.util.AppConstants;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.greenrobot.eventbus.EventBus;
import org.smartregister.BuildConfig;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.child.ChildLibrary;
import org.smartregister.child.util.ChildAppProperties;
import org.smartregister.child.util.DBConstants;
import org.smartregister.commonregistry.CommonFtsObject;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.helper.JsonSpecHelper;
import org.smartregister.growthmonitoring.GrowthMonitoringConfig;
import org.smartregister.growthmonitoring.GrowthMonitoringLibrary;
import org.smartregister.immunization.ImmunizationLibrary;
import org.smartregister.immunization.domain.jsonmapping.VaccineGroup;
import org.smartregister.immunization.util.VaccinatorUtils;
import org.smartregister.location.helper.LocationHelper;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import org.smartregister.reporting.ReportingLibrary;
import org.smartregister.repository.EventClientRepository;
import org.smartregister.stock.StockLibrary;
import org.smartregister.sync.helper.ECSyncHelper;
import org.smartregister.view.activity.DrishtiApplication;
import org.smartregister.view.receiver.TimeChangedBroadcastReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class CovacsApplication extends DrishtiApplication implements TimeChangedBroadcastReceiver.OnTimeChangedListener {
    private static JsonSpecHelper jsonSpecHelper;
    private static CommonFtsObject commonFtsObject;
    private EventClientRepository eventClientRepository;
    private ECSyncHelper ecSyncHelper;


    public static JsonSpecHelper getJsonSpecHelper() {
        return jsonSpecHelper;
    }

    public static synchronized CovacsApplication getInstance() {
        return (CovacsApplication) mInstance;
    }

    @Override
    public void logoutCurrentUser() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getApplicationContext().startActivity(intent);
        context.userService().logoutSession();
    }

    @Override
    public void onTimeChanged() {
        context.userService().forceRemoteLogin(context().allSharedPreferences().fetchRegisteredANM());
        logoutCurrentUser();
    }

    @Override
    public void onTimeZoneChanged() {
        context.userService().forceRemoteLogin(context().allSharedPreferences().fetchRegisteredANM());
        logoutCurrentUser();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = Context.getInstance();

        context.updateApplicationContext(getApplicationContext());
        context.updateCommonFtsObject(createCommonFtsObject(context.applicationContext()));

        //Initialize Modules
        CoreLibrary.init(context, new AppSyncConfiguration(), BuildConfig.BUILD_TIMESTAMP);

//        GrowthMonitoringConfig growthMonitoringConfig = new GrowthMonitoringConfig();
//        growthMonitoringConfig.setWeightForHeightZScoreFile("weight_for_height.csv");

//        GrowthMonitoringLibrary.getInstance().setGrowthMonitoringSyncTime(3, TimeUnit.MINUTES);


//        ImmunizationLibrary.getInstance().setVaccineSyncTime(3, TimeUnit.MINUTES);


        ConfigurableViewsLibrary.init(context);


//        ChildLibrary childLibrary = ChildLibrary.getInstance();
//        childLibrary.setApplicationVersionName(BuildConfig.VERSION_NAME);
//        childLibrary.setClientProcessorForJava(getClientProcessor());
//        childLibrary.getProperties().setProperty(ChildAppProperties.KEY.FEATURE_SCAN_QR_ENABLED, "true");
//        childLibrary.setEventBus(EventBus.getDefault());




        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG);



        SyncStatusBroadcastReceiver.init(this);

        jsonSpecHelper = new JsonSpecHelper(this);

        //init Job Manager

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


    }
    public Context context() {
        return context;
    }

    public EventClientRepository eventClientRepository() {
        if (eventClientRepository == null) {
            eventClientRepository = new EventClientRepository();
        }
        return eventClientRepository;
    }
    public static CommonFtsObject createCommonFtsObject(android.content.Context context) {
        if (commonFtsObject == null) {
            commonFtsObject = new CommonFtsObject(getFtsTables());
            for (String ftsTable : commonFtsObject.getTables()) {
                commonFtsObject.updateSearchFields(ftsTable, getFtsSearchFields(ftsTable));
                commonFtsObject.updateSortFields(ftsTable, getFtsSortFields(ftsTable, context));
            }
        }
        commonFtsObject.updateAlertScheduleMap(getAlertScheduleMap(context));

        return commonFtsObject;
    }

    public ECSyncHelper getEcSyncHelper() {
        if (ecSyncHelper == null) {
            ecSyncHelper = ECSyncHelper.getInstance(getApplicationContext());
        }
        return ecSyncHelper;
    }

    private static String[] getFtsTables() {
        return new String[]{DBConstants.RegisterTable.CLIENT, DBConstants.RegisterTable.MOTHER_DETAILS, DBConstants.RegisterTable.CHILD_DETAILS};
    }
    private static String[] getFtsSearchFields(String tableName) {
        switch (tableName) {
            case DBConstants.RegisterTable.CLIENT:
                return new String[]{
                        DBConstants.KEY.ZEIR_ID,
                        DBConstants.KEY.FIRST_NAME,
                        DBConstants.KEY.LAST_NAME
                };
            case DBConstants.RegisterTable.CHILD_DETAILS:
                return new String[]{DBConstants.KEY.LOST_TO_FOLLOW_UP, DBConstants.KEY.INACTIVE};

            default:
                return null;
        }
    }
    private static String[] getFtsSortFields(String tableName, android.content.Context context) {
//        switch (tableName) {
//            case AppConstants.TableNameConstants.ALL_CLIENTS:
//                return Arrays.asList(AppConstants.KeyConstants.FIRST_NAME, AppConstants.KeyConstants.LAST_NAME,
//                        AppConstants.KeyConstants.DOB, AppConstants.KeyConstants.ZEIR_ID, AppConstants.KeyConstants.LAST_INTERACTED_WITH,
//                        AppConstants.KeyConstants.DOD, AppConstants.KeyConstants.DATE_REMOVED).toArray(new String[0]);
//            case DBConstants.RegisterTable.CHILD_DETAILS:
//                List<VaccineGroup> vaccineList = VaccinatorUtils.getVaccineGroupsFromVaccineConfigFile(context, VaccinatorUtils.vaccines_file);
//                List<String> names = new ArrayList<>();
//                names.add(DBConstants.KEY.INACTIVE);
//                names.add(AppConstants.KeyConstants.RELATIONAL_ID);
//                names.add(DBConstants.KEY.LOST_TO_FOLLOW_UP);
//
//                for (VaccineGroup vaccineGroup : vaccineList) {
//                    populateAlertColumnNames(vaccineGroup.vaccines, names);
//                }
//
//                return names.toArray(new String[0]);
//
//            default:
//                return null;
//        }
        return null;
    }
    private static Map<String, Pair<String, Boolean>> getAlertScheduleMap(android.content.Context context) {
//        List<VaccineGroup> vaccines = getVaccineGroups(context);
//
//        Map<String, Pair<String, Boolean>> map = new HashMap<>();
//
//        for (VaccineGroup vaccineGroup : vaccines) {
//            populateAlertScheduleMap(vaccineGroup.vaccines, map);
//        }
        return null;
    }
}
