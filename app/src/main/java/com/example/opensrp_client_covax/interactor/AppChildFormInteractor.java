package com.example.opensrp_client_covax.interactor;

import com.example.opensrp_client_covax.widgets.AppChildCheckboxTextFactory;
import com.example.opensrp_client_covax.widgets.AppChildDatePickerFactory;
import com.example.opensrp_client_covax.widgets.AppChildEditTextFactory;
import com.example.opensrp_client_covax.widgets.AppChildSpinnerFactory;
import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.interactors.JsonFormInteractor;

public class AppChildFormInteractor extends JsonFormInteractor {
    private static final AppChildFormInteractor CHILD_INTERACTOR_INSTANCE = new AppChildFormInteractor();

    protected AppChildFormInteractor() {
        super();
    }

    public static JsonFormInteractor getInstance(){
        return CHILD_INTERACTOR_INSTANCE;
    }
    public static JsonFormInteractor getChildInteractorInstance() {
        return CHILD_INTERACTOR_INSTANCE;
    }

    @Override
    protected void registerWidgets() {
        super.registerWidgets();
        map.put(JsonFormConstants.EDIT_TEXT, new AppChildEditTextFactory());
        map.put(JsonFormConstants.DATE_PICKER, new AppChildDatePickerFactory());
        map.put(JsonFormConstants.CHECK_BOX, new AppChildCheckboxTextFactory());
        map.put(JsonFormConstants.SPINNER, new AppChildSpinnerFactory());
    }
}
