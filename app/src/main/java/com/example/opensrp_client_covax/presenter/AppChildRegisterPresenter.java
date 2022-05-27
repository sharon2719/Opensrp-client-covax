package com.example.opensrp_client_covax.presenter;

import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.util.AppUtils;

import org.smartregister.child.contract.ChildRegisterContract;
import org.smartregister.child.domain.UpdateRegisterParams;
import org.smartregister.child.presenter.BaseChildRegisterPresenter;
import org.smartregister.repository.EventClientRepository;

public class AppChildRegisterPresenter extends BaseChildRegisterPresenter {
    private final EventClientRepository eventClientRepository = CovacsApplication.getInstance().eventClientRepository();

    public AppChildRegisterPresenter(ChildRegisterContract.View view, ChildRegisterContract.Model model) {
        super(view, model);
    }
    @Override
    public void onRegistrationSaved(boolean isEdit) {
        super.onRegistrationSaved(isEdit);
    }

    @Override
    public void saveForm(String jsonString, UpdateRegisterParams updateRegisterParams) {
        String jsonForm = AppUtils.validateChildZone(jsonString);
        super.saveForm(jsonForm, updateRegisterParams);
    }
}
