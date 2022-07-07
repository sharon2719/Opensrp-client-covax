package com.example.opensrp_client_covax.model;


import android.content.Context;

import com.example.opensrp_client_covax.contract.ChildRegisterContract;
import com.example.opensrp_client_covax.util.AppUtils;

import org.json.JSONObject;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.location.helper.LocationHelper;

import java.util.List;

public class AppChildRegisterModel implements ChildRegisterContract.Model {

    @Override
    public JSONObject getFormAsjson(Context context, String formName, String entityId) throws Exception {
        return null;
    }

    @Override
    public void registerViewConfigurations(List<String> viewIdentifiers) {
        ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().registerViewConfigurations(viewIdentifiers);

    }

    @Override
    public void unregisterViewConfigurations(List<String> viewIdentifiers) {
        ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().unregisterViewConfiguration(viewIdentifiers);

    }

    @Override
    public String getLocationId(String locationName) {
        return LocationHelper.getInstance().getOpenMrsLocationId(locationName);
    }

    @Override
    public String getInitials() {
        return AppUtils.getUserInitials();
    }
}
