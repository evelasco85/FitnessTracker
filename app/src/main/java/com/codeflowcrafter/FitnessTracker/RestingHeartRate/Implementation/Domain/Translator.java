package com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

/**
 * Created by enric on 11/02/2018.
 */

public class Translator implements IEntityTranslator<RestingHeartRate> {
    int _indexId = 0;
    int _indexProfileId = 0;
    int _indexDate = 0;
    int _indexAge = 0;
    int _indexRestingHeartRate = 0;

    public void UpdateColumnOrdinals(Cursor cursor)
    {
        _indexId = cursor.getColumnIndexOrThrow(RestingHeartRate.COLUMN_ID);
        _indexProfileId = cursor.getColumnIndexOrThrow(RestingHeartRate.COLUMN_PROFILE_ID);
        _indexDate = cursor.getColumnIndexOrThrow(RestingHeartRate.COLUMN_DATE);
        _indexAge = cursor.getColumnIndexOrThrow(RestingHeartRate.COLUMN_AGE);
        _indexRestingHeartRate = cursor.getColumnIndexOrThrow(RestingHeartRate.COLUMN_RESTING_HEART_RATE);
    }

    //non-static to avoid race condition
    public RestingHeartRate CursorToEntity(Cursor cursor)
    {
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(RestingHeartRate.class);

        return new RestingHeartRate(mapper,
                cursor.getInt(_indexId),
                cursor.getInt(_indexProfileId),
                cursor.getString(_indexDate),
                cursor.getInt(_indexAge),
                cursor.getInt(_indexRestingHeartRate)
        );
    }

    public ContentValues EntityToContentValues(RestingHeartRate entity)
    {
        ContentValues values = new ContentValues();

        if(entity.GetId() > 0)
            values.put(RestingHeartRate.COLUMN_ID, entity.GetId());

        values.put(RestingHeartRate.COLUMN_PROFILE_ID, entity.GetProfileId());
        values.put(RestingHeartRate.COLUMN_DATE, entity.GetDate());
        values.put(RestingHeartRate.COLUMN_AGE, entity.GetAge());
        values.put(RestingHeartRate.COLUMN_RESTING_HEART_RATE, entity.GetRestingHeartRate());

        return values;
    }
}
