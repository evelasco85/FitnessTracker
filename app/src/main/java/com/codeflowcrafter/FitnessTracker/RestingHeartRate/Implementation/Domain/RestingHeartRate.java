package com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IContentRetriever;
import com.codeflowcrafter.FitnessTracker.Services.DomainService;
import com.codeflowcrafter.FitnessTracker.Shared.Domain.Interfaces.IAge;
import com.codeflowcrafter.FitnessTracker.Shared.Domain.Interfaces.IProfile;
import com.codeflowcrafter.FitnessTracker.Shared.Domain.Interfaces.IRestingHeartRate;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

import java.util.HashMap;

/**
 * Created by enric on 11/02/2018.
 */

public class RestingHeartRate
        extends DomainObject
        implements IContentRetriever, IProfile, IAge, IRestingHeartRate
{
    public static final String PROVIDER_NAME = "RhrProvider";
    public static final String TABLE_NAME = "rhr";

    /*COLUMNS SECTION HERE*/
    public static final String COLUMN_ID = "_id";
    private int _id;
    public int GetId(){return  _id;}

    public static final String COLUMN_PROFILE_ID = "profileId";
    private int _profileId;
    public int GetProfileId(){return  _profileId;}

    public static final String COLUMN_DATE = "date";
    private String _date;
    public String GetDate() {return _date;}

    public static final String COLUMN_AGE = "age";
    private int _age;
    public int GetAge() {return _age;}

    public static final String COLUMN_RESTING_HEART_RATE = "rhr";
    private int _rhr;
    public int GetRestingHeartRate() {return _rhr;}
    /**********************/

    public RestingHeartRate(
            IBaseMapper mapper,
            int id,
            int profileId,
            String date,
            int age,
            int rhr) {
        super(mapper);

        _id = id;
        _profileId = profileId;
        _date = date;
        _age = age;
        _rhr = rhr;
    }

    public RestingHeartRate(
            IBaseMapper mapper,
            Cursor cursor,
            HashMap<String, Integer> ordinals)
    {
        this(
                mapper,
                cursor.getInt(ordinals.get(COLUMN_ID)),
                cursor.getInt(ordinals.get(COLUMN_PROFILE_ID)),
                cursor.getString(ordinals.get(COLUMN_DATE)),
                cursor.getInt(ordinals.get(COLUMN_AGE)),
                cursor.getInt(ordinals.get(COLUMN_RESTING_HEART_RATE))
        );
    }

    public static HashMap<String, String> GetTableColumns()
    {
        HashMap<String, String> tableColumns = new HashMap<String, String>();

        tableColumns.put(COLUMN_ID, "integer primary key autoincrement");
        tableColumns.put(COLUMN_PROFILE_ID, "integer");
        tableColumns.put(COLUMN_DATE, "DATETIME");
        tableColumns.put(COLUMN_AGE, "integer");
        tableColumns.put(COLUMN_RESTING_HEART_RATE, "integer");

        return tableColumns;
    }

    public static HashMap<String, Integer> GetOrdinals(Cursor cursor)
    {
        HashMap<String, Integer> ordinals = new HashMap<String, Integer>();

        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_PROFILE_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_DATE);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_AGE);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_RESTING_HEART_RATE);

        return ordinals;
    }

    public ContentValues GetContentValues()
    {
        ContentValues values = new ContentValues();

        if(_id > 0)
            values.put(COLUMN_ID, _id);

        values.put(COLUMN_PROFILE_ID, _profileId);
        values.put(COLUMN_DATE, _date);
        values.put(COLUMN_AGE, _age);
        values.put(COLUMN_RESTING_HEART_RATE, _rhr);

        return values;
    }
}
