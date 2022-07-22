package com.example.opensrp_client_covax.presenter;

import android.util.Log;

import com.example.opensrp_client_covax.activity.ChildRegisterActivity;
import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.contract.ChildRegisterContract;
import com.example.opensrp_client_covax.domain.ChildEventClient;
import com.example.opensrp_client_covax.domain.UpdateRegisterParams;
import com.example.opensrp_client_covax.interactor.AppChildRegisterInteractor;
import com.example.opensrp_client_covax.model.AppChildRegisterModel;

import org.smartregister.domain.FetchStatus;
import org.smartregister.repository.EventClientRepository;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import timber.log.Timber;


public class AppChildRegisterPresenter implements ChildRegisterContract.Presenter, ChildRegisterContract.InteractorCallBack {
    private final EventClientRepository eventClientRepository = CovacsApplication.getInstance().eventClientRepository();
    private WeakReference<ChildRegisterContract.View> viewReference;
    private ChildRegisterContract.Model model;
    private ChildRegisterContract.Interactor interactor;

    public AppChildRegisterPresenter(ChildRegisterActivity childRegisterActivity, AppChildRegisterModel appChildRegisterModel) {
        this.model = appChildRegisterModel;
        this.viewReference = new WeakReference<>(childRegisterActivity);
        interactor = new AppChildRegisterInteractor();
    }

    @Override
    public void registerViewConfigurations(List<String> viewIdentifiers) {
        model.registerViewConfigurations(viewIdentifiers);
    }

    @Override
    public void unregisterViewConfiguration(List<String> viewIdentifiers) {
        model.unregisterViewConfigurations(viewIdentifiers);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        viewReference = null;//set to null on destroy
        // Inform interactor
        interactor.onDestroy(isChangingConfiguration);
        // Activity destroyed set interactor to null
        if (!isChangingConfiguration) {
            interactor = null;
            model = null;
        }
    }

    @Override
    public void updateInitials() {
//        do nothing
    }

    public ChildRegisterContract.View getView() {
        if (viewReference != null) {
            return viewReference.get();
        } else {
            return null;
        }
    }

    @Override
    public void startRegistrationForm(String formName, String updateEventType, String entityId, Map<String, String> valueMap) throws Exception {

    }

    @Override
    public void saveForm(String jsonString, UpdateRegisterParams updateRegisterParams) {
        try {

            List<ChildEventClient> childEventClientList = model.processRegistration(jsonString, updateRegisterParams.getFormTag());
            if (childEventClientList == null || childEventClientList.isEmpty()) {
                return;
            }

            interactor.saveRegistration(childEventClientList, jsonString, updateRegisterParams, this);

        } catch (Exception e) {
            Timber.e(Log.getStackTraceString(e));
        }
    }

    @Override
    public void onRegistrationSaved(boolean isEdit) {
        getView().refreshList(FetchStatus.fetched);
        getView().hideProgressDialog();
    }
}
