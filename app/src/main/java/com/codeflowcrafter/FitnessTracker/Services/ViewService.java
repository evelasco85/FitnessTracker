package com.codeflowcrafter.FitnessTracker.Services;

import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

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
}
