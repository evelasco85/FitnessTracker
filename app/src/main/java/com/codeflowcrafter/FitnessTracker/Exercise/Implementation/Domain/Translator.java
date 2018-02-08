package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

/**
 * Created by enric on 08/02/2018.
 */

public class Translator implements IEntityTranslator<Exercise> {
    int _indexId = 0;
    int _indexName = 0;
    int _indexDurationMinutes = 0;
    int _indexType = 0;
    int _indexIntensity = 0;

    public void UpdateColumnOrdinals(Cursor cursor)
    {
        _indexId = cursor.getColumnIndexOrThrow(Exercise.COLUMN_ID);
        _indexName = cursor.getColumnIndexOrThrow(Exercise.COLUMN_NAME);
        _indexDurationMinutes = cursor.getColumnIndexOrThrow(Exercise.COLUMN_DURATION_MINUTES);
        _indexType = cursor.getColumnIndexOrThrow(Exercise.COLUMN_TYPE);
        _indexIntensity = cursor.getColumnIndexOrThrow(Exercise.COLUMN_INTENSITY);
    }

    //non-static to avoid race condition
    public Exercise CursorToEntity(Cursor cursor)
    {
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(Exercise.class);

        return new Exercise(mapper,
                cursor.getInt(_indexId),
                cursor.getString(_indexName),
                cursor.getInt(_indexDurationMinutes),
                cursor.getString(_indexType),
                cursor.getString(_indexIntensity)
        );
    }

    public ContentValues EntityToContentValues(Exercise entity)
    {
        ContentValues values = new ContentValues();

        if(entity.GetId() > 0)
            values.put(Exercise.COLUMN_ID, entity.GetId());

        values.put(Exercise.COLUMN_NAME, entity.GetName());
        values.put(Exercise.COLUMN_DURATION_MINUTES, entity.GetDurationMinutes());
        values.put(Exercise.COLUMN_TYPE, entity.GetType());
        values.put(Exercise.COLUMN_INTENSITY, entity.GetIntensity());

        return values;
    }
}
