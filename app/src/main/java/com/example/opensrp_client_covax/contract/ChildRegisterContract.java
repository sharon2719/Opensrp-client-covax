package com.example.opensrp_client_covax.contract;

import android.content.Context;

import com.vijay.jsonwizard.domain.Form;

import org.json.JSONObject;
import org.smartregister.view.contract.BaseRegisterContract;

public interface ChildRegisterContract {

    interface Interactor {
        void onDestroy(boolean isChangingConfiguration);
    }

    interface Model {
        JSONObject getFormAsJson(Context context, String formName, String entityId, String currentLocationId) throws Exception;
    }
}
