package com.example.opensrp_client_covax.model;

import static com.example.opensrp_client_covax.util.AppConstants.KeyConstants.REGISTRATION_LOCATION_ID;
import static com.example.opensrp_client_covax.util.AppConstants.KeyConstants.REGISTRATION_LOCATION_NAME;

import androidx.annotation.NonNull;

import com.example.opensrp_client_covax.application.CovacsApplication;

import org.json.JSONObject;
import org.smartregister.child.domain.ChildEventClient;
import org.smartregister.child.model.BaseChildRegisterFragmentModel;
import org.smartregister.child.model.BaseChildRegisterModel;
import org.smartregister.clientandeventmodel.Client;
import org.smartregister.domain.tag.FormTag;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.util.JsonFormUtils;

import java.util.List;

public class AppChildRegisterModel extends BaseChildRegisterModel {
    @Override
    public List<ChildEventClient> processRegistration(@NonNull String jsonString, FormTag formTag) {
        List<ChildEventClient> childEventClients = super.processRegistration(jsonString, formTag);
        //Add location name as part of child attributes to avoid fetching name from events
        for (ChildEventClient childEventClient : childEventClients) {
            Client client = childEventClient.getClient();
            AllSharedPreferences sharedPreferences = getSharedPrefs();
            String currentUser = sharedPreferences.fetchRegisteredANM();
            client.getAttributes().put(REGISTRATION_LOCATION_ID, sharedPreferences.fetchDefaultLocalityId(currentUser));
            client.getAttributes().put(REGISTRATION_LOCATION_NAME, sharedPreferences.fetchCurrentLocality());
        }
        return childEventClients;
    }

    private AllSharedPreferences getSharedPrefs() {
        return CovacsApplication.getInstance().context().allSharedPreferences();
    }
}
