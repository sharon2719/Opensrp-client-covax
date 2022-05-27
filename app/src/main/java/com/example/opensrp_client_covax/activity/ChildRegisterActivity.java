package com.example.opensrp_client_covax.activity;

import static org.smartregister.child.util.Constants.*;

import android.app.Activity;
import android.content.Intent;

import com.example.opensrp_client_covax.fragment.ChildRegisterFragment;
import com.example.opensrp_client_covax.model.AppChildRegisterModel;
import com.example.opensrp_client_covax.presenter.AppChildRegisterPresenter;
import com.example.opensrp_client_covax.util.AppConstants;
import com.example.opensrp_client_covax.util.AppUtils;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONObject;
import org.smartregister.AllConstants;
import org.smartregister.child.activity.BaseChildRegisterActivity;
import org.smartregister.child.domain.UpdateRegisterParams;
import org.smartregister.child.util.Constants;
import org.smartregister.client.utils.domain.Form;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.lang.ref.WeakReference;
import java.util.Map;

import timber.log.Timber;

public class ChildRegisterActivity extends BaseChildRegisterActivity {

    @Override
    protected void initializePresenter() {
        presenter = new AppChildRegisterPresenter(this, new AppChildRegisterModel());
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        WeakReference<ChildRegisterFragment> weakReference = new WeakReference<>(new ChildRegisterFragment());
        return weakReference.get();
    }

    @Override
    public String getRegistrationForm() {
        return AppConstants.JsonForm.CHILD_ENROLLMENT;
    }


    @Override
    public void startFormActivity(JSONObject jsonForm) {
       startFormActivity("child_enrollment" , null , "");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AllConstants.BARCODE.BARCODE_REQUEST_CODE && resultCode == RESULT_OK) {
            Barcode barcode = data.getParcelableExtra(AllConstants.BARCODE.BARCODE_KEY);
            ((AppChildRegisterPresenter) presenter).updateChildCardStatus(barcode.displayValue);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void saveForm(String jsonString, UpdateRegisterParams updateRegisterParam) {
        String jsonForm = AppUtils.validateChildZone(jsonString);
        super.saveForm(jsonForm, updateRegisterParam);
    }


}