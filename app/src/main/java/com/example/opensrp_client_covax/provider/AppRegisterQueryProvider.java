package com.example.opensrp_client_covax.provider;

import com.example.opensrp_client_covax.util.AppConstants;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.child.provider.RegisterQueryProvider;
import org.smartregister.commonregistry.CommonFtsObject;


public class AppRegisterQueryProvider extends RegisterQueryProvider {
    public String getObjectIdsQuery(String mainCondition, String filters) {

        String strMainCondition = getMainCondition(mainCondition);

        String strFilters = getFilter(filters);

        if (StringUtils.isNotBlank(strFilters) && StringUtils.isBlank(strMainCondition)) {
            strFilters = String.format(" WHERE " + getDemographicTable() + ".phrase MATCH '*%s*'", filters);
        }

        return "SELECT " + getDemographicTable() + ".object_id " +
                "FROM " + CommonFtsObject.searchTableName(getDemographicTable()) + " " + getDemographicTable() + " " +
                "LEFT JOIN " + getChildDetailsTable() + " ON " + getDemographicTable() + ".object_id = " + getChildDetailsTable() + ".id " +
                "LEFT JOIN " + CommonFtsObject.searchTableName(getChildDetailsTable()) + " ON " + getDemographicTable() + ".object_id = " + CommonFtsObject.searchTableName(getChildDetailsTable()) + ".object_id " +
                strMainCondition + strFilters;
    }

    private String getFilter(String filters) {
        if (StringUtils.isNotBlank(filters)) {
            return String.format(" AND " + getDemographicTable() + ".phrase MATCH '*%s*'", filters);
        }
        return "";
    }

    private String getMainCondition(String mainCondition) {
        if (!StringUtils.isBlank(mainCondition)) {
            return " WHERE " + mainCondition;
        }
        return "";
    }

    public String getCountExecuteQuery(String mainCondition, String filters) {
        String strMainCondition = getMainCondition(mainCondition);

        String strFilters = getFilter(filters);

        if (StringUtils.isNotBlank(strFilters) && StringUtils.isBlank(strMainCondition)) {
            strFilters = String.format(" WHERE " + getDemographicTable() + ".phrase MATCH '*%s*'", filters);
        }

        return "SELECT count(" + getDemographicTable() + ".object_id) " +
                "FROM " + CommonFtsObject.searchTableName(getDemographicTable()) + " " + getDemographicTable() + " " +
                "LEFT JOIN " + getChildDetailsTable() + " ON " + getDemographicTable() + ".object_id = " + getChildDetailsTable() + ".id " +
                "LEFT JOIN " + CommonFtsObject.searchTableName(getChildDetailsTable()) + " ON " + getDemographicTable() + ".object_id = " + CommonFtsObject.searchTableName(getChildDetailsTable()) + ".object_id " +
                strMainCondition + strFilters;
    }

    public String mainRegisterQuery() {
        return "SELECT " + StringUtils.join(mainColumns(), ",") + " " +
                "FROM " + getChildDetailsTable() + " " +
                "JOIN " + getDemographicTable() + " ON " + getDemographicTable() + "." + AppConstants.KeyConstants.BASE_ENTITY_ID + " = " + getChildDetailsTable() + "." + AppConstants.KeyConstants.BASE_ENTITY_ID + " ";
    }

    public String mainRegisterQuery(String select) {
        if (StringUtils.isBlank(select)) {
            select = StringUtils.join(mainColumns(), ",");
        }
        return "SELECT " + select + " " +
                "FROM " + getChildDetailsTable() + " " +
                "JOIN " + getDemographicTable() + " ON " + getDemographicTable() + "." + AppConstants.KeyConstants.BASE_ENTITY_ID + " = " + getChildDetailsTable() + "." + AppConstants.KeyConstants.BASE_ENTITY_ID;
    }


    public String[] mainColumns() {
        return new String[]{
//                getDemographicTable() + "." + AppConstants.KeyConstants.ID + " as _id",
////                getDemographicTable() + "." + AppConstants.KeyConstants.COUNTY_ID,
////                getDemographicTable() + "." + AppConstants.KeyConstants.ZEIR_ID,
//                getChildDetailsTable() + "." + AppConstants.KeyConstants.ZEIR_ID,
//                getDemographicTable() + "." + AppConstants.KeyConstants.GENDER,
//                getDemographicTable() + "." + AppConstants.KeyConstants.FIRST_NAME,
//                getDemographicTable() + "." + AppConstants.KeyConstants.LAST_NAME,
//                getDemographicTable() + "." + AppConstants.KeyConstants.DOB,
//                getDemographicTable() + "." + AppConstants.KeyConstants.REGISTRATION_DATE,
////                getChildDetailsTable() + "." + AppConstants.KeyConstants.PMTCT_STATUS,
//                getChildDetailsTable() + "." + AppConstants.KeyConstants.INACTIVE,
//                getChildDetailsTable() + "." + AppConstants.KeyConstants.LOST_TO_FOLLOW_UP,
//                getDemographicTable() + "." + "address1",
        };
    }

    public String getChildDetailsTable() {
        return AppConstants.RegisterTable.CHILD_DETAILS;
    }

    public String getDemographicTable() {
        return AppConstants.RegisterTable.CLIENT;
    }
}
