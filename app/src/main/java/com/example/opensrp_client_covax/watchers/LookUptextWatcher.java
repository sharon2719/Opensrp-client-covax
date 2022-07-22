package com.example.opensrp_client_covax.watchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.opensrp_client_covax.domain.EntityLookUp;
import com.example.opensrp_client_covax.fragment.AppChildFormFragment;
import com.vijay.jsonwizard.fragments.JsonFormFragment;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.event.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LookUptextWatcher implements TextWatcher {

    private static Map<String, EntityLookUp> lookUpMap;

    private final View mView;
    private final JsonFormFragment formFragment;
    private final String mEntityId;

    public LookUptextWatcher(View mView, JsonFormFragment formFragment, String mEntityId) {
        this.mView = mView;
        this.formFragment = formFragment;
        this.mEntityId = mEntityId;
        lookUpMap = new HashMap<>();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = (String) mView.getTag(com.vijay.jsonwizard.R.id.raw_value);

        if (text == null) {
            text = editable.toString();
        }

        String key = (String) mView.getTag(com.vijay.jsonwizard.R.id.key);

        boolean afterLookUp = (Boolean) mView.getTag(com.vijay.jsonwizard.R.id.after_look_up);
        if (afterLookUp) {
            mView.setTag(com.vijay.jsonwizard.R.id.after_look_up, false);
            return;
        }

        EntityLookUp entityLookUp = new EntityLookUp();
        if (lookUpMap.containsKey(mEntityId)) {
            entityLookUp = lookUpMap.get(mEntityId);
        }

        if (StringUtils.isBlank(text)) {
            if (entityLookUp.containsKey(key)) {
                entityLookUp.remove(key);
            }
        } else {
            entityLookUp.put(key, text);
        }

        lookUpMap.put(mEntityId, entityLookUp);


        Listener<Map<CommonPersonObject, List<CommonPersonObject>>> listener = null;
        if (formFragment instanceof AppChildFormFragment) {
            AppChildFormFragment childFormFragment = (AppChildFormFragment) formFragment;
//            listener = childFormFragment.motherLookUpListener();
        }
    }
}
