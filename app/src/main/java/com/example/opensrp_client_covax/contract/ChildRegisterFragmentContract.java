package com.example.opensrp_client_covax.contract;

import android.content.Context;

import org.smartregister.child.cursor.AdvancedMatrixCursor;
import org.smartregister.configurableviews.model.Field;
import org.smartregister.configurableviews.model.View;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ChildRegisterFragmentContract {


    interface Presenter extends BaseRegisterFragmentContract.Presenter, org.smartregister.child.contract.ChildRegisterFragmentContract.Presenter {

        @Override
        void updateSortAndFilter(List<Field> list, Field field);

        String getMainCondition();

        String getDefaultSortQuery();

        @Override
        void processViewConfigurations();

        @Override
        void initializeQueries(String mainCondition);

        @Override
        void startSync();

        @Override
        void searchGlobally(String uniqueId);
    }

    interface Model{


        String countSelect(String tableName, String mainCondition);

        String mainSelect(String tableName, String mainCondition);

        Set getRegisterActiveColumns(String viewConfigurationIdentifier);

        ViewConfiguration getViewConfiguration(String viewConfigurationIdentifier);
    }

    public interface View extends org.smartregister.child.contract.ChildRegisterFragmentContract.View {
        @Override
        void initializeAdapter(Set<org.smartregister.configurableviews.model.View> set);

        @Override
        void recalculatePagination(AdvancedMatrixCursor advancedMatrixCursor);



        @Override
        void initializeQueryParams(String tableName, String countSelect, String mainSelect);

        @Override
        void countExecute();

        @Override
        void filterandSortInInitializeQueries();

        @Override
        void updateSearchBarHint(String searchBarText);

        @Override
        Context getContext();

        @Override
        String getString(int resId);

        @Override
        void updateFilterAndFilterStatus(String filterText, String sortText);

        @Override
        void showProgressView();

        @Override
        void hideProgressView();

        @Override
        void showNotFoundPopup(String opensrpId);

        @Override
        void setTotalPatients();
    }
}
