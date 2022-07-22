package com.example.opensrp_client_covax.util;

import static com.vijay.jsonwizard.utils.NativeFormLangUtils.getTranslatedString;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.domain.ChildEventClient;
import com.example.opensrp_client_covax.domain.Observation;
import com.vijay.jsonwizard.constants.JsonFormConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.AllConstants;
import org.smartregister.clientandeventmodel.Client;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.clientandeventmodel.Obs;
import org.smartregister.domain.tag.FormTag;
import org.smartregister.immunization.util.VaccinatorUtils;
import org.smartregister.location.helper.LocationHelper;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.util.FormUtils;
import org.smartregister.util.JsonFormUtils;
import org.smartregister.view.LocationPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

public class AppJsonFormUtils extends JsonFormUtils {

    public static final String METADATA = "metadata";
    public static final String ENCOUNTER_TYPE = "encounter_type";
    public static final int REQUEST_CODE_GET_JSON = 2244;
    public static final String CURRENT_OPENSRP_ID = "current_opensrp_id";
    public static final String READ_ONLY = "read_only";
    public static final String STEP2 = "step2";
    public static final String RELATIONAL_ID = "relational_id";
    public static final String CURRENT_ZEIR_ID = "current_zeir_id";
    public static final String ZEIR_ID = "ZEIR_ID";
    public static final String updateBirthRegistrationDetailsEncounter = "Update Birth Registration";
    public static final String BCG_SCAR_EVENT = "Bcg Scar";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(com.vijay.jsonwizard.utils.FormUtils.NATIIVE_FORM_DATE_FORMAT_PATTERN, Locale.ENGLISH);
    public static final String GENDER = "gender";
    public static final String M_ZEIR_ID = "M_ZEIR_ID";
    public static final String F_ZEIR_ID = "F_ZEIR_ID";
    private static final String ENCOUNTER = "encounter";
    private static final String IDENTIFIERS = "identifiers";
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private static final String OPENSRP_ID = "opensrp_id";
    private static final String FORM_SUBMISSION_FIELD = "formsubmissionField";
    private static final String LABEL_TEXT_STYLE = "label_text_style";
    private static final String RECURRING_SERVICES_FILE = "services.json";


    public static JSONObject getJson(Context context, String formName, String baseEntityID) throws Exception {
        String locationId = CovacsApplication.getInstance().getContext().allSharedPreferences().getPreference(AllConstants.CURRENT_LOCATION_ID);
        JSONObject jsonObject = new JSONObject(getTranslatedString(FormUtils.getInstance(context).getFormJson(formName).toString(), context));
        AppJsonFormUtils.getRegistrationForm(jsonObject, baseEntityID, locationId);
        return jsonObject;
    }

    public static void getRegistrationForm(JSONObject jsonObject, String entityId, String currentLocationId) throws org.json.JSONException {
        //empty block
    }
    public static void mergeAndSaveClient(Client baseClient) throws Exception {
        JSONObject updatedClientJson = new JSONObject(AppJsonFormUtils.gson.toJson(baseClient));
        JSONObject originalClientJsonObject = CovacsApplication.getInstance().getEcSyncHelper().getClient(baseClient.getBaseEntityId());
        JSONObject mergedJson = AppJsonFormUtils.merge(originalClientJsonObject, updatedClientJson);
        //TODO Save edit log ?
        CovacsApplication.getInstance().getEcSyncHelper().addClient(baseClient.getBaseEntityId(), mergedJson);
    }
    public static FormTag formTag(AllSharedPreferences allSharedPreferences) {
        FormTag formTag = new FormTag();
        formTag.providerId = allSharedPreferences.fetchRegisteredANM();
        formTag.team = allSharedPreferences.fetchDefaultTeam(allSharedPreferences.fetchRegisteredANM());
        formTag.teamId = allSharedPreferences.fetchDefaultTeamId(allSharedPreferences.fetchRegisteredANM());
        return formTag;
    }

