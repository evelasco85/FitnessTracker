package com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

import java.util.HashMap;

/**
 * Created by enric on 11/02/2018.
 */

public class RestingHeartRate extends DomainObject {
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
}
