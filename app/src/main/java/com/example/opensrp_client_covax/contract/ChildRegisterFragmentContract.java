package com.example.opensrp_client_covax.contract;

import org.smartregister.configurableviews.model.Field;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ChildRegisterFragmentContract {


    Presenter presenter();

    void setUniqueID(String s);

    void setAdvancedSearchFormData(HashMap<String, String> hashMap);

    void showNotFoundPopup(String s);

    interface Presenter extends BaseRegisterFragmentContract.Presenter {

        void updateSortAndFilter(List<Field> filterList, Field sortField);

        String getMainCondition();

        String getDefaultSortQuery();


        String getDueFilterCondition();
    }

    interface Model{


        String countSelect(String tableName, String mainCondition);

        String mainSelect(String tableName, String mainCondition);

        Set getRegisterActiveColumns(String viewConfigurationIdentifier);

        ViewConfiguration getViewConfiguration(String viewConfigurationIdentifier);
    }

    public interface View {
        Presenter presenter();

    }
}
