package com.example.opensrp_client_covax.util;

public class AppConstants {
    public interface IntentKeyUtil {
        String IS_REMOTE_LOGIN = "is_remote_login";
    }

    public interface JsonForm {
        String CHILD_ENROLLMENT = "child_enrollment";

    }

    public static final class KeyConstants {
        public static final String FIRST_NAME = "First_Name";
        public static final String LAST_NAME = "Last_Name";
        public static final String DOB = "Date_Birth";//Date Of Birth
        public static final String VIEW_CONFIGURATION_PREFIX = "ViewConfiguration_";
        public static final String REGISTRATION_LOCATION_ID = "registration_location_id";
        public static final String REGISTRATION_LOCATION_NAME = "registration_location_name";
        public static final String KEY = "key";
        public static final String CHILD_ZONE = "child_zone";
        public static final String CARD_STATUS = "card_status";
        public static final String PLACE_OF_BIRTH = "place_of_birth";
        public static final String BIRTH_FACILITY_NAME = "birth_facility_name";
        public static final String PMTCT_STATUS = "pmtct_status";
        public static final String CARD_STATUS_DATE = "card_status_date";
        public static final String LAST_INTERACTED_WITH = "last_interacted_with";

    }

    public static final class EventTypeConstants {
        public static final String CHILD_REGISTRATION = "Child Registration";
        public static final String UPDATE_CHILD_REGISTRATION = "Update Child Registration";
        public static final String CARD_STATUS_UPDATE = "card_status_update";
    }

    public static class ConfigurationConstants {
        public static final String LOGIN = "login";

    }

    public static final class INTENT_KEY {
        public static final java.lang.String TO_RESCHEDULE = "to_reschedule";
    }


}
