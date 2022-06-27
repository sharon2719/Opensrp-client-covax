package com.example.opensrp_client_covax.domain;


import com.vijay.jsonwizard.activities.JsonFormActivity;

import org.smartregister.child.activity.BaseChildImmunizationActivity;
import org.smartregister.child.activity.BaseChildRegisterActivity;

import org.smartregister.child.provider.RegisterQueryProvider;
import org.smartregister.view.activity.BaseProfileActivity;
import org.smartregister.view.activity.BaseRegisterActivity;

import java.util.ArrayList;
import java.util.Set;

public class ChildMetaData {
    public ChildMetaData.ChildRegister childRegister;
    public final Class childFormActivity;
    public final Class childImmunizationActivity;
    private Class<? extends BaseRegisterActivity> childRegisterActivity;
    public final Class profileActivity;
    public final boolean formWizardValidateRequiredFieldsBefore;
    private ArrayList<String> locationLevels;
    private ArrayList<String> healthFacilityLevels;
    private Set<String> fieldsWithLocationHierarchy;
    private RegisterQueryProvider registerQueryProvider;

    public ChildMetaData(Class<? extends JsonFormActivity> childFormActivity,
                         Class<? extends BaseProfileActivity> profileActivity,
                         Class<? extends BaseChildImmunizationActivity> childImmunizationActivity,
                         Class<? extends BaseChildRegisterActivity> childRegisterActivity,
                         boolean formWizardValidateRequiredFieldsBefore) {
        this.childFormActivity = childFormActivity;
        this.profileActivity = profileActivity;
        this.childImmunizationActivity = childImmunizationActivity;
        this.childRegisterActivity = childRegisterActivity;
        this.formWizardValidateRequiredFieldsBefore = formWizardValidateRequiredFieldsBefore;
        setRegisterQueryProvider(new RegisterQueryProvider());
    }

    public ChildMetaData(Class<? extends JsonFormActivity> childFormActivity,
                         Class<? extends BaseProfileActivity> profileActivity,
                         Class<? extends BaseChildImmunizationActivity> childImmunizationActivity,
                         Class<? extends BaseChildRegisterActivity> childRegisterActivity,
                         boolean formWizardValidateRequiredFieldsBefore,
                         RegisterQueryProvider registerQueryProvider) {
        this.childFormActivity = childFormActivity;
        this.profileActivity = profileActivity;
        this.childImmunizationActivity = childImmunizationActivity;
        this.childRegisterActivity = childRegisterActivity;
        this.formWizardValidateRequiredFieldsBefore = formWizardValidateRequiredFieldsBefore;
        this.registerQueryProvider = registerQueryProvider;
    }


    public ArrayList<String> getLocationLevels() {
        return locationLevels;
    }

    public void setLocationLevels(ArrayList<String> locationLevels) {
        this.locationLevels = locationLevels;
    }

    public ArrayList<String> getHealthFacilityLevels() {
        return healthFacilityLevels;
    }

    public void setHealthFacilityLevels(ArrayList<String> healthFacilityLevels) {
        this.healthFacilityLevels = healthFacilityLevels;
    }

    public Set<String> getFieldsWithLocationHierarchy() {
        return fieldsWithLocationHierarchy;
    }

    public void setFieldsWithLocationHierarchy(Set<String> fieldsWithLocationHierarchy) {
        this.fieldsWithLocationHierarchy = fieldsWithLocationHierarchy;
    }

    public RegisterQueryProvider getRegisterQueryProvider() {
        return registerQueryProvider;
    }
    

    public void setRegisterQueryProvider(RegisterQueryProvider registerQueryProvider) {
    }
    public Class<? extends BaseChildRegisterActivity> getChildRegisterActivity() {
        return (Class<? extends BaseChildRegisterActivity>) childRegisterActivity;
    }

    public void updateChildRegister(String childEnrollment, String client, String client1, String childRegistration, String updateChildRegistration, String childRegister, String guardian, String outOfCatchmentService) {
    }

    private class ChildRegister {
    }
}
