package com.example.opensrp_client_covax.widgets;

import android.view.View;

import com.example.opensrp_client_covax.util.AppConstants;
import com.vijay.jsonwizard.fragments.JsonFormFragment;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class WidgetUtils {

    public static void hookupLookup(View view, JSONObject jsonObject, JsonFormFragment formFragment) {
        try {

            if (jsonObject.has(AppConstants.KEY.LOOK_UP) && jsonObject.get(AppConstants.KEY.LOOK_UP).toString().equalsIgnoreCase(Boolean.TRUE.toString())) {

                String entityId = jsonObject.getString(AppConstants.KEY.ENTITY_ID);

                Map<String, List<View>> lookupMap = formFragment.getLookUpMap();

                List<View> lookUpViews = new ArrayList<>();
                if (lookupMap.containsKey(entityId)) {
                    lookUpViews = lookupMap.get(entityId);
                }

                if (!lookUpViews.contains(view)) {
                    lookUpViews.add(view);
                }

                lookupMap.put(entityId, lookUpViews);
            }

        } catch (JSONException exception) {

            Timber.e(exception);

        }
    }

}
