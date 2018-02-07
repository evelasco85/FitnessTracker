package com.codeflowcrafter.DatabaseAccess;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.codeflowcrafter.DatabaseAccess.Interfaces.IContentProviderTemplate;

public class ContentProviderTemplate extends ContentProvider
        implements IContentProviderTemplate
{
    private static final String ROOT_AUTHORITY_BASE = "com.codeflowcrafter";

    public static final int URI_SEARCH_GENERIC = 1;
    public static final int URI_SEARCH_SPECIFIC = 2;
    public static final int URI_SEARCH_GLOBAL = 3;

    private BaseTable _tableTemplate;
    private DatabaseHelper _dbHelper;
    private BaseContentProviders _dataAccess;
    private String _fullProviderAuthorityName;
    private UriMatcher _uriMatcher;
    private Uri _contentUri;

    public ContentProviderTemplate(
            String applicationName, BaseContentProviders dataAccess,
            String providerAuthorityName, String uriPath,
            BaseTable tableTemplate)
    {
        _dataAccess = dataAccess;
        _tableTemplate = tableTemplate;
        _fullProviderAuthorityName = ROOT_AUTHORITY_BASE + "." + applicationName + "." + providerAuthorityName;
        _uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        _contentUri =  Uri.parse("content://" + _fullProviderAuthorityName + "/" + uriPath);

        _uriMatcher.addURI(_fullProviderAuthorityName, SearchManager.SUGGEST_URI_PATH_QUERY, URI_SEARCH_GLOBAL);
        _uriMatcher.addURI(_fullProviderAuthorityName, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", URI_SEARCH_GLOBAL);
        _uriMatcher.addURI(_fullProviderAuthorityName, SearchManager.SUGGEST_URI_PATH_SHORTCUT, URI_SEARCH_GLOBAL);
        _uriMatcher.addURI(_fullProviderAuthorityName, SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", URI_SEARCH_GLOBAL);
        _uriMatcher.addURI(_fullProviderAuthorityName, uriPath, ContentProviderTemplate.URI_SEARCH_GENERIC);
        _uriMatcher.addURI(_fullProviderAuthorityName, uriPath + "/#", ContentProviderTemplate.URI_SEARCH_SPECIFIC);

        _tableTemplate.SetContentProviderTemplate(this);
    }

    public UriMatcher GetUriMatcher()
    {
        return _uriMatcher;
    }

    public Uri GetContentUri()
    {
        return _contentUri;
    }

    public BaseTable GetUnderlyingTable()
    {
        return _tableTemplate;
    }

    @Override
    public boolean onCreate()
    {
        Context context = getContext();

        _dbHelper = _dataAccess.GetDatabaseHelper(context);

        return true;
    }

    @Override
    public String getType(Uri uri)
    {
        switch (_uriMatcher.match(uri))
        {
            case URI_SEARCH_GENERIC: return "vnd.android.cursor.dir/vnd.codeflowcrafter." + _tableTemplate.GetTableName();
            case URI_SEARCH_SPECIFIC: return "vnd.android.cursor.item/vnd.codeflowcrafter."  + _tableTemplate.GetTableName();
            case URI_SEARCH_GLOBAL: return SearchManager.SUGGEST_MIME_TYPE;
            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sort)
    {
        String orderBy;

        if(TextUtils.isEmpty(sort))
            orderBy = "";
        else
            orderBy = sort;

        SQLiteQueryBuilder queryBuilder = _tableTemplate.GetQueryBuilder(uri);
        Cursor cursor = queryBuilder.query(_dbHelper.getWritableDatabase(), projection, selection, selectionArgs, null, null, orderBy);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return  cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues)
    {
        SQLiteDatabase database = _dbHelper.getWritableDatabase();
        long rowId = database.insert(_tableTemplate.GetTableName(), "N/A", initialValues);

        if(rowId > 0)
        {
            Uri newUri = ContentUris.withAppendedId(_tableTemplate.GetContentUri(), rowId);

            getContext().getContentResolver().notifyChange(newUri, null);

            return newUri;
        }

        throw new android.database.SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs)
    {
        SQLiteDatabase database = _dbHelper.getWritableDatabase();

        int count;

        switch (_uriMatcher.match(uri))
        {
            case URI_SEARCH_GENERIC:
                count = database.delete(_tableTemplate.GetTableName(), where, whereArgs);
                break;
            case URI_SEARCH_SPECIFIC:
                String segment = uri.getPathSegments().get(1);

                count = database.delete(_tableTemplate.GetTableName(),
                        _tableTemplate.GetRecordKeyColumnName() +
                                "=" + segment +
                                (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] wArgs)
    {
        SQLiteDatabase database = _dbHelper.getWritableDatabase();

        int count;

        switch (_uriMatcher.match(uri))
        {
            case  URI_SEARCH_GENERIC:
                count = database.update(_tableTemplate.GetTableName(), values, where, wArgs);
                break;
            case URI_SEARCH_SPECIFIC:
                String segment = uri.getPathSegments().get(1);
                count = database.update(_tableTemplate.GetTableName(), values,
                        _tableTemplate.GetRecordKeyColumnName() + "=" + segment +
                                (!TextUtils.isEmpty(where) ? " AND (" + where + + ')' : ""),
                        wArgs
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return  count;
    }
}
