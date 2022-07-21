package com.example.opensrp_client_covax.contract;

import android.content.Context;

import com.example.opensrp_client_covax.domain.ChildEventClient;
import com.example.opensrp_client_covax.domain.UpdateRegisterParams;

import org.json.JSONObject;
import org.smartregister.view.contract.BaseRegisterContract;

import java.util.List;
import java.util.Map;

public interface ChildRegisterContract {

    interface View extends BaseRegisterContract.View {
        void saveForm(String jsonString, UpdateRegisterParams updateRegisterParam);

        void setActiveMenuItem(int menuItemId);

        String getRegistrationForm();

        Presenter presenter();

        void onRegistrationSaved();

    }

    interface Presenter extends BaseRegisterContract.Presenter {
        View getView();

        void startRegistrationForm(String formName, String updateEventType, String entityId, Map<String, String> valueMap) throws Exception;

        void saveForm(String jsonString, boolean isEditMode);
    }

    interface Model {
        JSONObject getFormAsjson(Context context, String formName, String entityId) throws Exception;

        void registerViewConfigurations(List<String> viewIdentifiers);

        void unregisterViewConfigurations(List<String> viewIdentifiers);

        String getLocationId(String locationName);

        String getInitials();
    }

    interface Interactor {
        void saveRegistration(final List<ChildEventClient> childEventClientList, final String jsonString,
                              final com.example.opensrp_client_covax.domain.UpdateRegisterParams updateRegisterParams,
                              final ChildRegisterContract.InteractorCallBack callBack);

        void onDestroy(boolean isChangingConfiguration);
    }

    interface InteractorCallBack {
        void onRegistrationSaved(boolean isEdit);
    }

}
