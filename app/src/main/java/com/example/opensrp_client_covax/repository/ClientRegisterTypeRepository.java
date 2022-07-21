package com.example.opensrp_client_covax.repository;

import android.content.ContentValues;

import androidx.annotation.NonNull;

import com.example.opensrp_client_covax.util.AppConstants;

import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.repository.BaseRepository;

import java.util.Date;

public class ClientRegisterTypeRepository extends BaseRepository implements ClientRegisterTypeDao {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + AppConstants.TableNameConstants.REGISTER_TYPE + "("
            + AppConstants.Columns.RegisterType.BASE_ENTITY_ID + " VARCHAR NOT NULL,"
            + AppConstants.Columns.RegisterType.REGISTER_TYPE + " VARCHAR NOT NULL, "
            + AppConstants.Columns.RegisterType.DATE_CREATED + " INTEGER NOT NULL, "
            + AppConstants.Columns.RegisterType.DATE_REMOVED + " INTEGER NULL, "
            + "UNIQUE(" + AppConstants.Columns.RegisterType.BASE_ENTITY_ID + ", " + AppConstants.Columns.RegisterType.REGISTER_TYPE + ") ON CONFLICT REPLACE)";

    private static final String INDEX_BASE_ENTITY_ID = "CREATE INDEX " + AppConstants.TableNameConstants.REGISTER_TYPE
            + "_" + AppConstants.Columns.RegisterType.BASE_ENTITY_ID + "_index ON " + AppConstants.TableNameConstants.REGISTER_TYPE +
            "(" + AppConstants.Columns.RegisterType.BASE_ENTITY_ID + " COLLATE NOCASE);";

    public static void createTable(@NonNull SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_SQL);
        database.execSQL(INDEX_BASE_ENTITY_ID);
    }

    @Override
    public boolean removeAll(String baseEntityId) {
        return false;
    }

    @Override
    public boolean add(String registerType, String baseEntityId) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConstants.Columns.RegisterType.BASE_ENTITY_ID, baseEntityId);
        contentValues.put(AppConstants.Columns.RegisterType.REGISTER_TYPE, registerType);
        contentValues.put(AppConstants.Columns.RegisterType.DATE_CREATED, new Date().getTime());
        long result = database.insert(AppConstants.TableNameConstants.REGISTER_TYPE, null, contentValues);
        return result != -1;
    }
}
