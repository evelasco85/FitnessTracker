package com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain;

import android.text.TextUtils;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;
import com.codeflowcrafter.Utilities.DateHelper;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by enric on 04/02/2018.
 */

public class Profile extends DomainObject {
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
    public void SetGender(String gender){_gender = gender;}

    public static final String COLUMN_DOB = "date_of_birth";
    private String _date_of_birth;
    public String GetDateOfBirth() {return _date_of_birth;}
    public void SetDateOfBirth(String date_of_birth){_date_of_birth = date_of_birth;}

    public static final String COLUMN_CREATED_AT = "created_at";
    private String _created_at;
    public String GetCreatedAt() {return _created_at;}
    public void SetCreatedAt(String created_at){_created_at = created_at;}

    public static final String COLUMN_HEIGHT_INCHES = "heightInches";
    private int _heightInches;
    public int GetHeightInches() {return _heightInches;}
    public void SetHeightInches(int heightInches){ _heightInches = heightInches;}
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
}
