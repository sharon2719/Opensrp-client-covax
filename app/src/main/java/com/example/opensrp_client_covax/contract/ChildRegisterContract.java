package com.example.opensrp_client_covax.contract;

import android.content.Context;

import org.json.JSONObject;
import org.smartregister.child.domain.UpdateRegisterParams;
import org.smartregister.view.contract.BaseRegisterContract;

import java.util.List;
import java.util.Map;

public interface ChildRegisterContract {

    interface View extends BaseRegisterContract.View {
        void setActiveMenuItem(int menuItemId);

        String getRegistrationForm();

        Presenter presenter();

        void onRegistrationSaved();

        void saveForm(String jsonString, UpdateRegisterParams updateRegisterParam);
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
        void onDestroy(boolean isChangingConfiguration);
    }

    interface InteractorCallBack {
        void onRegistrationSaved(boolean isEdit);
    }

}
