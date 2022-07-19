package com.example.opensrp_client_covax.interactor;

import com.example.opensrp_client_covax.contract.ChildRegisterContract;
import com.example.opensrp_client_covax.domain.ChildEventClient;
import com.example.opensrp_client_covax.domain.UpdateRegisterParams;

import org.smartregister.util.AppExecutors;

import java.util.List;

public class AppChildRegisterInteractor implements ChildRegisterContract.Interactor {
    private final AppExecutors appExecutors;

    public AppChildRegisterInteractor() {
        this(new AppExecutors());
    }

    public AppChildRegisterInteractor(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    @Override
    public void saveRegistration(List<com.example.opensrp_client_covax.domain.ChildEventClient> childEventClientList, String jsonString,
                                 com.example.opensrp_client_covax.domain.UpdateRegisterParams updateRegisterParams,
                                 ChildRegisterContract.InteractorCallBack callBack) {
        Runnable runnable = () -> {
            saveRegistration(childEventClientList, jsonString, updateRegisterParams);
            appExecutors.mainThread().execute(() -> callBack.onRegistrationSaved(updateRegisterParams.isEditMode()));
        };

        appExecutors.diskIO().execute(runnable);
    }


    private void saveRegistration(List<ChildEventClient> childEventClientList, String jsonString, UpdateRegisterParams params) {

    }


    @Override
    public void onDestroy(boolean isChangingConfiguration) {

    }
}
