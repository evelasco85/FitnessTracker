package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.MVP;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IRequests;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;

import java.util.List;

/**
 * Created by enric on 08/02/2018.
 */

public interface IRequests extends Crud_IRequests<Exercise> {
    List<Exercise> GetData();
}
