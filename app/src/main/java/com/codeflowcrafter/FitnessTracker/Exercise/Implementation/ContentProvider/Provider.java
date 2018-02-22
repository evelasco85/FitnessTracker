package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.ContentProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Mapper;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.QueryObjects.QueryAll;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.QueryObjects.QueryAllStrengthening;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 08/02/2018.
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
        List<IBaseQueryObjectConcrete<Exercise>> queryObjects = new ArrayList<>();

        queryObjects.add(new QueryAll(context, uri));
        queryObjects.add(new QueryAllStrengthening(context, uri));
        dsManager.RegisterEntity(
                Exercise.class,
                mapper,
                queryObjects);
    }
}
