package com.codeflowcrafter.FitnessTracker.BMI.Implementation.ContentProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.Mapper;
import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.QueryObjects.QueryByProfileId;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 09/02/2018.
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
        List<IBaseQueryObjectConcrete<BodyMassIndex>> queryObjects = new ArrayList<>();

        queryObjects.add(new QueryByProfileId(context, uri));
        dsManager.RegisterEntity(
                BodyMassIndex.class,
                mapper,
                queryObjects);
    }
}
