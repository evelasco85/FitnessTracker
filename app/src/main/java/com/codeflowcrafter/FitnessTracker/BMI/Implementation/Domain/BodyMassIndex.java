package com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IContentRetriever;
import com.codeflowcrafter.FitnessTracker.Services.DomainService;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IHeight;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IProfile;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IWeight;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

import java.util.HashMap;

/**
 * Created by enric on 09/02/2018.
 */

public class BodyMassIndex
        extends DomainObject
        implements IContentRetriever, IProfile, IHeight, IWeight
{
    public static final String PROVIDER_NAME = "BmiProvider";
    public static final String TABLE_NAME = "bmi";

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
    public void SetDate(String date){
        _date = date;}

    public static final String COLUMN_HEIGHT_INCHES = "heightInches";
    private int _heightInches;
    public int GetHeightInches() {return _heightInches;}
    public void SetHeightInches(int heightInches){ _heightInches = heightInches;}

    public static final String COLUMN_WEIGHT_LBS = "weightLbs";
    private double _weightLbs;
    public double GetWeightLbs() {return _weightLbs;}
    public void SetWeightLbs(double weightLbs){ _weightLbs = weightLbs;}
    /**********************/

    public BodyMassIndex(
            IBaseMapper mapper,
            int id,
            int profileId,
            String date,
            int heightInches, double weightLbs) {
        super(mapper);

        _id = id;
        _profileId = profileId;
        _date = date;
        _heightInches = heightInches;
        _weightLbs = weightLbs;
    }

    public BodyMassIndex(
            IBaseMapper mapper,
            Cursor cursor,
            HashMap<String, Integer> ordinals)
    {
        this(
                mapper,
                cursor.getInt(ordinals.get(COLUMN_ID)),
                cursor.getInt(ordinals.get(COLUMN_PROFILE_ID)),
                cursor.getString(ordinals.get(COLUMN_DATE)),
                cursor.getInt(ordinals.get(COLUMN_HEIGHT_INCHES)),
                cursor.getDouble(ordinals.get(COLUMN_WEIGHT_LBS))
        );
    }

    public static HashMap<String, String> GetTableColumns()
    {
        HashMap<String, String> tableColumns = new HashMap<String, String>();

        tableColumns.put(COLUMN_ID, "integer primary key autoincrement");
        tableColumns.put(COLUMN_PROFILE_ID, "integer");
        tableColumns.put(COLUMN_DATE, "DATETIME");
        tableColumns.put(COLUMN_HEIGHT_INCHES, "integer");
        tableColumns.put(COLUMN_WEIGHT_LBS, "REAL");

        return tableColumns;
    }

    public static HashMap<String, Integer> GetOrdinals(Cursor cursor)
    {
        HashMap<String, Integer> ordinals = new HashMap<String, Integer>();

        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_PROFILE_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_DATE);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_HEIGHT_INCHES);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_WEIGHT_LBS);

        return ordinals;
    }

    public ContentValues GetContentValues()
    {
        ContentValues values = new ContentValues();

        if(_id > 0)
            values.put(COLUMN_ID, _id);

        values.put(COLUMN_PROFILE_ID, _profileId);
        values.put(COLUMN_DATE, _date);
        values.put(COLUMN_HEIGHT_INCHES, _heightInches);
        values.put(COLUMN_WEIGHT_LBS, _weightLbs);

        return values;
    }
}
