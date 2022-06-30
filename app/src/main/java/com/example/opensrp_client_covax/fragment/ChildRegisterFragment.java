package com.example.opensrp_client_covax.fragment;

import com.example.opensrp_client_covax.contract.AppChildRegisterFragmentContract;

import org.smartregister.configurableviews.model.View;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.HashMap;
import java.util.Set;

public class ChildRegisterFragment extends BaseRegisterFragment implements AppChildRegisterFragmentContract.View {

    @Override
    public void initializeAdapter(Set<View> visibleColumns) {

    }

    @Override
    public AppChildRegisterFragmentContract.Presenter presenter() {
        return null;
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public void setUniqueID(String qrCode) {

    }

    @Override
    public void setAdvancedSearchFormData(HashMap<String, String> advancedSearchFormData) {

    }

    @Override
    protected String getMainCondition() {
        return null;
    }

    @Override
    protected String getDefaultSortQuery() {
        return null;
    }

    @Override
    protected void startRegistration() {

    }

    @Override
    protected void onViewClicked(android.view.View view) {

    }

    @Override
    public void showNotFoundPopup(String opensrpId) {

    }
}
