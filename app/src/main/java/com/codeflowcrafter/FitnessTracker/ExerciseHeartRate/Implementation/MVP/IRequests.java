package com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.MVP;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IRequests;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain.ExerciseHeartRate;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.FitnessTracker.Shared.IntensityOfExercise;

import java.util.List;

/**
 * Created by enric on 19/02/2018.
 */

public interface IRequests extends Crud_IRequests<ExerciseHeartRate> {
    List<ExerciseHeartRate> GetData(int profileId);
    List<Exercise> GetExercisesData();

    RestingHeartRate GetLatestRestingHeartRate(int profileId);
    int GetMhr(int age);
    IntensityOfExercise GetIntensityOfExercise(List<Exercise> exercises, String exercise);
    String GetZoneRange(int maximumHeartRate, int restingHeartRate, IntensityOfExercise intensity);
}
