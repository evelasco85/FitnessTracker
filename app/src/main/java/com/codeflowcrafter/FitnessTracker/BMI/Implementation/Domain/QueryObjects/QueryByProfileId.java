package com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.QueryObjects;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObject;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enric on 09/02/2018.
 */

public class QueryByProfileId extends BaseQueryObject<BodyMassIndex, QueryByProfileId.Criteria> {
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
    public List<BodyMassIndex> PerformSearchOperation(QueryByProfileId.Criteria criteria) {
        String where = BodyMassIndex.COLUMN_PROFILE_ID + "=" +  criteria.ProfileId;
        String sortOrder = BodyMassIndex.COLUMN_ID + " DESC";
        CursorLoader loader = new CursorLoader(_context, _uri,
                null, where, null, sortOrder
        );

        Cursor cursor = loader.loadInBackground();
        List entityList = new ArrayList();

        if(cursor == null)
            return entityList;

        HashMap<String, Integer> ordinals = BodyMassIndex.GetOrdinals(cursor);
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(BodyMassIndex.class);

        while (cursor.moveToNext())
        {
            entityList.add(new BodyMassIndex(mapper, cursor, ordinals));
        }

        cursor.close();

        return  entityList;
    }
}
