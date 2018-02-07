package com.codeflowcrafter.FitnessTracker.Services;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Shared.Gender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 06/02/2018.
 */

public class ViewService {
    public static void SetSpinnerValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static void SetAge(TextView txtDateOfBirth, Calendar dateOfBirth)
    {
        int age = CalculatorService.CalculateAge(dateOfBirth);

        SetAge(txtDateOfBirth, age);
    }

    public static void SetAge(TextView txtDateOfBirth, int age)
    {
        String display = String.format("(%s)", String.valueOf(age));

        txtDateOfBirth.setText(display);
    }

    public static void SetMHR(TextView txtMHR, int age)
    {
        int maximumHeartRate = CalculatorService.GetMaximumHeartRate(age);
        String display = String.format("%s bpm", String.valueOf(maximumHeartRate));

        txtMHR.setText(display);
    }

    public static void InitializeGenderSpinner(Activity activity, Spinner spinGender)
    {
        List<String> genderArray =  new ArrayList<String>();

        genderArray.add(Gender.MALE);
        genderArray.add(Gender.FEMALE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity,
                android.R.layout.simple_spinner_item,
                genderArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(adapter);
    }
}
