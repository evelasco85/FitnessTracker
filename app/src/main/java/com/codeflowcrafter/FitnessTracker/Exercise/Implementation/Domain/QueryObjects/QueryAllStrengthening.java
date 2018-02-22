package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.QueryObjects;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.Shared.ExerciseType;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObject;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enric on 22/02/2018.
 */

public class QueryAllStrengthening extends BaseQueryObject<Exercise, QueryAllStrengthening.Criteria> {
    private Context _context;
    private Uri _uri;

    public static class Criteria {
        public Criteria() {
        }
    }

    public QueryAllStrengthening(Context context, Uri uri) {
        super(QueryAllStrengthening.Criteria.class);

        _context = context;
        _uri = uri;
    }

    @Override
    public List<Exercise> PerformSearchOperation(QueryAllStrengthening.Criteria criteria) {
        String where = Exercise.COLUMN_TYPE + "=" +  ExerciseType.TYPE_STRENGTHENING;
        String sortOrder = Exercise.COLUMN_ID + " DESC";

        CursorLoader loader = new CursorLoader(_context, _uri,
                null, where, null, sortOrder
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