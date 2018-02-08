package com.codeflowcrafter.FitnessTracker;

import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Translator;

/**
 * Created by enric on 06/02/2018.
 */

public class TranslatorService {
    private static TranslatorService s_instance = new TranslatorService();
    public static TranslatorService GetInstance()
    {
        return s_instance;
    }

    private com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Translator _profileTranslator = new com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Translator();
    public com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Translator GetProfileTranslator()
    {
        return _profileTranslator;
    }

    private Translator _exerciseTranslator = new Translator();
    public Translator GetExerciseTranslator()
    {
        return _exerciseTranslator;
    }
}
