package com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.ContentProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.Mapper;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.QueryObjects.QueryByProfileId;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.FitnessTracker.TranslatorService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 11/02/2018.
 */

public class Provider  extends ContentProviderTemplate {
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
        List<IBaseQueryObjectConcrete<RestingHeartRate>> queryObjects = new ArrayList<>();
        IEntityTranslator<RestingHeartRate> translator = TranslatorService
                .GetInstance()
                .GetRhrTranslator();

        queryObjects.add(new QueryByProfileId(context, uri, translator));
        dsManager.RegisterEntity(
                RestingHeartRate.class,
                mapper,
                queryObjects);
    }
}