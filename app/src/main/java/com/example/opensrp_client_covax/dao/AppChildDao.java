package com.example.opensrp_client_covax.dao;

import org.smartregister.child.dao.ChildDao;

import java.util.List;

public class AppChildDao extends ChildDao {
    public static String getBaseEntityIdByOpenSRPId(String openSRPId) {
        String sql = String.format("SELECT base_entity_id\n" +
                "FROM ec_client\n" +
                "WHERE opensrp_id = '%s'\n" +
                "  AND ec_client.date_removed is null\n" +
                "  AND ec_client.is_closed IS NOT '1';", openSRPId);

        DataMap<String> dataMap = cursor -> getCursorValue(cursor, "base_entity_id");

        List<String> result = readData(sql, dataMap);
        if (result == null || result.size() != 1)
            return null;

        return result.get(0);
    }

    public static boolean clientNeedsCard(String baseEntityId) {
        String sql = String.format("SELECT count(*) as count\n" +
                "FROM ec_child_details\n" +
                "WHERE ec_child_details.card_status = 'needs_card' COLLATE NOCASE\n" +
                "  AND ec_child_details.date_removed is null\n" +
                "  AND ec_child_details.is_closed IS NOT '1'\n" +
                "  AND ec_child_details.base_entity_id = '%s' COLLATE NOCASE;", baseEntityId);

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> result = readData(sql, dataMap);
        if (result == null || result.size() != 1)
            return false;

        return result.get(0) > 0;
    }

    public static int getDueVaccineCount(String vaccine) {
        String sql = "SELECT count(*) count\n" +
                "FROM alerts\n" +
                "WHERE scheduleName = '$s' COLLATE NOCASE\n" +
                "  AND startDate LIKE '%' || strftime('%Y-%m', date('now', 'start of month', '+1 month')) || '%'".replace("$s", vaccine);

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> result = readData(sql, dataMap);
        if (result == null || result.size() != 1)
            return result.get(0);

        return 0;
    }
}
