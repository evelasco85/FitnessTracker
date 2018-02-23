package com.codeflowcrafter.FitnessTracker.BMI;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.FitnessTracker.BMI.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.FitnessTracker.Services.ViewService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.UI.Date.Dialog_DatePicker;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 09/02/2018.
 */

public class Activity_BMI_Dialog_ReadAddEdit extends Base_Activity_Dialog_ReadAddEdit<BodyMassIndex, IRequests> {
    private final static int _fragmentId = R.layout.activity_bmi_fragment_read_add_edit;
    private final static int _saveCancelConcreteViewId = R.id.saveCancelFragmentPlaceholder;
    public static final String FRAGMENT_NAME = "Add/Edit BMI";
    public static final String KEY_PROFILE_ID = "Profile Id";
    public static final String KEY_HEIGHT_INCHES = "Height in Inches";

    private int _id = 0;
    private int _profileId = 0;
    private int _heightInches = 0;

    public static Activity_BMI_Dialog_ReadAddEdit newInstance(
            String action,
            BodyMassIndex entity,
            int profileId,
            int heightInches)
    {
        Activity_BMI_Dialog_ReadAddEdit dialog = new Activity_BMI_Dialog_ReadAddEdit();
        Bundle args = new Bundle();

        args.putString(Base_Activity_Dialog_ReadAddEdit.KEY_ACTION, action);
        args.putInt(KEY_PROFILE_ID, profileId);
        args.putInt(KEY_HEIGHT_INCHES, heightInches);
        dialog.setArguments(args);
        dialog.SetEntityToEdit(entity);

        return dialog;
    }

    public static void Show(
            FragmentManager manager, IRequests request,
            String action, BodyMassIndex entity,
            int profileId, int heightInches)
    {
        Activity_BMI_Dialog_ReadAddEdit dialog = Activity_BMI_Dialog_ReadAddEdit
                .newInstance(action, entity, profileId, heightInches);

        dialog.SetViewRequest(request);
        dialog.show(manager, FRAGMENT_NAME);
    }

    public Activity_BMI_Dialog_ReadAddEdit()
    {
        super(_fragmentId, _saveCancelConcreteViewId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        _profileId = getArguments().getInt(KEY_PROFILE_ID);
        _heightInches = getArguments().getInt(KEY_HEIGHT_INCHES);

        ViewService.SetHeight(
                _heightInches,
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtFeet),
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtInches)
        );

        return view;
    }

    public void SetConcreteViews(final View view, final String selectedAction) {
        EditText txtWeight = ActivityService.GetConcreteView(EditText.class, view, R.id.txtWeight);
        EditText txtFeet = ActivityService.GetConcreteView(EditText.class, view, R.id.txtFeet);
        EditText txtInches = ActivityService.GetConcreteView(EditText.class, view, R.id.txtInches);
        final TextView txtDate = ActivityService.GetConcreteView(TextView.class, view, R.id.txtDate);

        if(selectedAction.equals(ACTION_READ))
        {
            //Disable input contols
            ViewService.DisableConcreteView(txtWeight);
            ViewService.DisableConcreteView(txtFeet);
            ViewService.DisableConcreteView(txtInches);
            ViewService.DisableConcreteView(txtDate);

            return;
        }

        SetBMIChangeListener(view, txtWeight);
        SetBMIChangeListener(view, txtFeet);
        SetBMIChangeListener(view, txtInches);

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

    public BodyMassIndex ViewDataToModel(View view){
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(BodyMassIndex.class);
        String date = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtDate)
                .getText()
                .toString();
        int heightInches = ViewService.GetHeightInches(
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtFeet),
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtInches)
        );
        double weightLbs = 0;
        String weight = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtWeight)
                .getText()
                .toString();
        if(!TextUtils.isEmpty(weight)) weightLbs = Double.parseDouble(weight);

        return new BodyMassIndex(
                mapper,
                _id,
                _profileId,
                date,
                heightInches,
                weightLbs
        );
    }

    public void SetModelToViewData(View view, BodyMassIndex entity){
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
                .GetConcreteView(EditText.class, view, R.id.txtWeight)
                .setText(String.valueOf(entity.GetWeightLbs()));
        ViewService.SetHeight(
                entity.GetHeightInches(),
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtFeet),
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtInches)
        );
        SetComputedViews(view);
    }

    private void SetBMIChangeListener(final View view, EditText textbox)
    {
        textbox.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SetComputedViews(view);
            }
        });
    }

    private void SetComputedViews(View view)
    {
        int heightInches = ViewService.GetHeightInches(
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtFeet),
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtInches)
        );
        double weightLbs = 0;
        String weight = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtWeight)
                .getText()
                .toString();
        if(!TextUtils.isEmpty(weight)) weightLbs = Double.parseDouble(weight);

        ViewService.SetBmiInfo(view, weightLbs, heightInches);
    }
}
