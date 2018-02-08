package com.codeflowcrafter.FitnessTracker.Profile.Implementation.ContentProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.DatabaseAccess.Interfaces.IDatabaseHelperBuilder_Setup;
import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Mapper;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.QueryObjects.QueryAll;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.QueryObjects.QueryById;
import com.codeflowcrafter.FitnessTracker.TranslatorService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 04/02/2018.
 */

public class Provider extends ContentProviderTemplate {
    public Provider() {
        super(
                FitnessTrackerContentProviders.APPLICATION_NAME,
                FitnessTrackerContentProviders.GetInstance(),
                Profile.PROVIDER_NAME, Profile.TABLE_NAME,
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
        List<IBaseQueryObjectConcrete<Profile>> queryObjects = new ArrayList<>();
        IEntityTranslator<Profile> translator = TranslatorService
                .GetInstance()
                .GetProfileTranslator();

        queryObjects.add(new QueryAll(context, uri, translator));
        queryObjects.add(new QueryById(context, uri, translator));

        dsManager.RegisterEntity(
                Profile.class,
                mapper,
                queryObjects);
    }
}
