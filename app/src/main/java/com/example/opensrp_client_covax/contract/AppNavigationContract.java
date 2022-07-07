package com.example.opensrp_client_covax.contract;

import android.app.Activity;

import java.util.Date;

public interface AppNavigationContract {
    interface Presenter {

        AppNavigationContract.View getNavigationView();

        void refreshLastSync();

        void displayCurrentUser();

        void sync(Activity activity);

        String getLoggedInUserInitials();
    }

    interface View {

        void prepareViews(Activity activity);

        void refreshLastSync(Date lastSync);

        void refreshCurrentUser(String name);

        void logout(Activity activity);

    }

    interface Model {

        String getCurrentUser();
    }

    interface Interactor {

        Date sync();
    }
}
