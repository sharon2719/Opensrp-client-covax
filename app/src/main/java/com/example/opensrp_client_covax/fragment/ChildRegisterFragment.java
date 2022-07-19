package com.example.opensrp_client_covax.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.activity.ChildProfileActivity;
import com.example.opensrp_client_covax.activity.ChildRegisterActivity;
import com.example.opensrp_client_covax.contract.AppChildRegisterFragmentContract;
import com.example.opensrp_client_covax.cursor.AdvancedMatrixCursor;
import com.example.opensrp_client_covax.holders.RepositoryHolder;
import com.example.opensrp_client_covax.model.AppChildRegisterFragmentModel;
import com.example.opensrp_client_covax.presenter.AppChildRegisterFragmentPresenter;
import com.example.opensrp_client_covax.provider.AppChildRegisterProvider;
import com.example.opensrp_client_covax.util.AppConstants;
import com.example.opensrp_client_covax.util.Utils;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.cursoradapter.SmartRegisterQueryBuilder;
import org.smartregister.immunization.ImmunizationLibrary;
import org.smartregister.view.LocationPickerView;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

public class ChildRegisterFragment extends BaseRegisterFragment implements AppChildRegisterFragmentContract.View, android.view.View.OnClickListener, LocationPickerView.OnLocationChangeListener {
    public static final String CLICK_VIEW_NORMAL = "click_view_normal";
    protected android.view.View view;

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        android.view.View view = inflater.inflate(R.layout.activity_child_register, container, false);
        mView = view;
        onInitialization();
        setupViews(view);
        onResumption();

        return view;
    }

    @Override
    public void initializeAdapter(Set<org.smartregister.configurableviews.model.View> visibleColumns) {
        RepositoryHolder repositoryHolder = new RepositoryHolder();
        repositoryHolder.setCommonRepository(commonRepository());
        repositoryHolder.setVaccineRepository(ImmunizationLibrary.getInstance().vaccineRepository());


        AppChildRegisterProvider childRegisterProvider = new AppChildRegisterProvider(requireActivity(), repositoryHolder,
                visibleColumns, paginationViewHandler);
        clientAdapter = new RecyclerViewPaginatedAdapter(null, childRegisterProvider, context().commonrepository(this.tablename));
        clientAdapter.setCurrentlimit(20);
        clientsView.setAdapter(clientAdapter);
    }


    @Override
    public AppChildRegisterFragmentContract.Presenter presenter() {
        return (AppChildRegisterFragmentContract.Presenter) presenter;
    }

    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }
        String viewConfigurationIdentifier = ((BaseRegisterActivity) getActivity()).getViewIdentifiers().get(0);
        presenter = new AppChildRegisterFragmentPresenter(this, new AppChildRegisterFragmentModel(), viewConfigurationIdentifier);
    }


    @Override
    public void setUniqueID(String s) {
        if (getSearchView() != null) {
            getSearchView().setText(s);
        }
    }

    @Override
    public void setAdvancedSearchFormData(HashMap<String, String> advancedSearchFormData) {
//do nothing
    }

    @Override
    protected String getMainCondition() {
        return presenter().getMainCondition();
    }

    @Override
    protected String getDefaultSortQuery() {
        return presenter().getDefaultSortQuery();
    }

    @Override
    protected void startRegistration() {
        ((ChildRegisterActivity) getActivity()).startRegistration();
    }

    @Override
    public void onViewClicked(android.view.View view) {
        if (getActivity() == null) {
            return;
        }
//
//        if (view.getTag() != null && view.getTag(R.id.VIEW_ID) == CLICK_VIEW_NORMAL) {
//            if (view.getTag() instanceof CommonPersonObjectClient) {
//                goToChildDetailActivity((CommonPersonObjectClient) view.getTag(), false);
//            }
//        }
    }

    private void goToChildDetailActivity(CommonPersonObjectClient person, boolean launchDialog) {
        if (launchDialog) {
            Timber.i(person.name);
        }

        Intent intent = new Intent(getActivity(), ChildProfileActivity.class);
        intent.putExtra(AppConstants.INTENT_KEY.BASE_ENTITY_ID, person.getCaseId());
        startActivity(intent);
    }

    @Override
    public void showNotFoundPopup(String opensrpId) {
//        if (getActivity() == null) {
//            return;
//        }
//        NoMatchDialogFragment.launchDialog((BaseRegisterActivity) getActivity(), DIALOG_TAG, opensrpId);
    }

    @Override
    public void setupViews(android.view.View view) {
        super.setupViews(view);
//        TODO setup
    }

    @Override
    public void recalculatePagination(AdvancedMatrixCursor matrixCursor) {
        clientAdapter.setTotalcount(matrixCursor.getCount());
        Timber.v("Total count here%s", clientAdapter.getTotalcount());
        clientAdapter.setCurrentlimit(20);
        if (clientAdapter.getTotalcount() > 0) {
            clientAdapter.setCurrentlimit(clientAdapter.getTotalcount());
        }
        clientAdapter.setCurrentoffset(0);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final AdvancedMatrixCursor matrixCursor = ((AppChildRegisterFragmentPresenter) presenter).getMatrixCursor();
        if (!globalQrSearch || matrixCursor == null) {
            if (id == LOADER_ID) {
                return new CursorLoader(getActivity()) {
                    @Override
                    public Cursor loadInBackground() {
                        String query = filterAndSortQuery();
                        return commonRepository().rawCustomQueryForAdapter(query);
                    }
                };
            } else {
                return new CursorLoader(getContext());
            }
        } else {
            globalQrSearch = false;
            if (id == LOADER_ID) {// Returns a new CursorLoader
                return new CursorLoader(requireActivity()) {
                    @Override
                    public Cursor loadInBackground() {
                        return matrixCursor;
                    }
                };
            }// An invalid id was passed in
            return new CursorLoader(getContext());
        }
    }

    @VisibleForTesting
    protected String filterAndSortQuery() {
        SmartRegisterQueryBuilder sqb = new SmartRegisterQueryBuilder(mainSelect);
        String query = "";
        try {
            if (isValidFilterForFts(commonRepository())) {
                String sql = Utils.metadata().getRegisterQueryProvider().getObjectIdsQuery(mainCondition, filters) + (StringUtils.isBlank(getDefaultSortQuery()) ? "" : " order by " + getDefaultSortQuery());

                sql = sqb.addlimitandOffset(sql, clientAdapter.getCurrentlimit(), clientAdapter.getCurrentoffset());

                List<String> ids = commonRepository().findSearchIds(sql);
                query = com.example.opensrp_client_covax.util.Utils.metadata().getRegisterQueryProvider().mainRegisterQuery() +
                        " WHERE _id IN (%s) OR ec_mother_details.base_entity_id in (%s)" + (StringUtils.isBlank(getDefaultSortQuery()) ? "" : " order by " + getDefaultSortQuery());

                String joinedIds = "'" + StringUtils.join(ids, "','") + "'";
                return query.replace("%s", joinedIds);
            } else {
                if (!TextUtils.isEmpty(filters) && !TextUtils.isEmpty(Sortqueries)) {
                    sqb.addCondition(filters);
                    query = sqb.orderbyCondition(Sortqueries);
                    query = sqb.Endquery(sqb.addlimitandOffset(query
                            , clientAdapter.getCurrentlimit()
                            , clientAdapter.getCurrentoffset()));
                }
                return query;
            }
        } catch (Exception e) {
            Timber.e(e);
        }

        return query;
    }


    @Override
    public void onClick(android.view.View view) {
        onViewClicked(view);
    }

    @Override
    public void onLocationChange(String newLocation) {

    }
}
