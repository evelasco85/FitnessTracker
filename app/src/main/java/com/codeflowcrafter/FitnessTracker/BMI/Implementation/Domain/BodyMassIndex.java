package com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

import java.util.HashMap;

/**
 * Created by enric on 09/02/2018.
 */

public class BodyMassIndex extends DomainObject {
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
}
