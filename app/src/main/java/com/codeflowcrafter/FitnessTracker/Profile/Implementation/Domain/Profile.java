package com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IContentRetriever;
import com.codeflowcrafter.FitnessTracker.Services.DomainService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;
import com.codeflowcrafter.Utilities.DateHelper;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by enric on 04/02/2018.
 */

public class Profile
        extends DomainObject
        implements IContentRetriever {
    public static final String PROVIDER_NAME = "ProfileProvider";
    public static final String TABLE_NAME = "profile";

    /*COLUMNS SECTION HERE*/
    public static final String COLUMN_ID = "_id";
    private int _id;
    public int GetId(){return  _id;}

    public static final String COLUMN_NAME = "name";
    private String _name;
    public String GetName() {return _name;}

    public static final String COLUMN_GENDER = "gender";
    private String _gender;
    public String GetGender() {return _gender;}

    public static final String COLUMN_DOB = "date_of_birth";
    private String _date_of_birth;
    public String GetDateOfBirth() {return _date_of_birth;}

    public static final String COLUMN_CREATED_AT = "created_at";
    private String _created_at;
    public String GetCreatedAt() {return _created_at;}

    public static final String COLUMN_HEIGHT_INCHES = "heightInches";
    private int _heightInches;
    public int GetHeightInches() {return _heightInches;}
    /**********************/

    public Profile(IBaseMapper mapper,
                   int id,
                   String name, String gender,
                   String date_of_birth, String created_at,
                   int heightInches)
    {
        super(mapper);

        _id = id;
        _name = name;
        _gender = gender;
        _date_of_birth = date_of_birth;
        _heightInches = heightInches;
        _created_at = created_at;

        if(TextUtils.isEmpty(created_at))
            _created_at = DateHelper.DateToString(new Date(), "yyyy-MM-dd HH:mm");
    }

    public Profile(
            IBaseMapper mapper,
            Cursor cursor,
            HashMap<String, Integer> ordinals)
    {
        this(
                mapper,
                cursor.getInt(ordinals.get(COLUMN_ID)),
                cursor.getString(ordinals.get(COLUMN_NAME)),
                cursor.getString(ordinals.get(COLUMN_GENDER)),
                cursor.getString(ordinals.get(COLUMN_DOB)),
                cursor.getString(ordinals.get(COLUMN_CREATED_AT)),
                cursor.getInt(ordinals.get(COLUMN_HEIGHT_INCHES))
        );
    }

    public static HashMap<String, String> GetTableColumns()
    {
        HashMap<String, String> tableColumns = new HashMap<String, String>();

        tableColumns.put(COLUMN_ID, "integer primary key autoincrement");
        tableColumns.put(COLUMN_NAME, "TEXT");
        tableColumns.put(COLUMN_GENDER, "integer");
        tableColumns.put(COLUMN_DOB, "DATETIME");
        tableColumns.put(COLUMN_CREATED_AT, "DATETIME default CURRENT_DATE");
        tableColumns.put(COLUMN_HEIGHT_INCHES, "integer");

        return tableColumns;
    }

    public static HashMap<String, Integer> GetOrdinals(Cursor cursor)
    {
        HashMap<String, Integer> ordinals = new HashMap<String, Integer>();

        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_ID);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_NAME);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_GENDER);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_DOB);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_CREATED_AT);
        DomainService.SetColumnOrdinal(cursor, ordinals, COLUMN_HEIGHT_INCHES);

        return ordinals;
    }

    public ContentValues GetContentValues()
    {
        ContentValues values = new ContentValues();

        if(_id > 0)
            values.put(Profile.COLUMN_ID, _id);

        values.put(COLUMN_NAME, _name);
        values.put(COLUMN_GENDER, _gender);
        values.put(COLUMN_DOB, _date_of_birth);
        values.put(COLUMN_CREATED_AT, _created_at);
        values.put(COLUMN_HEIGHT_INCHES, _heightInches);

        return values;
    }
}
