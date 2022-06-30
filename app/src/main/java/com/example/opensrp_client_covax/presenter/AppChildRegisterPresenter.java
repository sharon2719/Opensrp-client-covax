package com.example.opensrp_client_covax.presenter;

import com.example.opensrp_client_covax.activity.ChildRegisterActivity;
import com.example.opensrp_client_covax.contract.ChildRegisterContract;
import com.example.opensrp_client_covax.model.AppChildRegisterModel;



import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;


public class AppChildRegisterPresenter implements ChildRegisterContract.Presenter {
    private WeakReference<ChildRegisterContract.View> viewReference;
    private ChildRegisterContract.Model model;
    private ChildRegisterContract.Interactor interactor;

    public AppChildRegisterPresenter(ChildRegisterActivity childRegisterActivity, AppChildRegisterModel appChildRegisterModel) {
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
        String initials = model.getInitials();
        if (initials != null && getView() != null) {
            getView().updateInitialsText(initials);
        }
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
    public void saveForm(String jsonString, boolean isEditMode) {

    }
}
