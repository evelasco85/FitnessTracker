package com.codeflowcrafter.FitnessTracker.ExerciseHeartRate;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain.ExerciseHeartRate;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.FitnessTracker.Services.ViewService;
import com.codeflowcrafter.FitnessTracker.Shared.HeartRate.Activity_Heart_Rate_Counter;
import com.codeflowcrafter.FitnessTracker.Shared.IntensityOfExercise;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

import java.util.List;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 19/02/2018.
 */

public class Activity_EHR_Dialog_ReadAddEdit extends Base_Activity_Dialog_ReadAddEdit<ExerciseHeartRate, IRequests> {
    private final static int _fragmentId = R.layout.activity_ehr_fragment_read_add_edit;
    private final static int _saveCancelConcreteViewId = R.id.saveCancelFragmentPlaceholder;
    private final static int REQUEST_CODE_RESTING_HEART_RATE = 123;
    private final static int REQUEST_CODE_EXERCISE_HEART_RATE = 456;
    private final static int REQUEST_CODE_RECOVERY_HEART_RATE = 789;
    public static final String FRAGMENT_NAME = "Add/Edit EHR";
    public static final String KEY_PROFILE_ID = "EHR Id";
    public static final String KEY_AGE = "Age";

    private int _id = 0;
    private int _profileId = 0;
    private int _age = 0;

    private List<Exercise> _exercises;
    public void SetExercises(List<Exercise> exercises) {
        this._exercises = exercises;
    }

    public static Activity_EHR_Dialog_ReadAddEdit newInstance(
            String action,
            ExerciseHeartRate entity,
            int profileId,
            int age,
            List<Exercise> exercises)
    {
        Activity_EHR_Dialog_ReadAddEdit dialog = new Activity_EHR_Dialog_ReadAddEdit();
        Bundle args = new Bundle();

        args.putString(Base_Activity_Dialog_ReadAddEdit.KEY_ACTION, action);
        args.putInt(KEY_PROFILE_ID, profileId);
        args.putInt(KEY_AGE, age);
        dialog.setArguments(args);
        dialog.SetEntityToEdit(entity);
        dialog.SetExercises(exercises);

        return dialog;
    }

    public static void Show(
            FragmentManager manager, IRequests request,
            String action, ExerciseHeartRate entity,
            int profileId, int age,
            List<Exercise> exercises)
    {
        Activity_EHR_Dialog_ReadAddEdit dialog = Activity_EHR_Dialog_ReadAddEdit
                .newInstance(action, entity, profileId, age, exercises);

        dialog.SetViewRequest(request);
        dialog.show(manager, FRAGMENT_NAME);
    }

    public Activity_EHR_Dialog_ReadAddEdit()
    {
        super(_fragmentId, _saveCancelConcreteViewId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _profileId = getArguments().getInt(KEY_PROFILE_ID);
        _age = getArguments().getInt(KEY_AGE);

        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    private void LoadDefaultData(View view)
    {
        IRequests request = GetViewRequest();
        int maximumHeartRate = request.GetMhr(_age);

        GetConcreteView(TextView.class, view, R.id.txtMhr)
                .setText(String.format("%s", String.valueOf(maximumHeartRate)));

        RestingHeartRate latestRHR = request.GetLatestRestingHeartRate(_profileId);

        if(latestRHR != null)
        {
            GetConcreteView(EditText.class, view, R.id.txtRhr)
                    .setText(String.format("%s", String.valueOf(latestRHR.GetRestingHeartRate())));

        }
    }

    private void SetExerciseSpinner(final Spinner spinExercise, final View fView)
    {
        ViewService.InitializeExercisesSpinner(
                this.getActivity(),
                spinExercise,
                _exercises);

        spinExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinExercise.setSelection(position);
                SetZone(fView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void SetConcreteViews(final View fView, final String selectedAction) {
        final Spinner spinExercise = ActivityService.GetConcreteView(Spinner.class, fView, R.id.spinExercise);
        final TextView txtDate = ActivityService.GetConcreteView(TextView.class, fView, R.id.txtDate);

        LoadDefaultData(fView);
        SetExerciseSpinner(spinExercise, fView);

        if(selectedAction.equals(ACTION_READ))
        {
            //Disable input contols
            ViewService.DisableConcreteView(txtDate);
            spinExercise.setEnabled(false);
            return;
        }

        ActivityService.GetConcreteView(Button.class, fView, R.id.btnSave)
                .setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        InvokeActionBasedPersistency(fView, selectedAction);
                        dismiss();
                    }
                });
        ActivityService.GetConcreteView(Button.class, fView, R.id.btnCancel)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetViewRequest()
                                .CancelEntry();
                        dismiss();
                    }
                });
    }

    private void InvokeActionBasedPersistency(View view, String selectedAction)
    {
        switch (selectedAction)
        {
            case ACTION_ADD:
                GetViewRequest().Add(ViewDataToModel(view));
                break;
            case ACTION_EDIT:
                GetViewRequest().Update(ViewDataToModel(view));
                break;
            default:
                break;
        }
    }

    public ExerciseHeartRate ViewDataToModel(View view){
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(ExerciseHeartRate.class);

        return null;
    }

    public void SetModelToViewData(View view, ExerciseHeartRate entity){
        if(entity == null){
            return;
        }

        _id = entity.GetId();
    }


    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            Intent resultingData)
    {
        if(resultCode != Activity.RESULT_OK) return;

        View view = getView();
        int heartRate = resultingData
                .getIntExtra(
                        Activity_Heart_Rate_Counter.RESULT_HEART_RATE,
                        0);

        switch (requestCode) {
            case (REQUEST_CODE_RESTING_HEART_RATE):
                ActivityService
                        .GetConcreteView(TextView.class, view, R.id.txtRhr)
                        .setText(String.valueOf(heartRate));
                return;
            case (REQUEST_CODE_EXERCISE_HEART_RATE):
                return;
            case (REQUEST_CODE_RECOVERY_HEART_RATE):
                return;
            default:
                return;
        }
    }

    private void SetZone(View view)
    {
        final Spinner spinExercise = ActivityService
                .GetConcreteView(Spinner.class, view, R.id.spinExercise);

        String exercise = (String)spinExercise.getSelectedItem();
        IntensityOfExercise intensity = GetViewRequest()
                .GetIntensityOfExercise(_exercises, exercise);

        if(intensity == null) return;

        int maximumHeartRate = 0;
        String maximumHeartRateStr = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtMhr)
                .getText()
                .toString();

        if(!TextUtils.isEmpty(maximumHeartRateStr))
            maximumHeartRate = Integer.parseInt(maximumHeartRateStr);

        int restingHeartRate = 0;
        String restingHeartRateStr = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtRhr)
                .getText()
                .toString();

        if(!TextUtils.isEmpty(restingHeartRateStr))
            restingHeartRate = Integer.parseInt(restingHeartRateStr);

        int lowerLimit = CalculatorService.CalculateZonedHeartRate(
                maximumHeartRate,
                restingHeartRate,
                intensity.GetLowerPercentLimit()
        );
        int upperLimit = CalculatorService.CalculateZonedHeartRate(
                maximumHeartRate,
                restingHeartRate,
                intensity.GetUpperPercentLimit()
        );

        String description = String.format("%d - %d", lowerLimit, upperLimit);

        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtZone)
                .setText(description);
    }
}