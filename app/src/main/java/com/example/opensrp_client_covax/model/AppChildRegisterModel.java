package com.example.opensrp_client_covax.model;

import org.json.JSONObject;
import org.smartregister.child.contract.ChildRegisterContract;
import org.smartregister.child.domain.ChildEventClient;
import org.smartregister.domain.tag.FormTag;

import java.util.List;
import java.util.Map;

public class AppChildRegisterModel implements ChildRegisterContract.Model {
    @Override
    public void registerViewConfigurations(List<String> list) {

    }

    @Override
    public void unregisterViewConfiguration(List<String> list) {

    }

    @Override
    public void saveLanguage(String s) {

    }

    @Override
    public String getLocationId(String s) {
        return null;
    }

    @Override
    public List<ChildEventClient> processRegistration(String s, FormTag formTag) {
        return null;
    }

    @Override
    public JSONObject getFormAsJson(String s, String s1, String s2, Map<String, String> map) throws Exception {
        return null;
    }

    @Override
    public String getInitials() {
        return null;
    }
}
