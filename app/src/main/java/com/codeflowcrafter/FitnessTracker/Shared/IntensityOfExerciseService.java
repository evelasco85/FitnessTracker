package com.codeflowcrafter.FitnessTracker.Shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 07/02/2018.
 */

public class IntensityOfExerciseService {
    private List<IntensityOfExercise> _intensities =  new ArrayList<>();

    private static IntensityOfExerciseService s_instance = new IntensityOfExerciseService();
    public static IntensityOfExerciseService GetInstance(){return s_instance;}

    private IntensityOfExerciseService()
    {
        IntensityOfExercise intensity1 = new IntensityOfExercise(
                "Light to moderate",
                55,
                65);
        IntensityOfExercise intensity2 = new IntensityOfExercise(
                "Moderate to vigorous",
                65,
                85);
        IntensityOfExercise intensity3 = new IntensityOfExercise(
                "Very vigorous",
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
}