    public static void populateJsonForm(@NotNull JSONObject jsonObject, @NotNull Map<String, String> valueMap) throws JSONException {
        Map<String, String> _valueMap = new HashMap<>(valueMap);
        int step = 1;
        while (jsonObject.has("step" + step)) {
            JSONObject jsonStepObject = jsonObject.getJSONObject("step" + step);
            JSONArray array = jsonStepObject.getJSONArray(JsonFormConstants.FIELDS);
            int position = 0;
            while (position < array.length() && _valueMap.size() > 0) {

                JSONObject object = array.getJSONObject(position);
                String key = object.getString(JsonFormConstants.KEY);

                if (_valueMap.containsKey(key)) {
                    object.put(JsonFormConstants.VALUE, _valueMap.get(key));
                    _valueMap.remove(key);
                }

                position++;
            }

            step++;
        }
    }  protected static Triple<Boolean, JSONObject, JSONArray> validateParameters(String jsonString) {
        JSONObject jsonForm = toJSONObject(jsonString);
        JSONArray fields = fields(jsonForm);
        return Triple.of(jsonForm != null && fields != null, jsonForm, fields);
    }
    protected static void processGender(JSONArray fields) {
        try {
            //TO DO Will need re-architecting later to support more languages, perhaps update the selector widget

            JSONObject genderObject = getFieldJSONObject(fields, AppConstants.KeyConstants.GENDER);
            String genderValue = "";

            String rawGender = genderObject.getString(JsonFormConstants.VALUE);
            char rawGenderChar = !TextUtils.isEmpty(rawGender) ? rawGender.charAt(0) : ' ';
            switch (rawGenderChar) {
                case 'm':
                case 'M':
                    genderValue = "Male";
                    break;

                case 'f':
                case 'F':
                    genderValue = "Female";
                    break;

                default:
                    break;

            }

            genderObject.put(AppConstants.KeyConstants.VALUE, genderValue);
        } catch (JSONException e) {
            Timber.e(e, "ChildJsonFormUtils --> processGender");
        }
    }
    public static ChildEventClient processChildDetailsForm(String jsonString, FormTag formTag) {
        try {
            Triple<Boolean, JSONObject, JSONArray> registrationFormParams = validateParameters(jsonString);

            if (!registrationFormParams.getLeft()) {
                return null;
            }

            JSONObject jsonForm = registrationFormParams.getMiddle();
            JSONArray fields = registrationFormParams.getRight();

            String entityId = getString(jsonForm, ENTITY_ID);
            if (StringUtils.isBlank(entityId)) {
                entityId = generateRandomUUIDString();
            }

            processGender(fields);//multi language to re visit

            processLocationFields(fields);

            lastInteractedWith(fields);



            Client baseClient = AppJsonFormUtils.createBaseClient(fields, formTag, entityId);
            baseClient.setClientType(AppConstants.KeyConstants.CHILD);

            Event baseEvent = AppJsonFormUtils.createEvent(fields, getJSONObject(jsonForm, METADATA),
                    formTag, entityId, jsonForm.getString(AppJsonFormUtils.ENCOUNTER_TYPE), AppConstants.KeyConstants.CHILD);
            AppJsonFormUtils.tagSyncMetadata(baseEvent);// tag docs


            tagClientLocation(baseClient, baseEvent);

            return new ChildEventClient(baseClient, baseEvent);
        } catch (Exception e) {
            Timber.e(e, "ChildJsonFormUtils --> processChildDetailsForm");
            return null;
        }
    }

