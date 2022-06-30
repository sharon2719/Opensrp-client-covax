package com.example.opensrp_client_covax.fragment;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.example.opensrp_client_covax.R;
import com.example.opensrp_client_covax.activity.ChildProfileActivity;
import com.example.opensrp_client_covax.activity.ChildRegisterActivity;
import com.example.opensrp_client_covax.contract.AppChildRegisterFragmentContract;
import com.example.opensrp_client_covax.model.AppChildRegisterFragmentModel;
import com.example.opensrp_client_covax.presenter.AppChildRegisterFragmentPresenter;
import com.example.opensrp_client_covax.provider.AppChildRegisterProvider;
import com.example.opensrp_client_covax.util.AppConstants;

import org.smartregister.child.fragment.NoMatchDialogFragment;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.configurableviews.model.View;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.cursoradapter.RecyclerViewProvider;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.HashMap;
import java.util.Set;

import timber.log.Timber;

public class ChildRegisterFragment extends BaseRegisterFragment implements AppChildRegisterFragmentContract.View {
    public static final String CLICK_VIEW_NORMAL = "click_view_normal";
    protected android.view.View view;
    @Override
    public void initializeAdapter(Set<View> visibleColumns) {
        AppChildRegisterProvider childRegisterProvider = new AppChildRegisterProvider(getActivity(),visibleColumns);
        clientAdapter = new RecyclerViewPaginatedAdapter(null, (RecyclerViewProvider<RecyclerView.ViewHolder>) childRegisterProvider,context().commonrepository(this.tablename));
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
        presenter = new AppChildRegisterFragmentPresenter(this,new AppChildRegisterFragmentModel(),viewConfigurationIdentifier);
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
        ((ChildRegisterActivity)getActivity()).startFormActivity(AppConstants.JSON_FORM.CHILD_ENROLLMENT,null,"");
    }

    @Override
    public void onViewClicked(android.view.View view) {
        if (getActivity() == null) {
            return;
        }

        if (view.getTag() != null && view.getTag(R.id.VIEW_ID) == CLICK_VIEW_NORMAL) {
            if (view.getTag() instanceof CommonPersonObjectClient) {
                goToChildDetailActivity((CommonPersonObjectClient) view.getTag(), false);
            }
        }
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
        if (getActivity() == null) {
            return;
        }
        NoMatchDialogFragment.launchDialog((BaseRegisterActivity) getActivity(), DIALOG_TAG, opensrpId);
    }
}
