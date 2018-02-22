package com.codeflowcrafter.FitnessTracker.RepMax.Implementation.MVP;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IRequests;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.Domain.RepMax;

import java.util.List;

/**
 * Created by enric on 22/02/2018.
 */

public interface IRequests extends Crud_IRequests<RepMax> {
    List<RepMax> GetData(int profileId);
    List<Exercise> GetStrengtheningExercisesData();
}
