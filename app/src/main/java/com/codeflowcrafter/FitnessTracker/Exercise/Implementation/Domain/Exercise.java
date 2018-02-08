package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

import java.util.HashMap;

/**
 * Created by enric on 08/02/2018.
 */

public class Exercise extends DomainObject {
    public static final String PROVIDER_NAME = "ExerciseProvider";
    public static final String TABLE_NAME = "exercise";

    /*COLUMNS SECTION HERE*/
    public static final String COLUMN_ID = "_id";
    private int _id;
    public int GetId(){return  _id;}

    public static final String COLUMN_NAME = "name";
    private String _name;
    public String GetName() {return _name;}

    public static final String COLUMN_DURATION_MINUTES = "durationInMinutes";
    private int _durationInMinutes;
    public int GetDurationMinutes() {return _durationInMinutes;}

    public static final String COLUMN_TYPE = "type";
    private String _type;
    public String GetType() {return _type;}

    public static final String COLUMN_INTENSITY = "intensity";
    private String _intensity;
    public String GetIntensity() {return _intensity;}
    /**********************/

    public Exercise(IBaseMapper mapper,
                   int id,
                   String name, int durationInMinutes,
                   String type, String intensity) {
        super(mapper);

        _id = id;
        _name = name;
        _durationInMinutes = durationInMinutes;
        _type = type;
        _intensity = intensity;
    }

    public static HashMap<String, String> GetTableColumns()
    {
        HashMap<String, String> tableColumns = new HashMap<String, String>();

        tableColumns.put(COLUMN_ID, "integer primary key autoincrement");
        tableColumns.put(COLUMN_NAME, "TEXT");
        tableColumns.put(COLUMN_DURATION_MINUTES, "integer");
        tableColumns.put(COLUMN_TYPE, "TEXT");
        tableColumns.put(COLUMN_INTENSITY, "TEXT");

        return tableColumns;
    }
}
