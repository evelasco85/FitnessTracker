package com.codeflowcrafter.FitnessTracker.RepMax.Implementation.Domain.QueryObjects;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.Domain.RepMax;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObject;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enric on 22/02/2018.
 */

public class QueryByProfileId extends BaseQueryObject<RepMax, QueryByProfileId.Criteria> {
    private Context _context;
    private Uri _uri;

    public static class Criteria {
        public int ProfileId;

        public Criteria(int profileId) {
            ProfileId = profileId;
        }
    }

    public QueryByProfileId(Context context, Uri uri) {
        super(QueryByProfileId.Criteria.class);

        _context = context;
        _uri = uri;
    }

    @Override
    public List<RepMax> PerformSearchOperation(QueryByProfileId.Criteria criteria) {
        String where = RepMax.COLUMN_PROFILE_ID + "=" +  criteria.ProfileId;
        String sortOrder = RepMax.COLUMN_ID + " DESC";
        CursorLoader loader = new CursorLoader(_context, _uri,
                null, where, null, sortOrder
        );

        Cursor cursor = loader.loadInBackground();
        List entityList = new ArrayList();

        if(cursor == null)
            return entityList;

        HashMap<String, Integer> ordinals = RepMax.GetOrdinals(cursor);
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(RepMax.class);

        while (cursor.moveToNext())
        {
            entityList.add(new RepMax(mapper, cursor, ordinals));
        }

        cursor.close();

        return  entityList;
    }
}