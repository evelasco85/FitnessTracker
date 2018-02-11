package com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.MVP;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IRequests;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;

/**
 * Created by enric on 11/02/2018.
 */

public interface IRequests extends Crud_IRequests<RestingHeartRate> {
    int GetMhr(int age);
}
