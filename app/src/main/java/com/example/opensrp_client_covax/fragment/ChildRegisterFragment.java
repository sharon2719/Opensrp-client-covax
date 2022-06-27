package com.example.opensrp_client_covax.fragment;

import android.view.View;

import com.example.opensrp_client_covax.contract.ChildRegisterFragmentContract;
import com.example.opensrp_client_covax.presenter.ChildRegisterFragmentPresenter;
import com.example.opensrp_client_covax.provider.ChildRegistrProvider;

import org.smartregister.child.cursor.AdvancedMatrixCursor;
import org.smartregister.child.model.BaseChildRegisterFragmentModel;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.domain.Response;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.HashMap;
import java.util.Set;

public class ChildRegisterFragment extends BaseRegisterFragment implements ChildRegisterFragmentContract.View {
    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }

        String viewConfigurationIdentifier = ((BaseRegisterActivity) getActivity()).getViewIdentifiers().get(0);
        presenter = new ChildRegisterFragmentPresenter(this, new BaseChildRegisterFragmentModel() {
            @Override
            public AdvancedMatrixCursor createMatrixCursor(Response<String> response) {
                return null;
            }
        }, viewConfigurationIdentifier);
    }

    @Override
    public ChildRegisterFragmentContract.Presenter presenter() {
        return (ChildRegisterFragmentContract.Presenter) presenter;
    }

    @Override
    public void setUniqueID(String s) {
        if (getSearchView() != null) {
            getSearchView().setText(s);
        }
    }

    @Override
    public void setAdvancedSearchFormData(HashMap<String, String> hashMap) {

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
        startFormActivity(getRegistrationForm(), null, (String) null);
    }

    private String getRegistrationForm() {
        return null;
    }

    @Override
    protected void onViewClicked(View view) {

    }

    @Override
    public void initializeAdapter(Set<org.smartregister.configurableviews.model.View> visibleColumns) {
        ChildRegistrProvider childRegisterProvider = new ChildRegistrProvider(getActivity(),commonRepository(),visibleColumns,registerActionHandler,paginationViewHandler);
        clientAdapter = new RecyclerViewPaginatedAdapter(null, childRegisterProvider, context().commonrepository(this.tablename));
        clientAdapter.setCurrentlimit(20);
        clientsView.setAdapter(clientAdapter);
    }

    @Override
    public void recalculatePagination(AdvancedMatrixCursor advancedMatrixCursor) {

    }

    @Override
    public void showNotFoundPopup(String s) {

    }
}
