package com.example.opensrp_client_covax.widgets;

import android.content.Context;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vijay.jsonwizard.fragments.JsonFormFragment;
import com.vijay.jsonwizard.widgets.DatePickerFactory;

import org.json.JSONObject;

public class AppChildDatePickerFactory extends DatePickerFactory {


    @Override
    public void attachLayout(String stepName, final Context context, JsonFormFragment formFragment, JSONObject jsonObject,
                             final MaterialEditText editText, final TextView duration) {
        super.attachLayout(stepName, context, formFragment, jsonObject, editText, duration);
    }
}
