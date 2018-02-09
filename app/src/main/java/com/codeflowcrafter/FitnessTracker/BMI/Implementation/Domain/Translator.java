package com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

/**
 * Created by enric on 09/02/2018.
 */

public class Translator implements IEntityTranslator<BodyMassIndex> {
    int _indexId = 0;
    int _indexProfileId = 0;
    int _indexDate = 0;
    int _indexHeightInches = 0;
    int _indexWeightLbs = 0;

    public void UpdateColumnOrdinals(Cursor cursor)
    {
        _indexId = cursor.getColumnIndexOrThrow(BodyMassIndex.COLUMN_ID);
        _indexProfileId = cursor.getColumnIndexOrThrow(BodyMassIndex.COLUMN_PROFILE_ID);
        _indexDate = cursor.getColumnIndexOrThrow(BodyMassIndex.COLUMN_DATE);
        _indexHeightInches = cursor.getColumnIndexOrThrow(BodyMassIndex.COLUMN_HEIGHT_INCHES);
        _indexWeightLbs = cursor.getColumnIndexOrThrow(BodyMassIndex.COLUMN_WEIGHT_LBS);
    }

    //non-static to avoid race condition
    public BodyMassIndex CursorToEntity(Cursor cursor)
    {
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(BodyMassIndex.class);

        return new BodyMassIndex(mapper,
                cursor.getInt(_indexId),
                cursor.getInt(_indexProfileId),
                cursor.getString(_indexDate),
                cursor.getInt(_indexHeightInches),
                cursor.getDouble(_indexWeightLbs)
        );
    }

    public ContentValues EntityToContentValues(BodyMassIndex entity)
    {
        ContentValues values = new ContentValues();

        if(entity.GetId() > 0)
            values.put(BodyMassIndex.COLUMN_ID, entity.GetId());

        values.put(BodyMassIndex.COLUMN_PROFILE_ID, entity.GetProfileId());
        values.put(BodyMassIndex.COLUMN_DATE, entity.GetDate());
        values.put(BodyMassIndex.COLUMN_HEIGHT_INCHES, entity.GetHeightInches());
        values.put(BodyMassIndex.COLUMN_WEIGHT_LBS, entity.GetWeightLbs());

        return values;
    }
}
