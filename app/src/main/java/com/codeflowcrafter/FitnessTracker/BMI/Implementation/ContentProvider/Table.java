package com.codeflowcrafter.FitnessTracker.BMI.Implementation.ContentProvider;

import android.app.SearchManager;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.codeflowcrafter.DatabaseAccess.BaseTable;
import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by enric on 09/02/2018.
 */

public class Table extends BaseTable {
    public Table()
    {
        _search_projection_map = new HashMap<String, String>();

        _search_projection_map.put(SearchManager.SUGGEST_COLUMN_TEXT_1, BodyMassIndex.COLUMN_DATE + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        _search_projection_map.put("_id", BodyMassIndex.COLUMN_ID + " AS " + "_id");
    }

    public String GetRecordKeyColumnName()
    {
        return BodyMassIndex.COLUMN_ID;
    }

    public String GetTableName()
    {
        return BodyMassIndex.TABLE_NAME;
    }

    public String GetProviderName(){ return BodyMassIndex.PROVIDER_NAME; }

    public String GetTableCreationScript()
    {
        HashMap<String, String> tableColumns = BodyMassIndex.GetTableColumns();

        String fields = "";
        String terminator = ", ";

        for(Map.Entry<String, String> entry : tableColumns.entrySet()) {
            fields += entry.getKey() + " " + entry.getValue() + terminator;
        }

        return "create table " + BodyMassIndex.TABLE_NAME + " ( " +
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
                queryBuilder.appendWhere(BodyMassIndex.COLUMN_DATE + " LIKE \"% " + uri.getPathSegments().get(1) + "%\"");
                queryBuilder.setProjectionMap(GetSearchProjectionMap());
                break;
            default:
                break;
        }

        return queryBuilder;
    }
}
