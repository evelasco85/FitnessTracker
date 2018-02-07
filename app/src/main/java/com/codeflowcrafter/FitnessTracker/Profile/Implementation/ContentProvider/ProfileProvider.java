package com.codeflowcrafter.FitnessTracker.Profile.Implementation.ContentProvider;

import com.codeflowcrafter.DatabaseAccess.ContentProviderTemplate;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;

/**
 * Created by enric on 04/02/2018.
 */

public class ProfileProvider extends ContentProviderTemplate {
    public ProfileProvider() {
        super(
                FitnessTrackerContentProviders.APPLICATION_NAME,
                FitnessTrackerContentProviders.GetInstance(),
                Profile.PROVIDER_NAME, Profile.TABLE_NAME,
                new ProfileTable());
    }
}
