package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IContentRetriever;
import com.codeflowcrafter.FitnessTracker.Services.DomainService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

import java.util.HashMap;

/**
 * Created by enric on 08/02/2018.
 */

public class Exercise
        extends DomainObject
        implements IContentRetriever {
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

    public Exercise(
            IBaseMapper mapper,
            Cursor cursor,
            HashMap<String, Integer> ordinals)
    {
        this(
                mapper,
                cursor.getInt(ordinals.get(COLUMN_ID)),
                cursor.getString(ordinals.get(COLUMN_NAME)),
                cursor.getInt(ordinals.get(COLUMN_DURATION_MINUTES)),
                cursor.getString(ordinals.get(COLUMN_TYPE)),
                cursor.getString(ordinals.get(COLUMN_INTENSITY))
        );
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

    public static HashMap<String, Integer> GetOrdinals(Cursor cursor)
    {
        HashMap<String, Integer> ordinals = new HashMap<String, Integer>();

        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_NAME);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_DURATION_MINUTES);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_TYPE);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_INTENSITY);

        return ordinals;
    }

    public ContentValues GetContentValues()
    {
        ContentValues values = new ContentValues();

        if(_id > 0)
            values.put(COLUMN_ID, _id);

        values.put(COLUMN_NAME, _name);
        values.put(COLUMN_DURATION_MINUTES, _durationInMinutes);
        values.put(COLUMN_TYPE, _type);
        values.put(COLUMN_INTENSITY, _intensity);

        return values;
    }
}
