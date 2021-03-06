package com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain;

import android.content.ContentResolver;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.Domain.BasalMetabolicRate;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain.ExerciseHeartRate;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IInvocationDelegates;

import java.util.Hashtable;

/**
 * Created by enric on 06/02/2018.
 */

public class Mapper extends BaseMapper<Profile> {
    public final static String KEY_MAPPER_NAME = "[Mapper Name]";
    public final static String KEY_OPERATION = "[Operation]";
    public final static String KEY_COUNT = "[Count]";

    private ContentResolver _resolver;
    private Uri _uri;

    public Mapper(ContentResolver resolver, Uri uri)
    {
        super(Profile.class);

        _resolver = resolver;
        _uri = uri;
    }

    @Override
    public boolean ConcreteUpdate(Profile entity, IInvocationDelegates invocationDelegates) {
        int updatedRecords = 0;

        if(entity == null)
            return false;

        String where = Profile.COLUMN_ID + "=" +  entity.GetId();

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
    public boolean ConcreteInsert(Profile entity, IInvocationDelegates invocationDelegates) {
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
    public boolean ConcreteDelete(Profile entity, IInvocationDelegates invocationDelegates) {
        String where = Profile.COLUMN_ID + "=" +  entity.GetId();
        int deletedRecords = _resolver.delete(_uri, where, null );

        Hashtable results = new Hashtable();

        results.put(KEY_MAPPER_NAME, this.getClass().getName());
        results.put(KEY_OPERATION, "Deletion");
        results.put(KEY_COUNT, String.valueOf(deletedRecords));

        if(deletedRecords > 0)
        {
            int profileId = entity.GetId();

            DeleteBMI(results, profileId);
            DeleteRHR(results, profileId);
            DeleteBMR(results, profileId);
            DeleteEHR(results, profileId);
        }

        invocationDelegates.SetResults(results);
        invocationDelegates.SuccessfulInvocation(entity);

        return true;
    }

    private void DeleteBMI(Hashtable results, int profileId)
    {
        String keyColumn = BodyMassIndex.COLUMN_PROFILE_ID;
        Uri uri = FitnessTrackerContentProviders
                .GetInstance()
                .GetBmiProvider()
                .GetContentUri();

        Delete(results, keyColumn, profileId, uri, "BMI");
    }

    private void DeleteRHR(Hashtable results, int profileId)
    {
        String keyColumn = RestingHeartRate.COLUMN_PROFILE_ID;
        Uri uri = FitnessTrackerContentProviders
                .GetInstance()
                .GetRhrProvider()
                .GetContentUri();

        Delete(results, keyColumn, profileId, uri, "RHR");
    }

    private void DeleteBMR(Hashtable results, int profileId)
    {
        String keyColumn = BasalMetabolicRate.COLUMN_PROFILE_ID;
        Uri uri = FitnessTrackerContentProviders
                .GetInstance()
                .GetBmrProvider()
                .GetContentUri();

        Delete(results, keyColumn, profileId, uri, "BMR");
    }

    private void DeleteEHR(Hashtable results, int profileId)
    {
        String keyColumn = ExerciseHeartRate.COLUMN_PROFILE_ID;
        Uri uri = FitnessTrackerContentProviders
                .GetInstance()
                .GetEhrProvider()
                .GetContentUri();

        Delete(results, keyColumn, profileId, uri, "EHR");
    }

    private void Delete(Hashtable results, String keyColumn, int keyValue, Uri uri, String module)
    {
        String where = keyColumn + "=" +  keyValue;
        int deletedRecords = _resolver.delete(uri, where, null );

        results.put("[" + module  + ": Operation]", "Deletion");
        results.put("[" + module  + ": Count]", String.valueOf(deletedRecords));
    }
}
