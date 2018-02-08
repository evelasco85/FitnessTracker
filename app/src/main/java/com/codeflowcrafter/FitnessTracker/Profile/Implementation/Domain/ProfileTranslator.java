package com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

/**
 * Created by enric on 04/02/2018.
 */

public class ProfileTranslator implements IEntityTranslator<Profile> {
    int _indexId = 0;
    int _indexName = 0;
    int _indexGender = 0;
    int _indexDoB = 0;
    int _indexCreatedAt = 0;
    int _indexHeighInches = 0;

    public void UpdateColumnOrdinals(Cursor cursor)
    {
        _indexId = cursor.getColumnIndexOrThrow(Profile.COLUMN_ID);
        _indexName = cursor.getColumnIndexOrThrow(Profile.COLUMN_NAME);
        _indexGender = cursor.getColumnIndexOrThrow(Profile.COLUMN_GENDER);
        _indexDoB = cursor.getColumnIndexOrThrow(Profile.COLUMN_DOB);
        _indexCreatedAt = cursor.getColumnIndexOrThrow(Profile.COLUMN_CREATED_AT);
        _indexHeighInches = cursor.getColumnIndexOrThrow(Profile.COLUMN_HEIGHT_INCHES);
    }

    //non-static to avoid race condition
    public Profile CursorToEntity(Cursor cursor)
    {
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(Profile.class);

        return new Profile(mapper,
                cursor.getInt(_indexId),
                cursor.getString(_indexName),
                cursor.getString(_indexGender),
                cursor.getString(_indexDoB),
                cursor.getString(_indexCreatedAt),
                cursor.getInt(_indexHeighInches)
        );
    }

    public ContentValues EntityToContentValues(Profile entity)
    {
        ContentValues values = new ContentValues();

        if(entity.GetId() > 0)
            values.put(Profile.COLUMN_ID, entity.GetId());

        values.put(Profile.COLUMN_NAME, entity.GetName());
        values.put(Profile.COLUMN_GENDER, entity.GetGender());
        values.put(Profile.COLUMN_DOB, entity.GetDateOfBirth());
        values.put(Profile.COLUMN_CREATED_AT, entity.GetCreatedAt());
        values.put(Profile.COLUMN_HEIGHT_INCHES, entity.GetHeightInches());

        return values;
    }
}
