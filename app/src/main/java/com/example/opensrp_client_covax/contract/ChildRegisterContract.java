package com.example.opensrp_client_covax.contract;

import org.json.JSONObject;
import org.smartregister.child.domain.UpdateRegisterParams;

import java.util.List;
import java.util.Map;

public interface ChildRegisterContract {
    void startFormActivity(String s, String s1, Map<String, String> map);

    void startFormActivity(JSONObject jsonForm);


    Presenter presenter();

    List<String> getViewIdentifiers();

    void startRegistration();

    String getRegistrationForm();

    void onPointerCaptureChanged(boolean hasCapture);

   interface Presenter {

        void saveForm(String jsonString, UpdateRegisterParams updateRegisterParam);
    }
    interface View{

    }
}
