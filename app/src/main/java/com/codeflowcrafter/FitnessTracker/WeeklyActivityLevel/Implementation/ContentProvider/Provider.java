package com.codeflowcrafter.FitnessTracker.WeeklyActivityLevel.Implementation.ContentProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.WeeklyActivityLevel.Implementation.Domain.BasalMetabolicRate;
import com.codeflowcrafter.FitnessTracker.WeeklyActivityLevel.Implementation.Domain.Mapper;
import com.codeflowcrafter.FitnessTracker.WeeklyActivityLevel.Implementation.Domain.QueryObjects.QueryByProfileId;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 12/02/2018.
 */

public class Provider extends ContentProviderTemplate {
    public Provider() {
        super(
                FitnessTrackerContentProviders.APPLICATION_NAME,
                FitnessTrackerContentProviders.GetInstance(),
                new Table());
    }

    @Override
    public void RegisterDomain(
            Context context,
            ContentResolver resolver,
            IDataSynchronizationManager dsManager)
    {
        Uri uri = GetContentUri();
        Mapper mapper = new Mapper(resolver, uri);
        List<IBaseQueryObjectConcrete<BasalMetabolicRate>> queryObjects = new ArrayList<>();

        queryObjects.add(new QueryByProfileId(context, uri));
        dsManager.RegisterEntity(
                BasalMetabolicRate.class,
                mapper,
                queryObjects);
    }
}
