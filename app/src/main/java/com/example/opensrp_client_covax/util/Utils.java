package com.example.opensrp_client_covax.util;


import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.domain.ChildMetadata;

import org.smartregister.Context;


public class Utils extends org.smartregister.util.Utils {

    public static Context context() {
        return CovacsApplication.getInstance().context();
    }

    public static ChildMetadata metadata() {
        return CovacsApplication.getInstance().getMetadata();
    }

}
