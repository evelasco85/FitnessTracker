package com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.QueryObjects;

import android.content.Context;
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

public class QueryById extends BaseQueryObject<Profile, QueryById.Criteria> {
    private Context _context;
    private Uri _uri;
    private IEntityTranslator<Profile> _translator;

    public static class Criteria {
        public int Id;

        public Criteria(int id) {
            Id = id;
        }
    }

    public QueryById(Context context, Uri uri, IEntityTranslator<Profile> translator) {
        super(QueryById.Criteria.class);

        _context = context;
        _uri = uri;
        _translator = translator;
    }

    @Override
    public List<Profile> PerformSearchOperation(Criteria criteria) {
        String where = Profile.COLUMN_ID + "=" +  criteria.Id;
        Cursor cursor = _context.getContentResolver().query(_uri, null, where, null, null);
        List entityList = new ArrayList();

        if(cursor == null) {
            return entityList;
        }

        _translator.UpdateColumnOrdinals(cursor);

        if(cursor.moveToFirst()) {
            entityList.add(_translator.CursorToEntity(cursor));
        }

        cursor.close();

        return entityList;
    }
}
