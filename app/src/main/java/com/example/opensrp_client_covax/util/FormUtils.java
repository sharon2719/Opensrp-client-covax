package com.example.opensrp_client_covax.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.commonregistry.CommonPersonObjectClient;

public class FormUtils {
    public static String obtainUpdatedForm(JSONObject form, Context context) throws JSONException {
        return obtainUpdatedForm(form, null, context);
    }

    private static String obtainUpdatedForm(JSONObject form, CommonPersonObjectClient childDetails, Context context) {
        return null;
    }
}
