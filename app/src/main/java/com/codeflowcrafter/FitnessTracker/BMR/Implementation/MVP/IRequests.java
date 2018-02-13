package com.codeflowcrafter.FitnessTracker.BMR.Implementation.MVP;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IRequests;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.Domain.BasalMetabolicRate;

import java.util.List;

/**
 * Created by enric on 13/02/2018.
 */

public interface IRequests extends Crud_IRequests<BasalMetabolicRate> {
    List<BasalMetabolicRate> GetData(int profileId);
}
