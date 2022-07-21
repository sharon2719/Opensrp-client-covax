package com.example.opensrp_client_covax.contract;

import com.example.opensrp_client_covax.cursor.AdvancedMatrixCursor;
import com.example.opensrp_client_covax.domain.Field;

import org.json.JSONArray;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.domain.Response;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

import java.util.List;
import java.util.Set;

public interface AppChildRegisterFragmentContract {
    interface View extends BaseRegisterFragmentContract.View {
        void initializeAdapter(Set<org.smartregister.configurableviews.model.View> visibleColumns);


        Presenter presenter();

        void recalculatePagination(AdvancedMatrixCursor matrixCursor);
    }

    interface Presenter extends BaseRegisterFragmentContract.Presenter {

        void updateSortAndFilter(List<Field> filterList, Field sortField);

        String getMainCondition();

        String getMainCondition(String tableName);

        String getDefaultSortQuery();

        String getDueFilterCondition();

    }

    interface Model {

        RegisterConfiguration defaultRegisterConfiguration();

        ViewConfiguration getViewConfiguration(String viewConfigurationIdentifier);

        Set<org.smartregister.configurableviews.model.View> getRegisterActiveColumns(String viewConfigurationIdentifier);

        String getFilterText(List<Field> filterList, String filter);

        String getSortText(Field sortField);

        JSONArray getJsonArray(Response<String> response);

        String mainSelect(String mainCondition);

        String countSelect(String mainCondition);
    }
}
