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

    private com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Translator
            _profileTranslator = new com.codeflowcrafter.FitnessTracker.Profile.Implementation
            .Domain.Translator();
    public com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Translator
    GetProfileTranslator()
    {
        return _profileTranslator;
    }

    private com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Translator
            _exerciseTranslator = new com.codeflowcrafter.FitnessTracker.Exercise.Implementation
            .Domain.Translator();
    public com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Translator
    GetExerciseTranslator()
    {
        return _exerciseTranslator;
    }

    private com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.Translator
            _bmiTranslator = new com.codeflowcrafter.FitnessTracker.BMI.Implementation
            .Domain.Translator();
    public com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.Translator
    GetBmiTranslator()
    {
        return _bmiTranslator;
    }
}
