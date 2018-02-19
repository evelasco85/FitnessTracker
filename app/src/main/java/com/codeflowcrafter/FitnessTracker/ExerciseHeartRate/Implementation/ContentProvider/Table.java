package com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.ContentProvider;

import android.app.SearchManager;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.codeflowcrafter.DatabaseAccess.BaseTable;
import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain.ExerciseHeartRate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by enric on 19/02/2018.
 */

public class Table extends BaseTable {
    public Table()
    {
        _search_projection_map = new HashMap<String, String>();

        _search_projection_map.put(SearchManager.SUGGEST_COLUMN_TEXT_1, ExerciseHeartRate.COLUMN_DATE + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        _search_projection_map.put("_id", ExerciseHeartRate.COLUMN_ID + " AS " + "_id");
    }

    public String GetRecordKeyColumnName()
    {
        return ExerciseHeartRate.COLUMN_ID;
    }

    public String GetTableName()
    {
        return ExerciseHeartRate.TABLE_NAME;
    }

    public String GetProviderName(){ return ExerciseHeartRate.PROVIDER_NAME; }

    public String GetTableCreationScript()
    {
        HashMap<String, String> tableColumns = ExerciseHeartRate.GetTableColumns();

        String fields = "";
        String terminator = ", ";

        for(Map.Entry<String, String> entry : tableColumns.entrySet()) {
            fields += entry.getKey() + " " + entry.getValue() + terminator;
        }

        return "create table " + ExerciseHeartRate.TABLE_NAME + " ( " +
                ((fields.length() > 0) ? fields.substring(0, fields.length() - terminator.length()) : fields) +
                " );";
    }

    public SQLiteQueryBuilder GetQueryBuilder(Uri uri)
    {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(GetTableName());

        switch (GetUriMatcher().match(uri))
        {
            case ContentProviderTemplate.URI_SEARCH_SPECIFIC:
                queryBuilder.appendWhere(GetRecordKeyColumnName() + "=" + uri.getPathSegments().get(1));
                break;
            case ContentProviderTemplate.URI_SEARCH_GLOBAL:
                queryBuilder.appendWhere(ExerciseHeartRate.COLUMN_DATE + " LIKE \"% " + uri.getPathSegments().get(1) + "%\"");
                queryBuilder.setProjectionMap(GetSearchProjectionMap());
                break;
            default:
                break;
        }

        return queryBuilder;
    }
}