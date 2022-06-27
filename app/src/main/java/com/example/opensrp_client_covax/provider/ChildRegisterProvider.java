package com.example.opensrp_client_covax.provider;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.configurableviews.model.View;
import org.smartregister.cursoradapter.RecyclerViewFragment;
import org.smartregister.cursoradapter.RecyclerViewProvider;
import org.smartregister.view.contract.SmartRegisterClient;
import org.smartregister.view.contract.SmartRegisterClients;
import org.smartregister.view.dialog.FilterOption;
import org.smartregister.view.dialog.ServiceModeOption;
import org.smartregister.view.dialog.SortOption;
import org.smartregister.view.fragment.BaseRegisterFragment;
import org.smartregister.view.viewholder.OnClickFormLauncher;

import java.util.Set;

public class ChildRegisterProvider implements RecyclerViewProvider<RecyclerView.ViewHolder> {
//    public ChildRegisterProvider(FragmentActivity activity, CommonRepository commonRepository, Set<View> visibleColumns, BaseRegisterFragment.RegisterActionHandler registerActionHandler, RecyclerViewFragment.PaginationViewHandler paginationViewHandler) {
    }

    @Override
    public void getView(Cursor cursor, SmartRegisterClient smartRegisterClient, RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void getFooterView(RecyclerView.ViewHolder viewHolder, int i, int i1, boolean b, boolean b1) {

    }

    @Override
    public SmartRegisterClients updateClients(FilterOption filterOption, ServiceModeOption serviceModeOption, FilterOption filterOption1, SortOption sortOption) {
        return null;
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {

    }

    @Override
    public OnClickFormLauncher newFormLauncher(String s, String s1, String s2) {
        return null;
    }

    @Override
    public LayoutInflater inflater() {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder createFooterHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
        return false;
    }
}
