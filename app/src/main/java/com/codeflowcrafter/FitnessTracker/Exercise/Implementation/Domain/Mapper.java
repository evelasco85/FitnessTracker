package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain;

import android.content.ContentResolver;
import android.net.Uri;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IInvocationDelegates;

import java.util.Hashtable;

/**
 * Created by enric on 08/02/2018.
 */

public class Mapper extends BaseMapper<Exercise> {
    public final static String KEY_MAPPER_NAME = "[Mapper Name]";
    public final static String KEY_OPERATION = "[Operation]";
    public final static String KEY_COUNT = "[Count]";

    private ContentResolver _resolver;
    private Uri _uri;

    public Mapper(ContentResolver resolver, Uri uri)
    {
        super(Exercise.class);

        _resolver = resolver;
        _uri = uri;
    }

    @Override
    public boolean ConcreteUpdate(Exercise entity, IInvocationDelegates invocationDelegates) {
        int updatedRecords = 0;

        if(entity == null)
            return false;

        String where = Exercise.COLUMN_ID + "=" +  entity.GetId();

        updatedRecords = _resolver
                .update(
                        _uri,
                        entity.GetContentValues(),
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
    public boolean ConcreteInsert(Exercise entity, IInvocationDelegates invocationDelegates) {
        _resolver
                .insert(
                        _uri,
                        entity.GetContentValues()
                );

        Hashtable results = new Hashtable();

        results.put(KEY_MAPPER_NAME, this.getClass().getName());
        results.put(KEY_OPERATION, "Insertion");
        results.put(KEY_COUNT, "1");

        invocationDelegates.SetResults(results);
        invocationDelegates.SuccessfulInvocation(entity);

        return true;
    }

    @Override
    public boolean ConcreteDelete(Exercise entity, IInvocationDelegates invocationDelegates) {
        String where = Exercise.COLUMN_ID + "=" +  entity.GetId();
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
