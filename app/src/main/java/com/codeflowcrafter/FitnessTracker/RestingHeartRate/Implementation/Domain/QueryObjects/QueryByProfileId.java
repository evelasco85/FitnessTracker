package com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.QueryObjects;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObject;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enric on 11/02/2018.
 */

public class QueryByProfileId  extends BaseQueryObject<RestingHeartRate, QueryByProfileId.Criteria> {
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
    public List<RestingHeartRate> PerformSearchOperation(QueryByProfileId.Criteria criteria) {
        String where = RestingHeartRate.COLUMN_PROFILE_ID + "=" +  criteria.ProfileId;
        String sortOrder = RestingHeartRate.COLUMN_ID + " DESC";
        CursorLoader loader = new CursorLoader(_context, _uri,
                null, where, null, sortOrder
        );

        Cursor cursor = loader.loadInBackground();
        List entityList = new ArrayList();

        if(cursor == null)
            return entityList;

        HashMap<String, Integer> ordinals = RestingHeartRate.GetOrdinals(cursor);
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(RestingHeartRate.class);

        while (cursor.moveToNext())
        {
            entityList.add(new RestingHeartRate(mapper, cursor, ordinals));
        }

        cursor.close();

        return  entityList;
    }
}
