package com.example.opensrp_client_covax.model;

import org.smartregister.child.cursor.AdvancedMatrixCursor;
import org.smartregister.child.model.BaseChildRegisterFragmentModel;
import org.smartregister.domain.Response;

public class ChildRegisterFragmentModel extends BaseChildRegisterFragmentModel {
    @Override
    public AdvancedMatrixCursor createMatrixCursor(Response<String> response) {
        return null;
    }
}
