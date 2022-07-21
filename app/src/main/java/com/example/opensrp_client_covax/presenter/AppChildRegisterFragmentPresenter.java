package com.example.opensrp_client_covax.presenter;

import com.example.opensrp_client_covax.contract.AppChildRegisterFragmentContract;
import com.example.opensrp_client_covax.cursor.AdvancedMatrixCursor;
import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;
import com.example.opensrp_client_covax.model.AppChildRegisterFragmentModel;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.configurableviews.model.Field;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.View;
import org.smartregister.configurableviews.model.ViewConfiguration;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AppChildRegisterFragmentPresenter implements AppChildRegisterFragmentContract.Presenter {
    protected AdvancedMatrixCursor matrixCursor;
    protected WeakReference<AppChildRegisterFragmentContract.View> viewReference;
    protected AppChildRegisterFragmentContract.Model model;
    protected String viewConfigurationIdentifier;
    protected RegisterConfiguration config;
    protected Set<org.smartregister.configurableviews.model.View> visibleColumns = new TreeSet<>();


    public AppChildRegisterFragmentPresenter(ChildRegisterFragment childRegisterFragment, AppChildRegisterFragmentModel appChildRegisterFragmentModel, String viewConfigurationIdentifier) {
        this.viewReference = new WeakReference<>(childRegisterFragment);
        this.viewConfigurationIdentifier = viewConfigurationIdentifier;
        this.model = appChildRegisterFragmentModel;
        this.config = model.defaultRegisterConfiguration();

    }

    @Override
    public void updateSortAndFilter(List<Field> filterList, Field sortField) {
        String filterText = model.getFilterText(filterList, getView().getString(org.smartregister.child.R.string.filter));
        String sortText = model.getSortText(sortField);

        getView().updateFilterAndFilterStatus(filterText, sortText);
    }

    private AppChildRegisterFragmentContract.View getView() {
        if (viewReference != null) return viewReference.get();
        else return null;
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
        if (StringUtils.isBlank(viewConfigurationIdentifier)) {
            return;
        }

        ViewConfiguration viewConfiguration = model.getViewConfiguration(viewConfigurationIdentifier);
        if (viewConfiguration != null) {
            config = (RegisterConfiguration) viewConfiguration.getMetadata();
            setVisibleColumns(model.getRegisterActiveColumns(viewConfigurationIdentifier));
        }

        if (config.getSearchBarText() != null && getView() != null) {
            getView().updateSearchBarHint(config.getSearchBarText());
        }
    }

    private void setVisibleColumns(Set<View> visibleColumns) {
        this.visibleColumns = visibleColumns;
    }

    @Override
    public void initializeQueries(String mainCondition) {
//        String tableName = Utils.metadata().getRegisterQueryProvider().getDemographicTable();
//
//        String countSelect = model.countSelect(mainCondition);
//        String mainSelect = model.mainSelect(mainCondition);
//
//        getView().initializeQueryParams(tableName, countSelect, mainSelect);
//        getView().initializeAdapter(visibleColumns);
//
//        getView().countExecute();
//        getView().filterandSortInInitializeQueries();
    }

    @Override
    public void startSync() {
// to do
    }

    @Override
    public void searchGlobally(String uniqueId) {
//        To do
    }


    public com.example.opensrp_client_covax.cursor.AdvancedMatrixCursor getMatrixCursor() {
        return matrixCursor;
    }
}
