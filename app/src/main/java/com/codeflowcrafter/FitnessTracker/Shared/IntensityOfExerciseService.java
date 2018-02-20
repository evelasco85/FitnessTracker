package com.codeflowcrafter.FitnessTracker.Shared;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 07/02/2018.
 */

public class IntensityOfExerciseService {
    private static final String INTENSITY_1 = "Light to moderate";
    private static final String INTENSITY_2 = "Moderate to vigorous";
    private static final String INTENSITY_3 = "Very vigorous";

    private List<IntensityOfExercise> _intensities =  new ArrayList<>();

    private static IntensityOfExerciseService s_instance = new IntensityOfExerciseService();
    public static IntensityOfExerciseService GetInstance(){return s_instance;}

    private IntensityOfExerciseService()
    {
        IntensityOfExercise intensity1 = new IntensityOfExercise(
                INTENSITY_1,
                55,
                65);
        IntensityOfExercise intensity2 = new IntensityOfExercise(
                INTENSITY_2,
                65,
                85);
        IntensityOfExercise intensity3 = new IntensityOfExercise(
                INTENSITY_3,
                85,
                100);

        _intensities.add(intensity1);
        _intensities.add(intensity2);
        _intensities.add(intensity3);
    }

    public List<String> GetIntensityDescriptions()
    {
        List<String> descriptions =  new ArrayList<>();

        for(int index = 0; index < _intensities.size(); ++index)
        {
            IntensityOfExercise intensity = _intensities.get(index);

            descriptions.add(intensity.GetDescription());
        }

        return descriptions;
    }

    public IntensityOfExercise GetIntensityByDescription(String description)
    {
        IntensityOfExercise intensityMatched = null;

        if(TextUtils.isEmpty(description)) return intensityMatched;

        for(int index = 0; index < _intensities.size(); ++index)
        {
            IntensityOfExercise intensity = _intensities.get(index);

            if(intensity == null) continue;

            if(description.equals(intensity.GetDescription()))
            {
                intensityMatched = intensity;
                break;
            }
        }

        return intensityMatched;
    }
}
