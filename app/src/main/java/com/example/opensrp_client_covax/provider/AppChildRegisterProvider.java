package com.example.opensrp_client_covax.provider;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.child.provider.ChildRegisterProvider;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.configurableviews.model.View;
import org.smartregister.cursoradapter.RecyclerViewProvider;
import org.smartregister.view.contract.SmartRegisterClient;
import org.smartregister.view.contract.SmartRegisterClients;
import org.smartregister.view.dialog.FilterOption;
import org.smartregister.view.dialog.ServiceModeOption;
import org.smartregister.view.dialog.SortOption;
import org.smartregister.view.viewholder.OnClickFormLauncher;


import java.text.MessageFormat;
import java.util.Set;

public class AppChildRegisterProvider  implements RecyclerViewProvider<ChildRegisterProvider.RegisterViewHolder> {
    private Set<org.smartregister.configurableviews.model.View> visibleColumns;
    private android.view.View.OnClickListener onClickListener;
    private android.view.View.OnClickListener paginationClickListener;

    public AppChildRegisterProvider(FragmentActivity activity, Set<View> visibleColumns) {
        this.visibleColumns = visibleColumns;
    }

    @Override
    public void getView(Cursor cursor, SmartRegisterClient client, ChildRegisterProvider.RegisterViewHolder viewHolder) {
        CommonPersonObjectClient pc = (CommonPersonObjectClient) client;
        if (visibleColumns.isEmpty()) {
            populatePatientColumn(pc, client, viewHolder);
            populateIdentifierColumn(pc, viewHolder);
        }
    }

    private void populateIdentifierColumn(CommonPersonObjectClient pc, ChildRegisterProvider.RegisterViewHolder viewHolder) {

    }

    private void populatePatientColumn(CommonPersonObjectClient pc, SmartRegisterClient client, ChildRegisterProvider.RegisterViewHolder viewHolder) {
    }

    @Override
    public void getFooterView(RecyclerView.ViewHolder viewHolder, int currentPageCount, int totalCount, boolean hasNextPage, boolean hasPreviousPage) {

    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption, FilterOption searchFilter, SortOption sortOption) {
        return null;
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {

    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return null;
    }

    @Override
    public LayoutInflater inflater() {
        return null;
    }

    @Override
    public ChildRegisterProvider.RegisterViewHolder createViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder createFooterHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
        return false;
    }
}
