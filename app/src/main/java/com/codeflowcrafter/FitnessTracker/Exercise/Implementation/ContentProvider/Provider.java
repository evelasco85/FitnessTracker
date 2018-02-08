package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.ContentProvider;

import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;

/**
 * Created by enric on 08/02/2018.
 */

public class Provider extends ContentProviderTemplate {
    public Provider() {
        super(
                FitnessTrackerContentProviders.APPLICATION_NAME,
                FitnessTrackerContentProviders.GetInstance(),
                Exercise.PROVIDER_NAME, Exercise.TABLE_NAME,
                new Table());
    }
}
