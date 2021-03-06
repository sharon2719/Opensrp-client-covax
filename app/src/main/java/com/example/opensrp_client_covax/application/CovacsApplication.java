package com.example.opensrp_client_covax.application;

import static org.smartregister.BuildConfig.BUILD_TIMESTAMP;
import static org.smartregister.BuildConfig.DEBUG;

import android.content.Intent;
import android.util.Pair;

import androidx.appcompat.app.AppCompatDelegate;

import com.evernote.android.job.JobManager;
import com.example.opensrp_client_covax.BuildConfig;
import com.example.opensrp_client_covax.activity.ChildFormActivity;
import com.example.opensrp_client_covax.activity.ChildImmunizationActivity;
import com.example.opensrp_client_covax.activity.ChildProfileActivity;
import com.example.opensrp_client_covax.activity.ChildRegisterActivity;
import com.example.opensrp_client_covax.activity.LoginActivity;
import com.example.opensrp_client_covax.domain.ChildMetadata;
import com.example.opensrp_client_covax.job.CovacsJobCreator;
import com.example.opensrp_client_covax.provider.AppRegisterQueryProvider;
import com.example.opensrp_client_covax.repository.CovacsRepository;
import com.example.opensrp_client_covax.util.AppConstants;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.commonregistry.CommonFtsObject;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.helper.JsonSpecHelper;
import org.smartregister.immunization.ImmunizationLibrary;
import org.smartregister.immunization.repository.VaccineRepository;
import org.smartregister.location.helper.LocationHelper;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import org.smartregister.repository.EventClientRepository;
import org.smartregister.repository.Repository;
import org.smartregister.repository.UniqueIdRepository;
import org.smartregister.sync.helper.ECSyncHelper;
import org.smartregister.view.activity.DrishtiApplication;
import org.smartregister.view.receiver.TimeChangedBroadcastReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;


public class CovacsApplication extends DrishtiApplication implements TimeChangedBroadcastReceiver.OnTimeChangedListener {
    private static JsonSpecHelper jsonSpecHelper;
    private static CommonFtsObject commonFtsObject;
    private EventClientRepository eventClientRepository;
    private ECSyncHelper ecSyncHelper;
    private boolean isBulkProcessing;
    private UniqueIdRepository uniqueIdRepository;

    public static JsonSpecHelper getJsonSpecHelper() {
        return jsonSpecHelper;
    }

    public static synchronized CovacsApplication getInstance() {
        return (CovacsApplication) mInstance;
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
        return new String[]{AppConstants.RegisterTable.CLIENT};
    }

    private static String[] getFtsSearchFields(String tableName) {
        if (AppConstants.RegisterTable.CHILD_DETAILS.equals(tableName)) {
            return new String[]{
                    AppConstants.KeyConstants.ZEIR_ID,
                    AppConstants.KeyConstants.FIRST_NAME,
                    AppConstants.KeyConstants.LAST_NAME
            };
//            case AppConstants.RegisterTable.CHILD_DETAILS:
//                return new String[]{AppConstants.KeyConstants.LOST_TO_FOLLOW_UP, AppConstants.KeyConstants.INACTIVE};
        }
        return null;
    }

    public static Locale getCurrentLocale() {
        return mInstance == null ? Locale.getDefault() : mInstance.getResources().getConfiguration().locale;
    }

    private static String[] getFtsSortFields(String tableName, android.content.Context context) {

        return null;
    }

    private static Map<String, Pair<String, Boolean>> getAlertScheduleMap(android.content.Context context) {

        return null;
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
        CoreLibrary.init(context, new AppSyncConfiguration(), BUILD_TIMESTAMP);

        ImmunizationLibrary.init(context, getRepository(), createCommonFtsObject(context.applicationContext()),
                BuildConfig.VERSION_CODE, BuildConfig.DATABASE_VERSION);
        ImmunizationLibrary.getInstance().setVaccineSyncTime(3, TimeUnit.MINUTES);
        ConfigurableViewsLibrary.init(context);


        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!DEBUG);

        SyncStatusBroadcastReceiver.init(this);

        jsonSpecHelper = new JsonSpecHelper(this);

        //init Job Manager
        JobManager.create(this).addJobCreator(new CovacsJobCreator());
        SyncStatusBroadcastReceiver.init(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        LocationHelper.init(new ArrayList<>(Arrays.asList(BuildConfig.ALLOWED_LEVELS)), BuildConfig.DEFAULT_LOCATION);

        initRepositories();
    }

    private void initRepositories() {
        vaccineRepository();

    }

    public VaccineRepository vaccineRepository() {
        return ImmunizationLibrary.getInstance().vaccineRepository();
    }

    public ChildMetadata getMetadata() {
        ChildMetadata metadata = new ChildMetadata(ChildFormActivity.class, ChildProfileActivity.class,
                ChildImmunizationActivity.class, ChildRegisterActivity.class, true, new AppRegisterQueryProvider());
        metadata.updateChildRegister(AppConstants.JSON_FORM.CHILD_ENROLLMENT, AppConstants.RegisterTable.CLIENT,
                AppConstants.EVENT_TYPE.CHILD_REGISTRATION, AppConstants.EVENT_TYPE.UPDATE_CHILD_REGISTRATION, AppConstants.CONFIGURATION.CHILD_REGISTER);
        metadata.setFieldsWithLocationHierarchy(new HashSet<>(Arrays.asList("Home_Facility")));
        metadata.setLocationLevels(new ArrayList<>(Arrays.asList(BuildConfig.LOCATION_LEVELS)));
        metadata.setHealthFacilityLevels(new ArrayList<>(Arrays.asList(BuildConfig.HEALTH_FACILITY_LEVELS)));
        return metadata;
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

    public ECSyncHelper getEcSyncHelper() {
        if (ecSyncHelper == null) {
            ecSyncHelper = ECSyncHelper.getInstance(getApplicationContext());
        }
        return ecSyncHelper;
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

    @Override
    public Repository getRepository() {
        try {
            if (repository == null) {
                repository = new CovacsRepository(getApplicationContext(), context);
            }
        } catch (UnsatisfiedLinkError e) {
            Timber.e(e, "CovacsApplication --> getRepository");
        }
        return repository;
    }


    public UniqueIdRepository getUniqueIdRepository() {
        if (uniqueIdRepository == null) {
            uniqueIdRepository = new UniqueIdRepository();
        }
        return uniqueIdRepository;
    }
}
