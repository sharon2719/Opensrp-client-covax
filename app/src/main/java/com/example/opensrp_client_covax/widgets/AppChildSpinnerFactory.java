package com.example.opensrp_client_covax.widgets;

import android.view.View;

import com.vijay.jsonwizard.fragments.JsonFormFragment;
import com.vijay.jsonwizard.widgets.SpinnerFactory;

import org.json.JSONObject;

public class AppChildSpinnerFactory extends SpinnerFactory {
    @Override
    public void genericWidgetLayoutHookback(View view, JSONObject jsonObject, JsonFormFragment formFragment) {

        WidgetUtils.hookupLookup(view, jsonObject, formFragment);
    }
}
