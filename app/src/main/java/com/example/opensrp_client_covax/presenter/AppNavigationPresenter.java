package com.example.opensrp_client_covax.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.example.opensrp_client_covax.contract.AppNavigationContract;
import com.example.opensrp_client_covax.interactor.AppNavigationInteractor;
import com.example.opensrp_client_covax.job.ScheduleJob;
import com.example.opensrp_client_covax.model.AppNavigationModel;

import org.smartregister.Context;
import org.smartregister.job.ExtendedSyncServiceJob;
import org.smartregister.job.ImageUploadServiceJob;
import org.smartregister.job.PullUniqueIdsServiceJob;
import org.smartregister.job.SyncLocationsByLevelAndTagsServiceJob;
import org.smartregister.job.SyncServiceJob;
import org.smartregister.job.ValidateSyncDataServiceJob;
import org.smartregister.repository.AllSharedPreferences;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public class AppNavigationPresenter implements AppNavigationContract.Presenter {

    private final AppNavigationContract.Model model;
    private final AppNavigationContract.Interactor interactor;
    private final WeakReference<AppNavigationContract.View> view;

    public AppNavigationPresenter(AppNavigationContract.View view) {
        this.model = AppNavigationModel.getInstance();
        this.interactor = AppNavigationInteractor.getInstance();
        this.view = new WeakReference<>(view);
    }

    @Override
    public AppNavigationContract.View getNavigationView() {
        return view.get();
    }

    @Override
    public void refreshLastSync() {
        getNavigationView().refreshLastSync(interactor.sync());
    }

    @Override
    public void displayCurrentUser() {
        getNavigationView().refreshCurrentUser(model.getCurrentUser());
    }

    @Override
    public void sync(Activity activity) {
        SyncServiceJob.scheduleJobImmediately(SyncServiceJob.TAG);
        ExtendedSyncServiceJob.scheduleJobImmediately(ExtendedSyncServiceJob.TAG);
        PullUniqueIdsServiceJob.scheduleJobImmediately(PullUniqueIdsServiceJob.TAG);
        ValidateSyncDataServiceJob.scheduleJobImmediately(ValidateSyncDataServiceJob.TAG);
        ImageUploadServiceJob.scheduleJobImmediately(ImageUploadServiceJob.TAG);
        ScheduleJob.scheduleJobImmediately(ScheduleJob.TAG);
        SyncLocationsByLevelAndTagsServiceJob.scheduleJobImmediately(SyncLocationsByLevelAndTagsServiceJob.TAG);
    }

    @Override
    public String getLoggedInUserInitials() {
        try {
            AllSharedPreferences allSharedPreferences = getAllSharedPreferences();
            String preferredName = allSharedPreferences.getANMPreferredName(allSharedPreferences.fetchRegisteredANM());
            if (!TextUtils.isEmpty(preferredName)) {
                String[] initialsArray = preferredName.split(" ");
                String initials = "";
                if (initialsArray.length > 0) {
                    initials = initialsArray[0].substring(0, 1);
                    if (initialsArray.length > 1) {
                        initials = initials + initialsArray[1].substring(0, 1);
                    }
                }
                return initials.toUpperCase();
            }

        } catch (StringIndexOutOfBoundsException exception) {
            Timber.e(exception, "Error fetching initials");
        }

        return null;
    }


    public AllSharedPreferences getAllSharedPreferences() {
        return Context.getInstance().allSharedPreferences();
    }
}
