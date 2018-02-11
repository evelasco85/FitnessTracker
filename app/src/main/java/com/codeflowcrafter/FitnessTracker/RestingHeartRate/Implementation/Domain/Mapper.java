package com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain;

import android.content.ContentResolver;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.FitnessTracker.TranslatorService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IInvocationDelegates;

import java.util.Hashtable;

/**
 * Created by enric on 11/02/2018.
 */

public class Mapper extends BaseMapper<RestingHeartRate> {
    public final static String KEY_MAPPER_NAME = "[Mapper Name]";
    public final static String KEY_OPERATION = "[Operation]";
    public final static String KEY_COUNT = "[Count]";

    private ContentResolver _resolver;
    private Uri _uri;
    private IEntityTranslator<RestingHeartRate> _translator = TranslatorService
            .GetInstance()
            .GetRhrTranslator();

    public Mapper(ContentResolver resolver, Uri uri)
    {
        super(RestingHeartRate.class);

        _resolver = resolver;
        _uri = uri;
    }

    @Override
    public boolean ConcreteUpdate(RestingHeartRate entity, IInvocationDelegates invocationDelegates) {
        int updatedRecords = 0;

        if(entity == null)
            return false;

        String where = RestingHeartRate.COLUMN_ID + "=" +  entity.GetId();

        updatedRecords = _resolver
                .update(
                        _uri,
                        _translator.EntityToContentValues(entity),
                        where, null);

        Hashtable results = new Hashtable();

        results.put(KEY_MAPPER_NAME, this.getClass().getName());
        results.put(KEY_OPERATION, "Update");
        results.put(KEY_COUNT, String.valueOf(updatedRecords));

        invocationDelegates.SetResults(results);
        invocationDelegates.SuccessfulInvocation(entity);

        return true;
    }

    @Override
    public boolean ConcreteInsert(RestingHeartRate entity, IInvocationDelegates invocationDelegates) {
        _resolver.insert(_uri, _translator.EntityToContentValues(entity));

        Hashtable results = new Hashtable();

        results.put(KEY_MAPPER_NAME, this.getClass().getName());
        results.put(KEY_OPERATION, "Insertion");
        results.put(KEY_COUNT, "1");

        invocationDelegates.SetResults(results);
        invocationDelegates.SuccessfulInvocation(entity);

        return true;
    }

    @Override
    public boolean ConcreteDelete(RestingHeartRate entity, IInvocationDelegates invocationDelegates) {
        String where = RestingHeartRate.COLUMN_ID + "=" +  entity.GetId();
        int deletedRecords = _resolver.delete(_uri, where, null );

        Hashtable results = new Hashtable();

        results.put(KEY_MAPPER_NAME, this.getClass().getName());
        results.put(KEY_OPERATION, "Deletion");
        results.put(KEY_COUNT, String.valueOf(deletedRecords));

        invocationDelegates.SetResults(results);
        invocationDelegates.SuccessfulInvocation(entity);

        return true;
    }
}
