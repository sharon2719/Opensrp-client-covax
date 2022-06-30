package com.example.opensrp_client_covax.util;


public class AppConstants {



    public static int REQUEST_CODE_GET_JSON =2244;
    public static final class CONFIGURATION{
        public static final String CHILD_REGISTER = "base_register";

    };

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
    public static final class ACTIVITY_PAYLOAD_TYPE {

        public static final String REGISTRATION = "REGISTRATION";
    }

    public static final class EVENT_TYPE {
        public static final String CHILD_REGISTRATION = "Child Registration";
        public static final String UPDATE_CHILD_REGISTRATION = "Update Child Registration";
        public static final String CARD_STATUS_UPDATE = "card_status_update";
        public static final String OUT_OF_CATCHMENT_SERVICE = "Out of Catchment Service";

    }
    public static class ConfigurationConstants {
        public static final String LOGIN = "login";

    }
    public static final class ACTIVITY_PAYLOAD{

        public static final String TABLE_NAME = "TABLE";
        public static final String BASE_ENTITY_ID = "BASE_ENTITY_ID";
        public static final String ACTION = "ACTION";
    }

    public interface IntentKeyUtil {
        String IS_REMOTE_LOGIN = "is_remote_login";
    }
    public static class JSON_FORM_EXTRA {
        public static final String JSON = "json";
        public static final String NEXT = "next";

        public static final String ENCOUNTER_TYPE = "encounter_type";
    }
    public static final class INTENT_KEY {
        public static final java.lang.String TO_RESCHEDULE = "to_reschedule";
        public static String BASE_ENTITY_ID = "base_entity_id";
    }
    public static class FORMS{
        public static final String CHILD_REGISTRATION = "child-enrollment";

    }
    public static final class RELATIONSHIP {
        public static String GUARDIAN= "father";
    };
    public static class JSON_FORM{

        public static final String CHILD_ENROLLMENT = "child_enrollment";
        public static String OUT_OF_CATCHMENT_SERVICE = "out_of_catchment_service";
    }
    public static class TABLES{

        public static final String EC_CHILD = "ec_child";
    }


    }
