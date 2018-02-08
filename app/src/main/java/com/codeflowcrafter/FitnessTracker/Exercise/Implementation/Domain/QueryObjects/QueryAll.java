package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.QueryObjects;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 08/02/2018.
 */

public class QueryAll extends BaseQueryObject<Exercise, QueryAll.Criteria> {
    private Context _context;
    private Uri _uri;
    private IEntityTranslator<Exercise> _translator;

    public static class Criteria {
        public Criteria() {
        }
    }

    public QueryAll(Context context, Uri uri, IEntityTranslator<Exercise> translator) {
        super(QueryAll.Criteria.class);

        _context = context;
        _uri = uri;
        _translator = translator;
    }

    @Override
    public List<Exercise> PerformSearchOperation(QueryAll.Criteria criteria) {

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

        return  entityList;
    }
}