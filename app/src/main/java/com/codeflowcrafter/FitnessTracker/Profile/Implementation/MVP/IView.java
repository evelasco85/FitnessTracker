package com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IView;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;

/**
 * Created by enric on 04/02/2018.
 */
public interface IView extends Crud_IView<Profile, IRequests> {
    void OnPromptExecution_BMI(int profileId, int heightInches);
    void OnPromptExecution_RestingHeartRate(int profileId, int age);
}
