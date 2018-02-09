package com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.QueryObjects;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 06/02/2018.
 */

public class QueryAll extends BaseQueryObject<Profile, QueryAll.Criteria> {
    private Context _context;
    private Uri _uri;
    private IEntityTranslator<Profile> _translator;

    public static class Criteria {
        public Criteria() {
        }
    }

    public QueryAll(Context context, Uri uri, IEntityTranslator<Profile> translator) {
        super(QueryAll.Criteria.class);

        _context = context;
        _uri = uri;
        _translator = translator;
    }

    @Override
    public List<Profile> PerformSearchOperation(QueryAll.Criteria criteria) {

        CursorLoader loader = new CursorLoader(_context, _uri,
                null, null, null, null
        );

        Cursor cursor = loader.loadInBackground();
        List entityList = new ArrayList();

        if(cursor == null)
            return entityList;

        _translator.UpdateColumnOrdinals(cursor);

        while (cursor.moveToNext())
        {
            entityList.add(_translator.CursorToEntity(cursor));
        }

        cursor.close();

        return  entityList;
    }
}
