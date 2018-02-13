package com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.QueryObjects;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObject;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enric on 06/02/2018.
 */

public class QueryById extends BaseQueryObject<Profile, QueryById.Criteria> {
    private Context _context;
    private Uri _uri;

    public static class Criteria {
        public int Id;

        public Criteria(int id) {
            Id = id;
        }
    }

    public QueryById(Context context, Uri uri) {
        super(QueryById.Criteria.class);

        _context = context;
        _uri = uri;
    }

    @Override
    public List<Profile> PerformSearchOperation(Criteria criteria) {
        String where = Profile.COLUMN_ID + "=" +  criteria.Id;
        Cursor cursor = _context.getContentResolver().query(_uri, null, where, null, null);
        List entityList = new ArrayList();

        if(cursor == null) {
            return entityList;
        }

        HashMap<String, Integer> ordinals = Profile.GetOrdinals(cursor);
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(Profile.class);

        if(cursor.moveToFirst()) {
            entityList.add(new Profile(mapper, cursor, ordinals));
        }

        cursor.close();

        return entityList;
    }
}
