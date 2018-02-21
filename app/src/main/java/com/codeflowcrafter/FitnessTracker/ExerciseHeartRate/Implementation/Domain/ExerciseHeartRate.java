package com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IContentRetriever;
import com.codeflowcrafter.FitnessTracker.Services.DomainService;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IAge;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IProfile;
import com.codeflowcrafter.FitnessTracker.Shared.DomainInterfaces.IRestingHeartRate;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

import java.util.HashMap;

/**
 * Created by enric on 19/02/2018.
 */

public class ExerciseHeartRate extends DomainObject
        implements IContentRetriever, IProfile, IAge, IRestingHeartRate
{
    public static final String PROVIDER_NAME = "EhrProvider";
    public static final String TABLE_NAME = "ehr";

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

    public static final String COLUMN_RESTING_HEART_RATE = "rhr";
    private int _rhr;
    public int GetRestingHeartRate() {return _rhr;}
    public void SetRestingHeartRate(int rhr){ _rhr = rhr;}

    public static final String COLUMN_EXERCISE_HEART_RATE = "exerciseHeartRate";
    private int _exerciseHeartRate;
    public int GetExerciseHeartRate() {return _exerciseHeartRate;}
    public void SetExerciseHeartRate(int exerciseHeartRate){ _exerciseHeartRate = exerciseHeartRate;}

    public static final String COLUMN_RECOVERY_HEART_RATE = "recoveryHeartRate";
    private int _recoveryHeartRate;
    public int GetRecoveryHeartRate() {return _recoveryHeartRate;}
    public void SetRecoveryHeartRate(int recoveryHeartRate){ _recoveryHeartRate = recoveryHeartRate;}

    public static final String COLUMN_EXERCISE = "exercise";
    private String _exercise;
    public String GetExercise() {return _exercise;}
    public void SetExercise(String exercise){ _exercise = exercise;}
    /**********************/

    public ExerciseHeartRate(
            IBaseMapper mapper,
            int id,
            int profileId,
            String date,
            int age,
            int rhr,
            int exerciseHeartRate,
            int recoveryHeartRate,
            String exercise) {
        super(mapper);

        _id = id;
        _profileId = profileId;
        _date = date;
        _age = age;
        _rhr = rhr;
        _exerciseHeartRate = exerciseHeartRate;
        _recoveryHeartRate = recoveryHeartRate;
        _exercise = exercise;
    }

    public ExerciseHeartRate(
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
                cursor.getInt(ordinals.get(COLUMN_RESTING_HEART_RATE)),
                cursor.getInt(ordinals.get(COLUMN_EXERCISE_HEART_RATE)),
                cursor.getInt(ordinals.get(COLUMN_RECOVERY_HEART_RATE)),
                cursor.getString(ordinals.get(COLUMN_EXERCISE))
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
        tableColumns.put(COLUMN_EXERCISE_HEART_RATE, "integer");
        tableColumns.put(COLUMN_RECOVERY_HEART_RATE, "integer");
        tableColumns.put(COLUMN_EXERCISE, "TEXT");

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
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_EXERCISE_HEART_RATE);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_RECOVERY_HEART_RATE);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_EXERCISE);

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
        values.put(COLUMN_EXERCISE_HEART_RATE, _exerciseHeartRate);
        values.put(COLUMN_RECOVERY_HEART_RATE, _recoveryHeartRate);
        values.put(COLUMN_EXERCISE, _exercise);

        return values;
    }
}
