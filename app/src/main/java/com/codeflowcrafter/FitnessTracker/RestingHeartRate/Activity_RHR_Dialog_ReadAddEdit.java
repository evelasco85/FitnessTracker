package com.codeflowcrafter.FitnessTracker.RestingHeartRate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.FitnessTracker.Services.ViewService;
import com.codeflowcrafter.FitnessTracker.Shared.HeartRate.Activity_Heart_Rate_Counter;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.UI.Date.Dialog_DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by enric on 11/02/2018.
 */

public class Activity_RHR_Dialog_ReadAddEdit  extends Base_Activity_Dialog_ReadAddEdit<RestingHeartRate, IRequests> {
    private final static int _fragmentId = R.layout.activity_rhr_fragment_read_add_edit;
    private final static int _saveCancelConcreteViewId = R.id.saveCancelFragmentPlaceholder;
    private final static int REQUEST_CODE_RESTING_HEART_RATE = 123;
    public static final String FRAGMENT_NAME = "Add/Edit RHR";
    public static final String KEY_PROFILE_ID = "RHR Id";
    public static final String KEY_AGE = "Age";

    private int _id = 0;
    private int _profileId = 0;
    private int _age = 0;

    public static Activity_RHR_Dialog_ReadAddEdit newInstance(
            String action,
            RestingHeartRate entity,
            int profileId,
            int age)
    {
        Activity_RHR_Dialog_ReadAddEdit dialog = new Activity_RHR_Dialog_ReadAddEdit();
        Bundle args = new Bundle();

        args.putString(Base_Activity_Dialog_ReadAddEdit.KEY_ACTION, action);
        args.putInt(KEY_PROFILE_ID, profileId);
        args.putInt(KEY_AGE, age);
        dialog.setArguments(args);
        dialog.SetEntityToEdit(entity);

        return dialog;
    }

    public static void Show(
            FragmentManager manager, IRequests request,
            String action, RestingHeartRate entity,
            int profileId, int age)
    {
        Activity_RHR_Dialog_ReadAddEdit dialog = Activity_RHR_Dialog_ReadAddEdit
                .newInstance(action, entity, profileId, age);

        dialog.SetViewRequest(request);
        dialog.show(manager, FRAGMENT_NAME);
    }

    public Activity_RHR_Dialog_ReadAddEdit()
    {
        super(_fragmentId, _saveCancelConcreteViewId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        _profileId = getArguments().getInt(KEY_PROFILE_ID);
        _age = getArguments().getInt(KEY_AGE);

        SetMHR(view);

        return view;
    }

    public void SetConcreteViews(final View view, final String selectedAction) {
        final TextView txtDate = ActivityService.GetConcreteView(TextView.class, view, R.id.txtDate);
        final TextView txtRhr = ActivityService.GetConcreteView(TextView.class, view, R.id.txtRhr);

        if(selectedAction == ACTION_READ)
        {
            //Disable input contols
            ViewService.DisableConcreteView(txtDate);
            ViewService.DisableConcreteView(txtRhr);

            return;
        }

        txtRhr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OpenHeartRateCounter();
                    }
                });
        ActivityService.GetConcreteView(Button.class, view, R.id.btnSave)
                .setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        InvokeActionBasedPersistency(view, selectedAction);
                        dismiss();
                    }
                });
        ActivityService.GetConcreteView(Button.class, view, R.id.btnCancel)
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

    public RestingHeartRate ViewDataToModel(View view){
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(RestingHeartRate.class);
        String date = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtDate)
                .getText()
                .toString();
        String rhr = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtRhr)
                .getText()
                .toString();
        int rhrValue = 0;
        if(!TextUtils.isEmpty(rhr)) rhrValue = Integer.parseInt(rhr);

        return new RestingHeartRate(
                mapper,
                _id,
                _profileId,
                date,
                _age,
                rhrValue
        );
    }

    public void SetModelToViewData(View view, RestingHeartRate entity){
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

        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtRhr)
                .setText(String.format("%s", String.valueOf(entity.GetRestingHeartRate())));
        SetMHR(view);
    }

    void SetMHR(View view)
    {
        int maximumHeartRate = GetViewRequest().GetMhr(_age);

        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtMhr)
                .setText(String.format("%s", String.valueOf(maximumHeartRate)));
    }

    private void OpenHeartRateCounter()
    {
        Intent intent = new Intent(getActivity(), Activity_Heart_Rate_Counter.class);

        startActivityForResult(intent, REQUEST_CODE_RESTING_HEART_RATE);
    }

    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            Intent resultingData)
    {
        if(resultCode != Activity.RESULT_OK) return;

        View view = getView();

        switch (requestCode) {
            case (REQUEST_CODE_RESTING_HEART_RATE):
                int heartRate = resultingData
                        .getIntExtra(
                                Activity_Heart_Rate_Counter.RESULT_HEART_RATE,
                                0);
                ActivityService
                        .GetConcreteView(TextView.class, view, R.id.txtRhr)
                        .setText(String.valueOf(heartRate));
                return;
            default:
                return;
        }
    }
}