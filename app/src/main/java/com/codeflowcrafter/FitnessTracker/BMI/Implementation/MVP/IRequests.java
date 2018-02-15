package com.codeflowcrafter.FitnessTracker.BMI.Implementation.MVP;

import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IRequests;

import java.util.List;

/**
 * Created by enric on 09/02/2018.
 */

public interface IRequests extends Crud_IRequests<BodyMassIndex> {
    double GetIdealWeightLbs(int heightInches);
    List<BodyMassIndex> GetData(int profileId);
    double GetBMI(double weightsLbs, int heightInches);
}
