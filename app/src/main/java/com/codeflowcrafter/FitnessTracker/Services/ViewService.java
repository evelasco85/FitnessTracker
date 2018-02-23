package com.codeflowcrafter.FitnessTracker.Services;

import android.app.Activity;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Shared.BMICategoryService;
import com.codeflowcrafter.FitnessTracker.Shared.ExerciseType;
import com.codeflowcrafter.FitnessTracker.Shared.Gender;
import com.codeflowcrafter.FitnessTracker.Shared.IntensityOfExerciseService;
import com.codeflowcrafter.FitnessTracker.Shared.LevelOfActivityService;
import com.codeflowcrafter.FitnessTracker.Shared.RepMaxService;

import java.text.DecimalFormat;
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

    public static void InitializeLevelOfActivitiesSpinner(Activity activity, Spinner spinLevelOfActivity)
    {
        List<String> levelActivityArray = LevelOfActivityService
                .GetInstance()
                .GetLevelDescriptions();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity,
                android.R.layout.simple_spinner_item,
                levelActivityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLevelOfActivity.setAdapter(adapter);
    }

    public static void InitializeExercisesSpinner(
            Activity activity,
            Spinner spinExercises,
            List<Exercise> exercises)
    {
        List<String> exerciseArray  =  new ArrayList<String>();

        if(exercises != null) {
            for (int index = 0; index < exercises.size(); index++) {
                Exercise exercise = exercises.get(index);

                if (exercise == null) continue;

                exerciseArray.add(exercise.GetName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity,
                android.R.layout.simple_spinner_item,
                exerciseArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinExercises.setAdapter(adapter);
    }

    public static void SetHeight(int totalInches, EditText txtFeet, EditText txtInches)
    {
        int feet = totalInches / 12;
        int inches = totalInches - (feet * 12);

        txtFeet.setText(String.valueOf(feet));
        txtInches.setText(String.valueOf(inches));
    }

    public static int GetHeightInches(EditText txtFeet, EditText txtInches)
    {
        String ft = txtFeet.getText().toString();
        String in = txtInches.getText().toString();

        int feet = 0;
        int inches = 0;

        if(!TextUtils.isEmpty(ft)) feet = Integer.parseInt(ft);
        if(!TextUtils.isEmpty(in)) inches = Integer.parseInt(in);

        int totalInches = (feet * 12) + inches;

        return totalInches;
    }

    public static void InitializeIntensitySpinner(Activity activity, Spinner spinIntensity)
    {
        List<String> intensityDescriptionArray = IntensityOfExerciseService
                .GetInstance()
                .GetIntensityDescriptions();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity,
                android.R.layout.simple_spinner_item,
                intensityDescriptionArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinIntensity.setAdapter(adapter);
    }

    public static void InitializeExerciseTypeSpinner(Activity activity, Spinner spinType)
    {
        List<String> exerciseTypeArray =  new ArrayList<String>();

        exerciseTypeArray.add(ExerciseType.TYPE_CARDIO);
        exerciseTypeArray.add(ExerciseType.TYPE_STRENGTHENING);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity,
                android.R.layout.simple_spinner_item,
                exerciseTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinType.setAdapter(adapter);
    }

    public static void DisableConcreteView(EditText editText)
    {
        editText.setEnabled(false);
        editText.setInputType(InputType.TYPE_NULL);
    }

    public static void DisableConcreteView(TextView editText)
    {
        editText.setEnabled(false);
        editText.setInputType(InputType.TYPE_NULL);
    }

    public static void SetDefaultSpinnerItemSelectedListener(final Spinner spinner)
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public static void SetClassification(TextView txtClassification, double bmiScore)
    {
        String classification = BMICategoryService
                .GetInstance()
                .GetBMICategory(bmiScore);

        txtClassification.setText(classification);
    }

    public static void Set_WorkoutSet(TextView txtSet, String week, String set, double oneRM, String currentWeek)
    {
        if((!TextUtils.isEmpty(week)) && (week.equals(currentWeek)))
            txtSet.setTextColor(Color.RED);
        else
            txtSet.setTextColor(Color.BLUE);

        txtSet.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
        txtSet
                .setText(
                        RepMaxService
                                .GetInstance()
                                .WorkoutSetDescription(week, set, oneRM)
                );
    }

    public static void SetBmiInfo(View view, double weightLbs, int heightInches)
    {
        double bmiScore = CalculatorService.GetBMI(weightLbs, heightInches);

        GetConcreteView(TextView.class, view, R.id.txtBmi)
                .setText(String.format("%.2f", bmiScore));
        ViewService.SetClassification(
                GetConcreteView(TextView.class, view, R.id.txtClassification),
                bmiScore
        );

        double idealWeight =  BMICategoryService
                .GetInstance()
                .IdealNormalWeightLbs(heightInches);
        double idealWeightToLose = weightLbs - idealWeight;

        GetConcreteView(TextView.class, view, R.id.txtIdealWeight)
                .setText(String.format("%.2f", idealWeight));
        GetConcreteView(TextView.class, view, R.id.txtIdealWeightToLose)
                .setText(String.format("%.2f", idealWeightToLose));

        DecimalFormat formatter = new DecimalFormat("#,###,###.##");
        double caloriesToLose = CalculatorService.GetCaloriesToBurn(idealWeightToLose);

        GetConcreteView(TextView.class, view, R.id.txtCaloriesToBurn)
                .setText(
                        formatter.format(caloriesToLose)
                );
    }
}
