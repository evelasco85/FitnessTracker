package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.QueryObjects;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObject;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enric on 08/02/2018.
 */

public class QueryAll extends BaseQueryObject<Exercise, QueryAll.Criteria> {
    private Context _context;
    private Uri _uri;

    public static class Criteria {
        public Criteria() {
        }
    }

    public QueryAll(Context context, Uri uri) {
        super(QueryAll.Criteria.class);

        _context = context;
        _uri = uri;
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

        HashMap<String, Integer> ordinals = Exercise.GetOrdinals(cursor);
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(Exercise.class);

        while (cursor.moveToNext())
        {
            entityList.add(new Exercise(mapper, cursor, ordinals));
        }

        cursor.close();

        return  entityList;
    }
}