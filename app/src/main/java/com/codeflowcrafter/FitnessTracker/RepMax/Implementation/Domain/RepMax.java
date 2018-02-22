package com.codeflowcrafter.FitnessTracker.RepMax.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IContentRetriever;
import com.codeflowcrafter.FitnessTracker.Services.DomainService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

import java.util.HashMap;

/**
 * Created by enric on 22/02/2018.
 */

public class RepMax extends DomainObject
        implements IContentRetriever {
    public static final String PROVIDER_NAME = "RepMaxProvider";
    public static final String TABLE_NAME = "repMax";

    /*COLUMNS SECTION HERE*/
    public static final String COLUMN_ID = "_id";
    private int _id;
    public int GetId(){return  _id;}

    public static final String COLUMN_PROFILE_ID = "profileId";
    private int _profileId;
    public int GetProfileId(){return  _profileId;}

    public static final String COLUMN_START_DATE = "startDate";
    private String _startDate;
    public String GetStartDate() {return _startDate;}

    public static final String COLUMN_WEIGHT_LBS = "weightLbs";
    private double _weightLbs;
    public double GetWeightLbs() {return _weightLbs;}

    public static final String COLUMN_REPITITIONS = "repititions";
    private int _repititions;
    public int GetRepititions() {return _repititions;}
    /**********************/

    public RepMax(
            IBaseMapper mapper,
            int id,
            int profileId,
            String startDate,
            double weightLbs,
            int repititions) {
        super(mapper);

        _id = id;
        _profileId = profileId;
        _startDate = startDate;
        _weightLbs = weightLbs;
        _repititions = repititions;
    }

    public RepMax(
            IBaseMapper mapper,
            Cursor cursor,
            HashMap<String, Integer> ordinals)
    {
        this(
                mapper,
                cursor.getInt(ordinals.get(COLUMN_ID)),
                cursor.getInt(ordinals.get(COLUMN_PROFILE_ID)),
                cursor.getString(ordinals.get(COLUMN_START_DATE)),
                cursor.getDouble(ordinals.get(COLUMN_WEIGHT_LBS)),
                cursor.getInt(ordinals.get(COLUMN_REPITITIONS))
        );
    }

    public static HashMap<String, String> GetTableColumns()
    {
        HashMap<String, String> tableColumns = new HashMap<String, String>();

        tableColumns.put(COLUMN_ID, "integer primary key autoincrement");
        tableColumns.put(COLUMN_PROFILE_ID, "integer");
        tableColumns.put(COLUMN_START_DATE, "DATETIME");
        tableColumns.put(COLUMN_WEIGHT_LBS, "REAL");
        tableColumns.put(COLUMN_REPITITIONS, "integer");


        return tableColumns;
    }

    public static HashMap<String, Integer> GetOrdinals(Cursor cursor)
    {
        HashMap<String, Integer> ordinals = new HashMap<String, Integer>();

        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_PROFILE_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_START_DATE);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_WEIGHT_LBS);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_REPITITIONS);

        return ordinals;
    }

    public ContentValues GetContentValues()
    {
        ContentValues values = new ContentValues();

        if(_id > 0)
            values.put(COLUMN_ID, _id);

        values.put(COLUMN_PROFILE_ID, _profileId);
        values.put(COLUMN_START_DATE, _startDate);
        values.put(COLUMN_WEIGHT_LBS, _weightLbs);
        values.put(COLUMN_REPITITIONS, _repititions);

        return values;
    }
}
