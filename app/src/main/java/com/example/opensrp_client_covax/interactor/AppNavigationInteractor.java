package com.example.opensrp_client_covax.interactor;

import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.contract.AppNavigationContract;
import com.example.opensrp_client_covax.dao.NavigationDao;

import org.smartregister.util.AppExecutors;

import java.util.Date;

import timber.log.Timber;

public class AppNavigationInteractor implements AppNavigationContract.Interactor {
    private static AppNavigationInteractor instance;
    private AppExecutors appExecutors = new AppExecutors();
    private CovacsApplication covacsApplication;


    public static AppNavigationInteractor getInstance() {
        if (instance == null)
            instance = new AppNavigationInteractor();

        return instance;
    }

    @Override
    public Date sync() {
        Date res = null;
        try {
            res = new Date(getLastCheckTimeStamp());
        } catch (Exception e) {
            Timber.e(e.toString());
        }
        return res;
    }

    private Long getLastCheckTimeStamp() {
        return covacsApplication.getEcSyncHelper().getLastCheckTimeStamp();
    }

    @Override
    public void setApplication(CovacsApplication covacsApplication) {
        this.covacsApplication = covacsApplication;
    }

    @Override
    public void getRegisterCount(String tableName, AppNavigationContract.InteractorCallback<Integer> callback) {
        if (callback != null) {
            appExecutors.diskIO().execute(() -> {
                try {
                    final Integer finalCount = getCount(tableName);
                    appExecutors.mainThread().execute(() -> callback.onResult(finalCount));
                } catch (final Exception e) {
                    appExecutors.mainThread().execute(() -> callback.onError(e));
                }
            });

        }
    }

    private int getCount(String tableName) {
        return NavigationDao.getQueryCount("");
    }
}
