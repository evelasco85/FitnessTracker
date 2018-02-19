package com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.MVP;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IRequests;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain.ExerciseHeartRate;

import java.util.List;

/**
 * Created by enric on 19/02/2018.
 */

public interface IRequests extends Crud_IRequests<ExerciseHeartRate> {
    List<ExerciseHeartRate> GetData(int profileId);
}
