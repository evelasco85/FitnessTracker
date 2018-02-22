package com.codeflowcrafter.FitnessTracker.RepMax;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.Domain.RepMax;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.FitnessTracker.Services.ViewService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.UI.Date.Dialog_DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by enric on 22/02/2018.
 */

public class Activity_RepMax_Dialog_ReadAddEdit extends Base_Activity_Dialog_ReadAddEdit<RepMax, IRequests> {
    private final static int _fragmentId = R.layout.activity_repmax_fragment_read_add_edit;
    private final static int _saveCancelConcreteViewId = R.id.saveCancelFragmentPlaceholder;
    public static final String FRAGMENT_NAME = "Add/Edit RepMax";
    public static final String KEY_PROFILE_ID = "Profile Id";

    private int _id = 0;
    private int _profileId = 0;

    private List<Exercise> _exercises;
    public void SetExercises(List<Exercise> exercises) {
        this._exercises = exercises;
    }

    public static Activity_RepMax_Dialog_ReadAddEdit newInstance(
            String action,
            RepMax entity,
            int profileId,
            List<Exercise> exercises)
    {
        Activity_RepMax_Dialog_ReadAddEdit dialog = new Activity_RepMax_Dialog_ReadAddEdit();
        Bundle args = new Bundle();

        args.putString(Base_Activity_Dialog_ReadAddEdit.KEY_ACTION, action);
        args.putInt(KEY_PROFILE_ID, profileId);
        dialog.setArguments(args);
        dialog.SetEntityToEdit(entity);
        dialog.SetExercises(exercises);

        return dialog;
    }

    public static void Show(
            FragmentManager manager, IRequests request,
            String action, RepMax entity,
            int profileId,
            List<Exercise> exercises)
    {
        Activity_RepMax_Dialog_ReadAddEdit dialog = Activity_RepMax_Dialog_ReadAddEdit
                .newInstance(action, entity, profileId, exercises);

        dialog.SetViewRequest(request);
        dialog.show(manager, FRAGMENT_NAME);
    }

    public Activity_RepMax_Dialog_ReadAddEdit()
    {
        super(_fragmentId, _saveCancelConcreteViewId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _profileId = getArguments().getInt(KEY_PROFILE_ID);

        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void SetConcreteViews(final View fView, final String selectedAction) {
        final Spinner spinExercise = ActivityService.GetConcreteView(Spinner.class, fView, R.id.spinExercise);
        final TextView txtStartDate = ActivityService.GetConcreteView(TextView.class, fView, R.id.txtStartDate);

        SetExerciseSpinner(spinExercise, fView);

        if(selectedAction.equals(ACTION_READ))
        {
            //Disable input contols
            ViewService.DisableConcreteView(txtStartDate);
            ViewService.DisableConcreteView(ActivityService
                    .GetConcreteView(EditText.class, fView, R.id.txtWeightLbs));
            ViewService.DisableConcreteView(ActivityService
                    .GetConcreteView(EditText.class, fView, R.id.txtRepititions));
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

        txtStartDate.setText(
                (new SimpleDateFormat(CalculatorService.DateFormat))
                        .format(
                                Calendar
                                        .getInstance()
                                        .getTime()
                        )
        );
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_DatePicker dialog = new Dialog_DatePicker();

                dialog.SetDefaultDate(txtStartDate.getText().toString());
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
                        txtStartDate.setText(dateOfBirth);
                    }
                });
                dialog.show(getFragmentManager(), "datePicker");
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

    public RepMax ViewDataToModel(View view){
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(RepMax.class);
        String startDate = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtStartDate)
                .getText()
                .toString();

        double weightLbs = 0;
        String weight = ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtWeightLbs)
                .getText()
                .toString();

        if(!TextUtils.isEmpty(weight)) weightLbs = Double.parseDouble(weight);

        int repititions = 0;
        String repititionsValue = ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtRepititions)
                .getText().toString();
        if(!TextUtils.isEmpty(repititionsValue)) repititions = Integer.parseInt(repititionsValue);

        String exercise =  (String)ActivityService
                .GetConcreteView(Spinner.class, view, R.id.spinExercise)
                .getSelectedItem();

        return new RepMax(
                mapper,
                _id,
                _profileId,
                startDate,
                exercise,
                weightLbs,
                repititions
        );
    }

    public void SetModelToViewData(View view, RepMax entity){
        if(entity == null){
            return;
        }

        _id = entity.GetId();
        String startDate = entity.GetStartDate();

        if(!TextUtils.isEmpty(startDate)) {
            ActivityService
                    .GetConcreteView(TextView.class, view, R.id.txtStartDate)
                    .setText(startDate);
        }

        ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtWeightLbs)
                .setText(String.valueOf(entity.GetWeightLbs()));

        ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtRepititions)
                .setText(String.valueOf(entity.GetRepititions()));

        String exercise = entity.GetExercise();

        if(!TextUtils.isEmpty(exercise)) {
            final Spinner spinExercise = ActivityService
                    .GetConcreteView(Spinner.class, view, R.id.spinExercise);

            ViewService.SetSpinnerValue(spinExercise, exercise);
        }
    }
}
