package com.example.opensrp_client_covax.model;

import android.content.Context;

import com.example.opensrp_client_covax.contract.ChildRegisterContract;
import com.example.opensrp_client_covax.util.AppJsonFormUtils;

import org.json.JSONObject;
import org.smartregister.util.FormUtils;

public class ChildRegisterModel implements ChildRegisterContract.Model {
    @Override
    public JSONObject getFormAsJson(Context context, String formName, String entityId, String currentLocationId) throws Exception {
        JSONObject jsonObject = FormUtils.getInstance(context).getFormJson(formName);
        AppJsonFormUtils.getRegistrationForm(jsonObject, entityId, currentLocationId);

        return jsonObject;
    }
}
