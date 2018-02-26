package com.codeflowcrafter.FitnessTracker.ExerciseHeartRate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.codeflowcrafter.UI.Date.Dialog_DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
//    private Toast countdownToast = null;

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
        SetMHR(view);

        RestingHeartRate latestRHR = GetViewRequest().GetLatestRestingHeartRate(_profileId);

        if(latestRHR != null)
        {
            GetConcreteView(EditText.class, view, R.id.txtRhr)
                    .setText(String.format("%s", String.valueOf(latestRHR.GetRestingHeartRate())));
        }
    }

    private void SetMHR(View view)
    {
        int maximumHeartRate = GetViewRequest().GetMhr(_age);

        GetConcreteView(TextView.class, view, R.id.txtMhr)
                .setText(String.format("%s", String.valueOf(maximumHeartRate)));
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
        final EditText txtRhr = ActivityService.GetConcreteView(EditText.class, fView, R.id.txtRhr);
        final EditText txtEhr = ActivityService.GetConcreteView(EditText.class, fView, R.id.txtEhr);
        final EditText txtRecoverHr = ActivityService.GetConcreteView(EditText.class, fView, R.id.txtRecoverHr);

        LoadDefaultData(fView);
        SetExerciseSpinner(spinExercise, fView);

        if(selectedAction.equals(ACTION_READ))
        {
            //Disable input contols
            ViewService.DisableConcreteView(txtDate);
            ViewService.DisableConcreteView(txtRhr);
            ViewService.DisableConcreteView(txtEhr);
            ViewService.DisableConcreteView(txtRecoverHr);
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
        txtDate.setText(
                (new SimpleDateFormat(CalculatorService.DateFormat))
                        .format(
                                Calendar
                                        .getInstance()
                                        .getTime()
                        )
        );
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_DatePicker dialog = new Dialog_DatePicker();

                dialog.SetDefaultDate(txtDate.getText().toString());
                dialog.SetOnDateSetListener(new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        Calendar calendar = Calendar.getInstance();

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);

                        String dateOfBirth = (new SimpleDateFormat(CalculatorService.DateFormat))
                                .format(calendar.getTime());
                        txtDate.setText(dateOfBirth);
                    }
                });
                dialog.show(getFragmentManager(), "datePicker");
            }
        });
        txtRhr.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SetZone(fView);
            }
        });
        txtRhr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenHeartRateCounter(REQUEST_CODE_RESTING_HEART_RATE);
            }
        });
        txtEhr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenHeartRateCounter(REQUEST_CODE_EXERCISE_HEART_RATE);
            }
        });
        txtRecoverHr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenHeartRateCounter(REQUEST_CODE_RECOVERY_HEART_RATE);
            }
        });

        Button btnRest = ActivityService
                .GetConcreteView(Button.class, fView, R.id.btnRest);

        btnRest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder countdownDialog =  new AlertDialog
                                .Builder(getActivity());
                        final TextView text = new TextView(getActivity());
                        final int millisPerSecond = 1000;
                        final int minutes = 2;
                        final int secondsPerMinutes = 60;
                        final long totalSeconds = minutes * secondsPerMinutes * millisPerSecond;
                        final CountDownTimer timer = new CountDownTimer(totalSeconds, millisPerSecond) {
                            @Override
                            public void onTick(long millisUntilFinished) {

//                                text.setText(String.format("%d", millisUntilFinished/millisPerSecond));
                                ViewService.SetTime(text, millisUntilFinished/millisPerSecond);
                            }

                            @Override
                            public void onFinish() {
                                text.setText("REST COMPLETE!");
                            }
                        };

                        text.setTextColor(Color.RED);
                        text.setTextSize(17);
                        countdownDialog.setTitle("Recovery Period");
                        text.setGravity(Gravity.CENTER);
                        countdownDialog.setMessage("Recovery Countdown(seconds)");
                        countdownDialog.setView(text);
                        countdownDialog.setCancelable(false);
                        countdownDialog.setPositiveButton(
                                "Terminate Countdown",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg) {
                                        timer.cancel();
                                        dialog.dismiss();
                                    }
                                }
                        );

                        timer.start();
                        countdownDialog.show();
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
        String date = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtDate)
                .getText()
                .toString();

        int rhr = 0;
        String rhrValue = ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtRhr)
                .getText().toString();
        if(!TextUtils.isEmpty(rhrValue)) rhr = Integer.parseInt(rhrValue);

        int exerciseHeartRate = 0;
        String exerciseHeartRateValue = ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtEhr)
                .getText().toString();
        if(!TextUtils.isEmpty(exerciseHeartRateValue)) exerciseHeartRate = Integer.parseInt(exerciseHeartRateValue);

        int recoveryHeartRate = 0;
        String recoveryHeartRateValue = ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtRecoverHr)
                .getText().toString();
        if(!TextUtils.isEmpty(recoveryHeartRateValue)) recoveryHeartRate = Integer.parseInt(recoveryHeartRateValue);

        String exercise = (String)ActivityService
                .GetConcreteView(Spinner.class, view, R.id.spinExercise)
                .getSelectedItem();

        return new ExerciseHeartRate(
                mapper,
                _id,
                _profileId,
                date,
                _age,
                rhr,
                exerciseHeartRate,
                recoveryHeartRate,
                exercise
        );
    }

    public void SetModelToViewData(View view, ExerciseHeartRate entity){
        if(entity == null){
            return;
        }

        _id = entity.GetId();
        String date = entity.GetDate();

        if(!TextUtils.isEmpty(date)) {
            ActivityService
                    .GetConcreteView(TextView.class, view, R.id.txtDate)
                    .setText(date);
        }

        _age = entity.GetAge();

        SetMHR(view);
        ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtRhr)
                .setText(String.valueOf(entity.GetRestingHeartRate()));

        String exercise = entity.GetExercise();

        if(!TextUtils.isEmpty(exercise)) {
            final Spinner spinExercise = ActivityService
                    .GetConcreteView(Spinner.class, view, R.id.spinExercise);

            ViewService.SetSpinnerValue(spinExercise, exercise);
        }

        ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtEhr)
                .setText(String.valueOf(entity.GetExerciseHeartRate()));
        ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtRecoverHr)
                .setText(String.valueOf(entity.GetRecoveryHeartRate()));
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
        EditText textbox = null;

        switch (requestCode) {
            case (REQUEST_CODE_RESTING_HEART_RATE):
                textbox = ActivityService
                        .GetConcreteView(EditText.class, view, R.id.txtRhr);
                break;
            case (REQUEST_CODE_EXERCISE_HEART_RATE):
                textbox = ActivityService
                        .GetConcreteView(EditText.class, view, R.id.txtEhr);
                break;
            case (REQUEST_CODE_RECOVERY_HEART_RATE):
                textbox = ActivityService
                        .GetConcreteView(EditText.class, view, R.id.txtRecoverHr);
                break;
            default:
                return;
        }

        if(textbox != null)
            textbox.setText(String.valueOf(heartRate));
    }

    private void SetZone(View view)
    {
        final Spinner spinExercise = ActivityService
                .GetConcreteView(Spinner.class, view, R.id.spinExercise);

        String exercise = (String)spinExercise.getSelectedItem();
        IntensityOfExercise intensity = GetViewRequest()
                .GetIntensityOfExercise(_exercises, exercise);

        if(intensity == null) return;

        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtIntensity)
                .setText(intensity.GetDescription());

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

        String zoneRange = GetViewRequest().GetZoneRange(
                maximumHeartRate,
                restingHeartRate,
                intensity);

        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtZone)
                .setText(zoneRange);
    }

    private void OpenHeartRateCounter(int requestCode)
    {
        Intent intent = new Intent(getActivity(), Activity_Heart_Rate_Counter.class);

        startActivityForResult(intent, requestCode);
    }
}