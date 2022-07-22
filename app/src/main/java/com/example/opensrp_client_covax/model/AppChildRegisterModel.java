package com.example.opensrp_client_covax.model;


import static com.example.opensrp_client_covax.util.AppConstants.JSON_FORM_EXTRA.ENCOUNTER_TYPE;

import android.content.Context;

import com.example.opensrp_client_covax.contract.ChildRegisterContract;
import com.example.opensrp_client_covax.domain.ChildEventClient;
import com.example.opensrp_client_covax.util.AppJsonFormUtils;
import com.example.opensrp_client_covax.util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.domain.tag.FormTag;
import org.smartregister.location.helper.LocationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class AppChildRegisterModel implements ChildRegisterContract.Model {


    @Override
    public void registerViewConfigurations(List<String> viewIdentifiers) {
        ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().registerViewConfigurations(viewIdentifiers);

    }

    @Override
    public void unregisterViewConfigurations(List<String> viewIdentifiers) {
        ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().unregisterViewConfiguration(viewIdentifiers);

    }
    @Override
    public JSONObject getEditFormAsJson(Context context, String formName, String updateEventType, String entityId, Map<String, String> valueMap) throws Exception {
        JSONObject formJsonObject = getFormAsJson(context, formName, entityId);
        formJsonObject.put(ENCOUNTER_TYPE, updateEventType);
       AppJsonFormUtils.populateJsonForm(formJsonObject, valueMap);
        return formJsonObject;
    }
    @Override
    public JSONObject getFormAsJson(Context context, String formName, String entityId) throws Exception {
        return AppJsonFormUtils.getJson(context, formName, entityId);
    }

    @Override
    public String getLocationId(String locationName) {
        return LocationHelper.getInstance().getOpenMrsLocationId(locationName);
    }

    @Override
    public String getInitials() {
        return AppUtils.getUserInitials();
    }

    @Override
    public List<ChildEventClient> processRegistration(String jsonString, FormTag formTag) {
        JSONObject form;
        List<ChildEventClient> childEventClientList = new ArrayList<>();
        try {
            form = new JSONObject(jsonString);
//            updateEncounterTypes(form);

            ChildEventClient childEventClient = AppJsonFormUtils.processChildDetailsForm(jsonString, formTag);
            if (childEventClient == null) {
                return null;
            }


            childEventClientList.add(childEventClient);

        } catch (JSONException e) {
            Timber.e(e, "Error processing registration form");
        }
        return childEventClientList;
    }
}