    private static void tagClientLocation(Client baseClient, Event baseEvent) {
        //Tag client with event's location and team
        baseClient.setLocationId(baseEvent.getLocationId());
        baseClient.setTeamId(baseEvent.getTeamId());
    }
    protected static void lastInteractedWith(JSONArray fields) {
        try {
            JSONObject lastInteractedWith = new JSONObject();
            lastInteractedWith.put(AppConstants.KeyConstants.KEY, AppConstants.JSON_FORM_KEY.LAST_INTERACTED_WITH);
            lastInteractedWith.put(AppConstants.KeyConstants.VALUE, Calendar.getInstance().getTimeInMillis());
            fields.put(lastInteractedWith);
        } catch (JSONException e) {
            Timber.e(e, "ChildJsonFormUtils --> lastInteractedWith");
        }
    }
    protected static void processLocationFields(JSONArray fields) throws JSONException {
        for (int i = 0; i < fields.length(); i++) {
            try {
                if (fields.getJSONObject(i).has(JsonFormConstants.TYPE) && fields.getJSONObject(i).getString(JsonFormConstants.TYPE).equals(JsonFormConstants.TREE)) {
                    try {
                        String rawValue = fields.getJSONObject(i).getString(JsonFormConstants.VALUE);
                        JSONArray valueArray = new JSONArray(rawValue);
                        if (valueArray.length() > 0) {
                            String lastLocationName = valueArray.getString(valueArray.length() - 1);
                            String lastLocationId = LocationHelper.getInstance().getOpenMrsLocationId(lastLocationName);
                            fields.getJSONObject(i).put(JsonFormConstants.VALUE, lastLocationId);
                        }
                    } catch (Exception e) {
                        Timber.e(e, "JsonFormUitls --> processLocationFields");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static Event tagSyncMetadata(@NonNull Event event) {

        AllSharedPreferences allSharedPreferences = Utils.getAllSharedPreferences();
        String providerId = allSharedPreferences.fetchRegisteredANM();
        event.setProviderId(providerId);
        event.setLocationId(getProviderLocationId(CovacsApplication.getInstance().context().applicationContext()));

        String childLocationId = getChildLocationId(allSharedPreferences.fetchDefaultLocalityId(providerId), allSharedPreferences);
        event.setChildLocationId(childLocationId);
        event.setTeam(allSharedPreferences.fetchDefaultTeam(providerId));
        event.setTeamId(allSharedPreferences.fetchDefaultTeamId(providerId));

        event.addDetails(AllConstants.DATA_STRATEGY, allSharedPreferences.fetchCurrentDataStrategy());

        try {
            addObservation(AllConstants.DATA_STRATEGY, allSharedPreferences.fetchCurrentDataStrategy(), Observation.TYPE.TEXT, event);
        } catch (JSONException jsonException) {
            Timber.e(jsonException);
        }
        return event;
    }
    protected static void addObservation(String key, String value, Observation.TYPE type, Event event) throws JSONException {
        //In case it is an unsynced Event and we are updating, we need to remove the previous Observation with the same form field tag
        //Form fields should always be unique per submission

        List<Obs> obsList = event.getObs();
        if (obsList != null && obsList.size() > 0) {
            obsList.removeIf(obs -> obs.getFormSubmissionField().equals(key));
        }

        // Process new observation
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY, key);
        jsonObject.put(VALUE, value);
        jsonObject.put(OPENMRS_DATA_TYPE, type != null ? type : AllConstants.TEXT);
        addObservation(event, jsonObject);
    }
    @Nullable
    public static String getChildLocationId(@NonNull String defaultLocationId, @NonNull AllSharedPreferences allSharedPreferences) {
        String currentLocality = allSharedPreferences.fetchCurrentLocality();

        try {
            if (StringUtils.isNotBlank(currentLocality)) {
                String currentLocalityId = LocationHelper.getInstance().getOpenMrsLocationId(currentLocality);
                if (StringUtils.isNotBlank(currentLocalityId) && !defaultLocationId.equalsIgnoreCase(currentLocalityId)) {
                    return AllConstants.DATA_CAPTURE_STRATEGY.ADVANCED.equals(allSharedPreferences.fetchCurrentDataStrategy()) ? AppConstants.KeyConstants.ADVANCED_DATA_CAPTURE_STRATEGY_PREFIX + VaccinatorUtils.createIdentifier(currentLocalityId) : currentLocalityId;
                }
            }
        } catch (Exception e) {
            Timber.e(e);
        }

        return null;
    }
    public static String getProviderLocationId(Context context) {

        AllSharedPreferences allSharedPreferences = org.smartregister.util.Utils.getAllSharedPreferences();

        return CovacsApplication.getInstance().getProperties().isTrue(ChildAppProperties.KEY.SYNC_BY_DEFAULT_FACILITY_ID_ENABLED) ?
                allSharedPreferences.fetchDefaultLocalityId(allSharedPreferences.fetchRegisteredANM()) : getProviderCurrentSelectedLocationId(context, allSharedPreferences);
    }
    protected static String getProviderCurrentSelectedLocationId(Context context, AllSharedPreferences allSharedPreferences) {
        try {
            String currentLocality = allSharedPreferences.getPreference(AllConstants.CURRENT_LOCATION_ID);
            String openMrsLocationId = LocationHelper.getInstance().getOpenMrsLocationId(currentLocality);
            if (StringUtils.isNotBlank(openMrsLocationId)) return currentLocality;
        } catch (NullPointerException exception) {
            LocationPickerView locationPickerView = CovacsApplication.getInstance().getLocationPickerView(context);
            if (locationPickerView != null) {
                String locationId = LocationHelper.getInstance().getOpenMrsLocationId(locationPickerView.getSelectedItem());
                if (StringUtils.isNotBlank(locationId)) return locationId;
            }
            Timber.e(exception);
        }
        return allSharedPreferences.fetchDefaultLocalityId(allSharedPreferences.fetchRegisteredANM());
    }
}
