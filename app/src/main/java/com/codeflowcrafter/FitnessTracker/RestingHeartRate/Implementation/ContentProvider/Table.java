package com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.ContentProvider;

import android.app.SearchManager;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.codeflowcrafter.DatabaseAccess.BaseTable;
import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by enric on 11/02/2018.
 */

public class Table extends BaseTable {
    public Table()
    {
        _search_projection_map = new HashMap<String, String>();

        _search_projection_map.put(SearchManager.SUGGEST_COLUMN_TEXT_1, RestingHeartRate.COLUMN_DATE + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        _search_projection_map.put("_id", RestingHeartRate.COLUMN_ID + " AS " + "_id");
    }

    public String GetRecordKeyColumnName()
    {
        return RestingHeartRate.COLUMN_ID;
    }

    public String GetTableName()
    {
        return RestingHeartRate.TABLE_NAME;
    }

    public String GetProviderName(){ return RestingHeartRate.PROVIDER_NAME; }

    public String GetTableCreationScript()
    {
        HashMap<String, String> tableColumns = RestingHeartRate.GetTableColumns();

        String fields = "";
        String terminator = ", ";

        for(Map.Entry<String, String> entry : tableColumns.entrySet()) {
            fields += entry.getKey() + " " + entry.getValue() + terminator;
        }

        return "create table " + RestingHeartRate.TABLE_NAME + " ( " +
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
                queryBuilder.appendWhere(RestingHeartRate.COLUMN_DATE + " LIKE \"% " + uri.getPathSegments().get(1) + "%\"");
                queryBuilder.setProjectionMap(GetSearchProjectionMap());
                break;
            default:
                break;
        }

        return queryBuilder;
    }
}
