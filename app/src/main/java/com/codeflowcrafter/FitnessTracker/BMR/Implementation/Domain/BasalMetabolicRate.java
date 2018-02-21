package com.codeflowcrafter.FitnessTracker.BMR.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IContentRetriever;
import com.codeflowcrafter.FitnessTracker.Services.DomainService;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IAge;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IHeight;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IProfile;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IWeight;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

import java.util.HashMap;

/**
 * Created by enric on 12/02/2018.
 */

public class BasalMetabolicRate
        extends DomainObject
        implements IContentRetriever, IProfile, IAge, IHeight, IWeight
{
    public static final String PROVIDER_NAME = "BmrProvider";
    public static final String TABLE_NAME = "bmr";

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

    public static final String COLUMN_AGE = "age";
    private int _age;
    public int GetAge() {return _age;}
    public void SetAge(int age){
        _age = age;}

    public static final String COLUMN_HEIGHT_INCHES = "heightInches";
    private int _heightInches;
    public int GetHeightInches() {return _heightInches;}
    public void SetHeightInches(int heightInches){ _heightInches = heightInches;}

    public static final String COLUMN_WEIGHT_LBS = "weightLbs";
    private double _weightLbs;
    public double GetWeightLbs() {return _weightLbs;}
    public void SetWeightLbs(double weightLbs){ _weightLbs = weightLbs;}

    public static final String COLUMN_LVL_ACTIVITY = "LevelOfActivity";
    private String _levelOfActivity;
    public String GetLevelOfActivity() {return _levelOfActivity;}
    public void SetLevelOfActivity(String levelOfActivity){
        _levelOfActivity = levelOfActivity;}
    /**********************/

    public BasalMetabolicRate(
            IBaseMapper mapper,
            int id,
            int profileId,
            String date,
            int age,
            int heightInches,
            double weightLbs,
            String levelOfActivity) {
        super(mapper);

        _id = id;
        _profileId = profileId;
        _date = date;
        _age = age;
        _heightInches = heightInches;
        _weightLbs = weightLbs;
        _levelOfActivity = levelOfActivity;
    }

    public BasalMetabolicRate(
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
                cursor.getInt(ordinals.get(COLUMN_HEIGHT_INCHES)),
                cursor.getDouble(ordinals.get(COLUMN_WEIGHT_LBS)),
                cursor.getString(ordinals.get(COLUMN_LVL_ACTIVITY))
        );
    }

    public static HashMap<String, String> GetTableColumns()
    {
        HashMap<String, String> tableColumns = new HashMap<String, String>();

        tableColumns.put(COLUMN_ID, "integer primary key autoincrement");
        tableColumns.put(COLUMN_PROFILE_ID, "integer");
        tableColumns.put(COLUMN_DATE, "DATETIME");
        tableColumns.put(COLUMN_AGE, "integer");
        tableColumns.put(COLUMN_HEIGHT_INCHES, "integer");
        tableColumns.put(COLUMN_WEIGHT_LBS, "double");
        tableColumns.put(COLUMN_LVL_ACTIVITY, "TEXT");

        return tableColumns;
    }

    public static HashMap<String, Integer> GetOrdinals(Cursor cursor)
    {
        HashMap<String, Integer> ordinals = new HashMap<String, Integer>();

        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_PROFILE_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_DATE);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_AGE);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_HEIGHT_INCHES);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_WEIGHT_LBS);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_LVL_ACTIVITY);

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
        values.put(COLUMN_HEIGHT_INCHES, _heightInches);
        values.put(COLUMN_WEIGHT_LBS, _weightLbs);
        values.put(COLUMN_LVL_ACTIVITY, _levelOfActivity);

        return values;
    }
}
