package com.codeflowcrafter.FitnessTracker;

import android.content.ContentResolver;
import android.content.Context;

import com.codeflowcrafter.DatabaseAccess.BaseContentProviders;
import com.codeflowcrafter.DatabaseAccess.DatabaseHelper;
import com.codeflowcrafter.DatabaseAccess.DatabaseHelperBuilder;
import com.codeflowcrafter.DatabaseAccess.Interfaces.IDatabaseHelperBuilder_Setup;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;

/**
 * Created by aiko on 5/1/17.
 */

public class FitnessTrackerContentProviders extends BaseContentProviders {
    public static final String APPLICATION_NAME = "FitnessTrackerApp";
    public static final String DATABASE_TAG_NAME = "FitnessTrackerDB";
    private static final String DATABASE_FILENAME = "fitnessTracker.db";

    private IDatabaseHelperBuilder_Setup _dbHelperSetup = DatabaseHelperBuilder
            .GetInstance()
            .SetDatabase(DATABASE_TAG_NAME, DATABASE_FILENAME);

    private static FitnessTrackerContentProviders _instance = new FitnessTrackerContentProviders();
    public static FitnessTrackerContentProviders GetInstance(){ return _instance;}

    private com.codeflowcrafter.FitnessTracker.Profile.Implementation.ContentProvider.Provider
            _profileProvider = new com.codeflowcrafter.FitnessTracker.Profile.Implementation
            .ContentProvider.Provider();
    private com.codeflowcrafter.FitnessTracker.Exercise.Implementation.ContentProvider.Provider
            _exerciseProvider = new com.codeflowcrafter.FitnessTracker.Exercise.Implementation
            .ContentProvider.Provider();
    private com.codeflowcrafter.FitnessTracker.BMI.Implementation.ContentProvider.Provider
            _bmiProvider = new com.codeflowcrafter.FitnessTracker.BMI.Implementation
            .ContentProvider.Provider();
    private com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.ContentProvider.Provider
            _rhrProvider = new com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation
            .ContentProvider.Provider();
    private com.codeflowcrafter.FitnessTracker.BMR.Implementation.ContentProvider.Provider
            _bmrProvider = new com.codeflowcrafter.FitnessTracker.BMR.Implementation
            .ContentProvider.Provider();
    private com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.ContentProvider.Provider
            _ehrProvider = new com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation
            .ContentProvider.Provider();
    private com.codeflowcrafter.FitnessTracker.RepMax.Implementation.ContentProvider.Provider
            _repMaxProvider = new com.codeflowcrafter.FitnessTracker.RepMax.Implementation
            .ContentProvider.Provider();

    private FitnessTrackerContentProviders() {
        _profileProvider.SetupTable(_dbHelperSetup);
        _exerciseProvider.SetupTable(_dbHelperSetup);
        _bmiProvider.SetupTable(_dbHelperSetup);
        _rhrProvider.SetupTable(_dbHelperSetup);
        _bmrProvider.SetupTable(_dbHelperSetup);
        _ehrProvider.SetupTable(_dbHelperSetup);
        _repMaxProvider.SetupTable(_dbHelperSetup);
    }

    public void RegisterDomains(
            Context context,
            ContentResolver resolver,
            IDataSynchronizationManager dsManager)
    {
        _profileProvider.RegisterDomain(context, resolver, dsManager);
        _exerciseProvider.RegisterDomain(context, resolver, dsManager);
        _bmiProvider.RegisterDomain(context, resolver, dsManager);
        _rhrProvider.RegisterDomain(context, resolver, dsManager);
        _bmrProvider.RegisterDomain(context, resolver, dsManager);
        _ehrProvider.RegisterDomain(context, resolver, dsManager);
        _repMaxProvider.RegisterDomain(context, resolver, dsManager);
    }

    public DatabaseHelper GetDatabaseHelper(Context context)
    {
        DatabaseHelper dbHelper = _dbHelperSetup.Create(context);

        return dbHelper;
    }

    public com.codeflowcrafter.FitnessTracker.Profile.Implementation.ContentProvider.Provider
    GetProfileProvider()
    {
        return _profileProvider;
    }

    public com.codeflowcrafter.FitnessTracker.Exercise.Implementation.ContentProvider.Provider
    GetExerciseProvider()
    {
        return _exerciseProvider;
    }

    public com.codeflowcrafter.FitnessTracker.BMI.Implementation.ContentProvider.Provider
    GetBmiProvider()
    {
        return _bmiProvider;
    }

    public com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.ContentProvider.Provider
    GetRhrProvider()
    {
        return _rhrProvider;
    }

    public com.codeflowcrafter.FitnessTracker.BMR.Implementation.ContentProvider.Provider
    GetBmrProvider()
    {
        return _bmrProvider;
    }

    public com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.ContentProvider.Provider
    GetEhrProvider()
    {
        return _ehrProvider;
    }

    public com.codeflowcrafter.FitnessTracker.RepMax.Implementation.ContentProvider.Provider
    GetRepMaxProvider()
    {
        return _repMaxProvider;
    }
}
