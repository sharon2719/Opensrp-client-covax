package com.example.opensrp_client_covax.util;

import android.graphics.Color;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.opensrp_client_covax.application.CovacsApplication;
import com.example.opensrp_client_covax.domain.ChildMetadata;
import com.example.opensrp_client_covax.domain.EditWrapper;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.smartregister.Context;
import org.smartregister.immunization.ImmunizationLibrary;
import org.smartregister.immunization.domain.Vaccine;
import org.smartregister.immunization.repository.VaccineRepository;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.repository.UniqueIdRepository;
import org.smartregister.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class AppUtils extends Utils {

    public static final SimpleDateFormat DB_DF = new SimpleDateFormat(AppConstants.SQLITE_DATE_TIME_FORMAT);


    public static TableRow getDataRow(android.content.Context context, String label, String value, TableRow row) {
        TableRow tr = row;
        if (row == null) {
            tr = new TableRow(context);
            TableRow.LayoutParams trlp =
                    new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(trlp);
            tr.setPadding(10, 5, 10, 5);
        }

        TextView l = new TextView(context);
        l.setText(label + ": ");
        l.setPadding(20, 2, 20, 2);
        l.setTextColor(Color.BLACK);
        l.setTextSize(14);
        l.setBackgroundColor(Color.WHITE);
        tr.addView(l);

        TextView v = new TextView(context);
        v.setText(value);
        v.setPadding(20, 2, 20, 2);
        v.setTextColor(Color.BLACK);
        v.setTextSize(14);
        v.setBackgroundColor(Color.WHITE);
        tr.addView(v);

        return tr;
    }

    public static TableRow getDataRow(android.content.Context context, String label, String value, String field,
                                      TableRow row) {
        TableRow tr = row;
        if (row == null) {
            tr = new TableRow(context);
            TableRow.LayoutParams trlp =
                    new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(trlp);
            tr.setPadding(10, 5, 10, 5);
        }

        TextView l = new TextView(context);
        l.setText(label + ": ");
        l.setPadding(20, 2, 20, 2);
        l.setTextColor(Color.BLACK);
        l.setTextSize(14);
        l.setBackgroundColor(Color.WHITE);
        tr.addView(l);

        EditWrapper editWrapper = new EditWrapper();
        editWrapper.setCurrentValue(value);
        editWrapper.setField(field);

        EditText e = new EditText(context);
        e.setTag(editWrapper);
        e.setText(value);
        e.setPadding(20, 2, 20, 2);
        e.setTextColor(Color.BLACK);
        e.setTextSize(14);
        e.setBackgroundColor(Color.WHITE);
        e.setInputType(InputType.TYPE_NULL);
        tr.addView(e);

        return tr;
    }

    public static TableRow getDataRow(android.content.Context context) {
        TableRow tr = new TableRow(context);
        TableRow.LayoutParams trlp =
                new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(trlp);
        tr.setPadding(0, 0, 0, 0);
        // tr.setBackgroundColor(Color.BLUE);
        return tr;
    }

    public static int addAsInts(boolean ignoreEmpty, String... vals) {
        int i = 0;
        for (String v : vals) {
            i += ignoreEmpty && StringUtils.isBlank(v) ? 0 : Integer.parseInt(v);
        }
        return i;
    }

    public static TableRow addToRow(android.content.Context context, String value, TableRow row) {
        return addToRow(context, value, row, false, 1);
    }

    public static TableRow addToRow(android.content.Context context, String value, TableRow row, int weight) {
        return addToRow(context, value, row, false, weight);
    }

    public static TableRow addToRow(android.content.Context context, String value, TableRow row, boolean compact) {
        return addToRow(context, value, row, compact, 1);
    }

    public static void putAll(Map<String, String> map, Map<String, String> extend) {
        Collection<String> values = extend.values();
        while (true) {
            if (!(values.remove(null))) break;
        }
        map.putAll(extend);
    }

    public static void addVaccine(VaccineRepository vaccineRepository, Vaccine vaccine) {
        try {
            if (vaccineRepository == null || vaccine == null) {
                return;
            }

            //Update team and team_id before adding vaccine
            AllSharedPreferences allSharedPreferences = getAllSharedPreferences();
            String providerId = allSharedPreferences.fetchRegisteredANM();
            vaccine.setTeam(allSharedPreferences.fetchDefaultTeam(providerId));
            vaccine.setTeamId(allSharedPreferences.fetchDefaultTeamId(providerId));

            vaccine.setName(vaccine.getName().trim());
            // Add the vaccine
            vaccineRepository.add(vaccine);

            String name = vaccine.getName();
            if (!StringUtils.isBlank(name) && name.contains("/")) {

                updateFTSForCombinedVaccineAlternatives(vaccineRepository, vaccine);
            }
//            if (!BaseRepository.TYPE_Synced.equals(vaccine.getSyncStatus()))
//                Utils.postEvent(new ClientDirtyFlagEvent(vaccine.getBaseEntityId(), VaccineIntentService.EVENT_TYPE));

        } catch (Exception e) {
            Timber.e(e);
        }

    }


    /**
     * Update vaccines in the same group where either can be given. For example measles 1 / mr 1
     *
     * @param vaccineRepository
     * @param vaccine
     */
    public static void updateFTSForCombinedVaccineAlternatives(VaccineRepository vaccineRepository, Vaccine vaccine) {

        List<String> ftsVaccineNames = getAlternativeCombinedVaccines(VaccineRepository.removeHyphen(vaccine.getName()), ImmunizationLibrary.COMBINED_VACCINES_MAP);

        if (ftsVaccineNames != null) {

            for (String ftsVaccineName : ftsVaccineNames) {
                ftsVaccineName = VaccineRepository.addHyphen(ftsVaccineName.toLowerCase());
                Vaccine ftsVaccine = new Vaccine();
                ftsVaccine.setBaseEntityId(vaccine.getBaseEntityId());
                ftsVaccine.setName(ftsVaccineName);
                vaccineRepository.updateFtsSearch(ftsVaccine);
            }

        }
    }

    /**
     * @param vaccineName_       Vaccine whos alternative vaccines names must be found
     * @param combinedVaccineMap Combined vaccine map
     * @return list of alternative vaccines to {@code vaccineName_}
     */

    public static List<String> getAlternativeCombinedVaccines(String vaccineName_, Map<String, String> combinedVaccineMap) {

        List<String> comboVaccineList = null;

        String vaccineName = VaccineRepository.removeHyphen(vaccineName_);
        String comboVaccinesValue = combinedVaccineMap.get(vaccineName_);
        if (comboVaccinesValue != null) {

            String[] comboVaccines = StringUtils.stripAll(comboVaccinesValue.split("/"));

            comboVaccineList = Lists.newArrayList(comboVaccines);

            comboVaccineList.remove(vaccineName);
        }
        return comboVaccineList;

    }

    public static Date dobStringToDate(String dobString) {
        DateTime dateTime = dobStringToDateTime(dobString);
        if (dateTime != null) {
            return dateTime.toDate();
        }
        return null;
    }

    public static DateTime dobStringToDateTime(String dobString) {
        try {
            if (StringUtils.isBlank(dobString)) {
                return null;
            }
            return new DateTime(dobString);

        } catch (Exception e) {
            return null;
        }
    }

    public static String getTodaysDate() {
        return convertDateFormat(LocalDate.now().toDate(), DB_DF);
    }

    public static String convertDateFormat(Date date, SimpleDateFormat formatter) {

        return formatter.format(date);
    }

    public static Context context() {
        return CovacsApplication.getInstance().context();
    }

    public static ChildMetadata metadata() {
        return CovacsApplication.getInstance().getMetadata();
    }

    // date utils from core
    public static String getDuration(String date) {
        DateTime duration;
        if (StringUtils.isNotBlank(date)) {
            try {
                duration = new DateTime(date);
                return getDuration(String.valueOf(duration)); //check if String.valueOf is necessary
            } catch (Exception e) {
                Timber.e(e);
            }
        }
        return "";
    }

    public static float convertDpToPixel(float dp, android.content.Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static String getNextOpenMrsId() {
        UniqueIdRepository uniqueIdRepo = CovacsApplication.getInstance().getUniqueIdRepository();
        return uniqueIdRepo.getNextUniqueId() != null ? uniqueIdRepo.getNextUniqueId().getOpenmrsId() : "";
    }
}
