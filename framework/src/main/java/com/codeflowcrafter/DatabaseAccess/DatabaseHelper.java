package com.codeflowcrafter.DatabaseAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aiko on 4/29/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private String _tag;
    private String _databaseFilename;
    private static final int DATABASE_VERSION = 1;
    private HashMap<String, String> _tableScripts  = new HashMap<String, String>();

    public DatabaseHelper(
            String tagName, String databaseFilename,
            Context context, HashMap<String, String> tableStructureSet)
    {
        super(context, databaseFilename, null, DATABASE_VERSION);

        _tag = tagName;
        _databaseFilename = databaseFilename;
        _tableScripts.putAll(tableStructureSet);
    }

    @Override
    public  void onCreate(SQLiteDatabase db)
    {
        for(Map.Entry<String, String> table : _tableScripts.entrySet())
        {
            db.execSQL(table.getValue());
        }
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(_tag, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

        for(Map.Entry<String, String> table : _tableScripts.entrySet())
        {
            db.execSQL("DROP TABLE IF EXISTS " + table.getValue());
        }

        onCreate(db);
    }
}