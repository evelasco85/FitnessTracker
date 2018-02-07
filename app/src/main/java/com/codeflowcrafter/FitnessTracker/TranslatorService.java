package com.codeflowcrafter.FitnessTracker;

import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.ProfileTranslator;

/**
 * Created by enric on 06/02/2018.
 */

public class TranslatorService {
    private static TranslatorService s_instance = new TranslatorService();
    public static TranslatorService GetInstance()
    {
        return s_instance;
    }

    private ProfileTranslator _profileTranslator = new ProfileTranslator();
    public ProfileTranslator GetProfileTranslator()
    {
        return _profileTranslator;
    }
}
