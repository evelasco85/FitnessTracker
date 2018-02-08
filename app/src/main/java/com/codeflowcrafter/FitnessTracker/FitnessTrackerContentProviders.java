package com.codeflowcrafter.FitnessTracker;

import android.content.Context;

import com.codeflowcrafter.DatabaseAccess.BaseContentProviders;
import com.codeflowcrafter.DatabaseAccess.DatabaseHelper;
import com.codeflowcrafter.DatabaseAccess.DatabaseHelperBuilder;
import com.codeflowcrafter.DatabaseAccess.Interfaces.IDatabaseHelperBuilder_Setup;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.ContentProvider.Provider;

/**
 * Created by aiko on 5/1/17.
 */


public class FitnessTrackerContentProviders extends BaseContentProviders {

    public static final String APPLICATION_NAME = "FitnessTrackerApp";
    public static final String DATABASE_TAG_NAME = "FitnessTrackerDB";
    private static final String DATABASE_FILENAME = "fitnessTracker.db";

    private IDatabaseHelperBuilder_Setup _dbHelperSetup;

    private static FitnessTrackerContentProviders _instance = new FitnessTrackerContentProviders();
    public static FitnessTrackerContentProviders GetInstance(){ return _instance;}

    private com.codeflowcrafter.FitnessTracker.Profile.Implementation.ContentProvider.Provider _profileProvider = new com.codeflowcrafter.FitnessTracker.Profile.Implementation.ContentProvider.Provider();
    private Provider _exerciseProvider = new Provider();

    private FitnessTrackerContentProviders() {

        _dbHelperSetup = DatabaseHelperBuilder.GetInstance()
                .SetDatabase(DATABASE_TAG_NAME, DATABASE_FILENAME)
                .AddTable(
                        _profileProvider.GetUnderlyingTable().GetTableName(),
                        _profileProvider.GetUnderlyingTable().GetTableCreationScript()
                )
                .AddTable(
                        _exerciseProvider.GetUnderlyingTable().GetTableName(),
                        _exerciseProvider.GetUnderlyingTable().GetTableCreationScript()
                )
                ;
    }

    public com.codeflowcrafter.FitnessTracker.Profile.Implementation.ContentProvider.Provider GetProfileProvider()
    {
        return _profileProvider;
    }

    public Provider GetExerciseProvider()
    {
        return _exerciseProvider;
    }

    public DatabaseHelper GetDatabaseHelper(Context context)
    {
        DatabaseHelper dbHelper = _dbHelperSetup.Create(context);

        return dbHelper;
    }
}
