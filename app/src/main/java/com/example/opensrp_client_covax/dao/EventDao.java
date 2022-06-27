package com.example.opensrp_client_covax.dao;

import com.example.opensrp_client_covax.application.CovacsApplication;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.dao.AbstractDao;
import org.smartregister.repository.BaseRepository;
import org.smartregister.sync.helper.ECSyncHelper;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class EventDao extends AbstractDao {
    /***
     * This method forces the device to resync all the data without an event id
     * NB: Critical bug detected on Sync
     */
    public static void markEventsForReUpload() {
        updateDB("update client set syncStatus = '" + BaseRepository.TYPE_Unsynced + "' where baseEntityId in (select baseEntityId from event where ifnull(eventId,'') = '') ");
        updateDB("update ImageList set syncStatus = '" + BaseRepository.TYPE_Unsynced + "' where entityId in (select baseEntityId from event where ifnull(eventId,'') = '') ");
        updateDB("update event set syncStatus = '" + BaseRepository.TYPE_Unsynced + "' where ifnull(eventId,'') = '' ");
    }

    /***
     * Computes the minimum server Id before any possibly unsynced event found in the database.
     * This method should return a NULL value if all events are synced.
     * @return
     */
    public static Long getMinimumVerifiedServerVersion() {
        String sql = "select max(serverVersion) serverVersion from event where dateCreated <= (select min(dateCreated) minDate from event where ifnull(eventId,'') = '' and syncStatus = 'Synced') ";
        DataMap<Long> dataMap = c -> {
            return getCursorLongValue(c, "serverVersion");
        };
        return AbstractDao.readSingleValue(sql, dataMap);
    }

    public static List<Event> getEvents(String baseEntityID, String eventType, int limit) {
        String sql = "select json from event where baseEntityId = '" + baseEntityID + "' COLLATE NOCASE and eventType = '" + eventType + "' COLLATE NOCASE order by updatedAt desc limit " + limit;

        final ECSyncHelper syncHelper = CovacsApplication.getInstance().getEcSyncHelper();
        DataMap<Event> dataMap = c -> {
            try {
                return syncHelper.convert(new JSONObject(getCursorValue(c, "json")), Event.class);
            } catch (JSONException e) {
                Timber.e(e);
            }
            return null;
        };
        return AbstractDao.readData(sql, dataMap);
    }

    @Nullable
    public static Event getLatestEvent(String baseEntityID, List<String> eventTypes) {
        StringBuilder types = new StringBuilder();
        for (String eventType : eventTypes) {
            if (types.length() > 0)
                types.append(" , ");

            types.append("'").append(eventType).append("'");
        }

        String sql = "select json from event where baseEntityId = '" + baseEntityID + "' COLLATE NOCASE and eventType in (" + types.toString() + ") COLLATE NOCASE order by updatedAt desc limit 1";

        final ECSyncHelper syncHelper = CovacsApplication.getInstance().getEcSyncHelper();
        DataMap<Event> dataMap = c -> {
            try {
                return syncHelper.convert(new JSONObject(getCursorValue(c, "json")), Event.class);
            } catch (JSONException e) {
                Timber.e(e);
            }
            return null;
        };

        List<Event> res = AbstractDao.readData(sql, dataMap);
        if (res != null && res.size() > 0)
            return res.get(0);

        return null;
    }

    public static List<org.smartregister.domain.Event> getUnprocessedEvents(String[] events) {

        StringBuilder builder = new StringBuilder();
        int size = events.length;
        int x = 0;
        while (x < size) {
            builder.append("'").append(events[x]).append("'");

            if (x < (size - 1))
                builder.append(",");
            x++;
        }

        String sql = "select event.json event_json from event " +
                "where event.formSubmissionId not in (select form_submission_id from visits) and event.eventType in (" + builder.toString() + ") " +
                "order by event.rowid asc ";

        List<org.smartregister.domain.Event> results = new ArrayList<>();

        final ECSyncHelper syncHelper = CovacsApplication.getInstance().getEcSyncHelper();
        DataMap<Void> dataMap = c -> {
            try {
                org.smartregister.domain.Event event = syncHelper.convert(new JSONObject(getCursorValue(c, "event_json")), org.smartregister.domain.Event.class);
                results.add(event);
                return null;
            } catch (JSONException e) {
                Timber.e(e);
            }
            return null;
        };

        AbstractDao.readData(sql, dataMap);

        return results;
    }
}
