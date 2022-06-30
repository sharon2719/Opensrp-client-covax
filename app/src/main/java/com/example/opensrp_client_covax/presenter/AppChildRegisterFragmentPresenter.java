package com.example.opensrp_client_covax.presenter;

import com.example.opensrp_client_covax.contract.AppChildRegisterFragmentContract;
import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;
import com.example.opensrp_client_covax.model.AppChildRegisterFragmentModel;

import org.smartregister.configurableviews.model.Field;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

import java.util.List;

public class AppChildRegisterFragmentPresenter implements AppChildRegisterFragmentContract.Presenter {
    public AppChildRegisterFragmentPresenter(ChildRegisterFragment childRegisterFragment, AppChildRegisterFragmentModel appChildRegisterFragmentModel, String viewConfigurationIdentifier) {
    }

    @Override
    public void updateSortAndFilter(List<Field> filterList, Field sortField) {

    }

    @Override
    public String getMainCondition() {
        return null;
    }

    @Override
    public String getMainCondition(String tableName) {
        return null;
    }

    @Override
    public String getDefaultSortQuery() {
        return null;
    }

    @Override
    public String getDueFilterCondition() {
        return null;
    }

    @Override
    public void processViewConfigurations() {

    }

    @Override
    public void initializeQueries(String mainCondition) {

    }

    @Override
    public void startSync() {

    }

    @Override
    public void searchGlobally(String uniqueId) {

    }
}
