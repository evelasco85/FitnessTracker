package com.codeflowcrafter.DatabaseAccess.Interfaces;

import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

/**
 * Created by aiko on 4/29/17.
 */
public interface IBaseTable
{
    Uri GetContentUri();
    String GetRecordKeyColumnName();
    String GetTableName();
    String GetTableCreationScript();
    HashMap<String, String> GetSearchProjectionMap();
    SQLiteQueryBuilder GetQueryBuilder(Uri uri);
}
