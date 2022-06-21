package com.example.opensrp_client_covax.contract;

import android.content.Context;

import com.vijay.jsonwizard.domain.Form;

import org.json.JSONObject;
import org.smartregister.view.contract.BaseRegisterContract;

import java.util.List;
import java.util.Map;

public interface ChildRegisterContract {

    Form getFormConfig();

    BaseRegisterContract.Presenter presenter();

    String getRegistrationForm();
    void saveForm(String jsonString, boolean isEditMode, String table);

     interface Interactor {
        void onDestroy(boolean isChangingConfiguration);
    }

     interface Model {
         JSONObject getFormAsJson(Context context, String formName, String entityId, String currentLocationId) throws Exception;
     }
}
