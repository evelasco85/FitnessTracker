package com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP;

import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IRequests;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;

import java.util.Calendar;
import java.util.List;

/**
 * Created by enric on 04/02/2018.
 */

public interface IRequests extends Crud_IRequests<Profile> {
    void Prompt_BMI(int profileId, int heightInches);
    void Prompt_RestingHeartRate(int profileId, int age);
    List<Profile> GetData();
    int GetAge(String dateOfBirth);
    int GetAge(Calendar dateOfBirthCalendar);
    int GetMhr(int age);
}
