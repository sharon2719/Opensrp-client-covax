package com.example.opensrp_client_covax.application;

import android.content.Intent;
import android.util.Pair;

import androidx.appcompat.app.AppCompatDelegate;

import com.evernote.android.job.JobManager;
import com.example.opensrp_client_covax.job.CovacsJobCreator;
import com.example.opensrp_client_covax.activity.LoginActivity;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.BuildConfig;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.child.util.DBConstants;
import org.smartregister.commonregistry.CommonFtsObject;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.helper.JsonSpecHelper;
import org.smartregister.location.helper.LocationHelper;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import org.smartregister.repository.EventClientRepository;
import org.smartregister.repository.UniqueIdRepository;
import org.smartregister.sync.ClientProcessorForJava;
import org.smartregister.sync.helper.ECSyncHelper;
import org.smartregister.util.AppExecutors;
import org.smartregister.view.activity.DrishtiApplication;
import org.smartregister.view.receiver.TimeChangedBroadcastReceiver;

import java.util.Locale;
import java.util.Map;


public class CovacsApplication extends DrishtiApplication implements TimeChangedBroadcastReceiver.OnTimeChangedListener {
    private static JsonSpecHelper jsonSpecHelper;
    private static CommonFtsObject commonFtsObject;
    private EventClientRepository eventClientRepository;
    private ECSyncHelper ecSyncHelper;
    private boolean isBulkProcessing;

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

        ConfigurableViewsLibrary.init(context);

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG);

        SyncStatusBroadcastReceiver.init(this);

        jsonSpecHelper = new JsonSpecHelper(this);

        //init Job Manager
        JobManager.create(this).addJobCreator(new CovacsJobCreator());

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

    public static Locale getCurrentLocale() {
        return mInstance == null ? Locale.getDefault() : mInstance.getResources().getConfiguration().locale;
    }

//    public static ClientProcessorForJava getClientProcessor(android.content.Context context) {
//        if (clientProcessor == null) {
//            clientProcessor = CoreClientProcessor.getInstance(context);
//        }
//        return clientProcessor;
//    }
public ECSyncHelper getEcSyncHelper() {
    if (ecSyncHelper == null) {
        ecSyncHelper = ECSyncHelper.getInstance(getApplicationContext());
    }
    return ecSyncHelper;
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
    public String getSyncLocations() {
        if (LocationHelper.getInstance() != null && LocationHelper.getInstance().locationIdsFromHierarchy() != null)
            return LocationHelper.getInstance().locationIdsFromHierarchy();
        return "";
    }
    public boolean allowLazyProcessing() {
        return true;
    }

    public boolean isBulkProcessing() {
        return isBulkProcessing;
    }

    public void setBulkProcessing(boolean bulkProcessing) {
        isBulkProcessing = bulkProcessing;
    }

}
