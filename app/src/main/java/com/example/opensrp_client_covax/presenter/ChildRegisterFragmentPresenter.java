package com.example.opensrp_client_covax.presenter;

import com.example.opensrp_client_covax.contract.ChildRegisterFragmentContract;
import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;
import com.example.opensrp_client_covax.util.AppConstants;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.child.model.BaseChildRegisterFragmentModel;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

import java.lang.ref.WeakReference;
import java.util.Set;

public class ChildRegisterFragmentPresenter implements BaseRegisterFragmentContract.Presenter {

    protected String viewConfigurationIdentifier;
    protected ChildRegisterFragmentContract.Model model;
    protected WeakReference<ChildRegisterFragmentContract.View> viewReference;
    protected RegisterConfiguration config;
    private Set<ChildRegisterFragmentContract.View> visibleColumns;

    public ChildRegisterFragmentPresenter(ChildRegisterFragment childRegisterFragment, BaseChildRegisterFragmentModel baseChildRegisterFragmentModel, String viewConfigurationIdentifier) {
    }


    @Override
    public void processViewConfigurations() {
        if (StringUtils.isBlank(viewConfigurationIdentifier)) {
            return;
        }

        ViewConfiguration viewConfiguration = model.getViewConfiguration(viewConfigurationIdentifier);
        if (viewConfiguration != null) {
            config = (RegisterConfiguration) viewConfiguration.getMetadata();
            this.visibleColumns = model.getRegisterActiveColumns(viewConfigurationIdentifier);
        }

    }

    public String getMainTable() {
        return AppConstants.TABLES.EC_CHILD;
    }

    @Override
    public void initializeQueries(String mainCondition) {
        String tableName = getMainTable();

        String countSelect = model.countSelect(tableName, mainCondition);
        String mainSelect = model.mainSelect(tableName, mainCondition);


    }

    protected ChildRegisterFragmentContract.View getView() {
        if (viewReference != null)
            return viewReference.get();
        else
            return null;
    }

    @Override
    public void startSync() {

    }

    @Override
    public void searchGlobally(String s) {

    }
}
