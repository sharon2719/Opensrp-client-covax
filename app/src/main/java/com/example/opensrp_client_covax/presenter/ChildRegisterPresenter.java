package com.example.opensrp_client_covax.presenter;

import com.example.opensrp_client_covax.activity.ChildRegisterActivity;
import com.example.opensrp_client_covax.contract.ChildRegisterContract;
import com.example.opensrp_client_covax.model.ChildRegisterModel;

import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.util.Utils;
import org.smartregister.view.contract.BaseRegisterContract;

import java.lang.ref.WeakReference;
import java.util.List;

public class ChildRegisterPresenter implements BaseRegisterContract.Presenter {

    protected WeakReference viewReference;
    protected ChildRegisterContract.Interactor interactor;
    protected ChildRegisterContract.Model model;

    public ChildRegisterPresenter(ChildRegisterActivity childRegisterActivity, ChildRegisterModel childRegisterModel) {
    }

    @Override
    public void registerViewConfigurations(List<String> viewIdentifiers) {
        if (viewIdentifiers != null)
            ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().registerViewConfigurations(viewIdentifiers);


    }

    @Override
    public void unregisterViewConfiguration(List<String> viewIdentifiers) {
        if (viewIdentifiers != null)
            ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().unregisterViewConfiguration(viewIdentifiers);

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
        String initials = Utils.getUserInitials();
        if (getView() != null) {
            getView().updateInitialsText(initials);
        }
    }

    private BaseRegisterContract.View getView() {
        if (viewReference != null)
            return (BaseRegisterContract.View) viewReference.get();
        else
            return null;
    }
}

