package com.codeflowcrafter.FitnessTracker.Profile;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.FitnessTracker.Services.ViewService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.UI.Date.Dialog_DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;
import static com.codeflowcrafter.FitnessTracker.Services.ViewService.SetSpinnerValue;

/**
 * Created by enric on 05/02/2018.
 */

public class Activity_Profile_Dialog_ReadAddEdit extends Base_Activity_Dialog_ReadAddEdit<Profile, IRequests> {
    private final static int _fragmentId = R.layout.activity_profile_fragment_read_add_edit;
    private final static int _saveCancelConcreteViewId = R.id.saveCancelFragmentPlaceholder;
    public static final String FRAGMENT_NAME = "Add/Edit Profile";

    private int _id = 0;

    public static Activity_Profile_Dialog_ReadAddEdit newInstance(
            String action,
            Profile entity)
    {
        Activity_Profile_Dialog_ReadAddEdit dialog = new Activity_Profile_Dialog_ReadAddEdit();
        Bundle args = new Bundle();

        args.putString(Base_Activity_Dialog_ReadAddEdit.KEY_ACTION, action);
        dialog.setArguments(args);
        dialog.SetEntityToEdit(entity);

        return dialog;
    }

    public static void Show(
            FragmentManager manager, IRequests request,
            String action, Profile entity)
    {
        Activity_Profile_Dialog_ReadAddEdit dialog = Activity_Profile_Dialog_ReadAddEdit
                .newInstance(action, entity);

        dialog.SetViewRequest(request);
        dialog.show(manager, FRAGMENT_NAME);
    }

    public Activity_Profile_Dialog_ReadAddEdit()
    {
        super(_fragmentId, _saveCancelConcreteViewId);
    }

    public void SetConcreteViews(final View view, final String selectedAction){
        Spinner spinGender = GetConcreteView(Spinner.class, view, R.id.spinGender);

        ViewService.InitializeGenderSpinner(this.getActivity(), spinGender);

        if(selectedAction == ACTION_READ)
        {
            //Disable input contols
            EditText txtName = GetConcreteView(EditText.class, view, R.id.txtName);

            txtName.setEnabled(false);
            txtName.setInputType(InputType.TYPE_NULL);

            EditText txtFeet = GetConcreteView(EditText.class, view, R.id.txtFeet);

            txtFeet.setEnabled(false);
            txtFeet.setInputType(InputType.TYPE_NULL);

            EditText txtInches = GetConcreteView(EditText.class, view, R.id.txtInches);

            txtInches.setEnabled(false);
            txtInches.setInputType(InputType.TYPE_NULL);

            spinGender.setEnabled(false);

            return;
        }

        GetConcreteView(Button.class, view, R.id.btnSave)
                .setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        InvokeActionBasedPersistency(view, selectedAction);
                        dismiss();
                    }
                });

        GetConcreteView(Button.class, view, R.id.btnCancel)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetViewRequest()
                                .CancelEntry();
                        dismiss();
            }
        });

        final TextView txtDateOfBirth = GetConcreteView(TextView.class, view, R.id.txtDateOfBirth);
        final TextView txtAge = GetConcreteView(TextView.class, view, R.id.txtAge);
        final TextView txtMhr = GetConcreteView(TextView.class, view, R.id.txtMhr);

        txtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_DatePicker dialog = new Dialog_DatePicker();

                dialog.SetDefaultDate(txtDateOfBirth.getText().toString());
                dialog.SetOnDateSetListener(new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        Calendar dateOfBirthCalendar = Calendar.getInstance();

                        dateOfBirthCalendar.set(Calendar.YEAR, year);
                        dateOfBirthCalendar.set(Calendar.MONTH, month);
                        dateOfBirthCalendar.set(Calendar.DAY_OF_MONTH, day);

                        txtDateOfBirth
                                .setText(
                                        (new SimpleDateFormat(CalculatorService.DateFormat))
                                                .format(dateOfBirthCalendar.getTime())
                                );

                        int age = CalculatorService.CalculateAge(dateOfBirthCalendar);

                        ViewService.SetAge(txtAge, age);
                        ViewService.SetMHR(txtMhr, age);
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

    public Profile ViewDataToModel(View view){
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(Profile.class);
        final Spinner genderSpinner = GetConcreteView(Spinner.class, view, R.id.spinGender);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        String gender = (String)genderSpinner.getSelectedItem();
        int heightInches = ViewService.GetHeightInches(
                GetConcreteView(EditText.class, view, R.id.txtFeet),
                GetConcreteView(EditText.class, view, R.id.txtInches)
        );

        return new Profile(mapper,
                _id,
                GetConcreteView(EditText.class, view, R.id.txtName).getText().toString(),
                gender,
                GetConcreteView(TextView.class, view, R.id.txtDateOfBirth).getText().toString(),
                "",
                heightInches
                );
    }

    public void SetModelToViewData(View view, Profile entity){
        if(entity == null) return;

        _id = entity.GetId();

        GetConcreteView(EditText.class, view, R.id.txtName).setText(entity.GetName());

        String dateOfBirth = entity.GetDateOfBirth();

        if(!TextUtils.isEmpty(dateOfBirth)) {
            TextView txtDateOfBirth = GetConcreteView(TextView.class, view, R.id.txtDateOfBirth);
            TextView txtAge = GetConcreteView(TextView.class, view, R.id.txtAge);
            TextView txtMhr = GetConcreteView(TextView.class, view, R.id.txtMhr);
            Calendar dobCalendar = CalculatorService.ConvertToCalendar(dateOfBirth);
            int age = CalculatorService.CalculateAge(dobCalendar);

            txtDateOfBirth.setText(dateOfBirth);
            ViewService.SetAge(txtAge, age);
            ViewService.SetMHR(txtMhr, age);
        }

        String gender = entity.GetGender();

        if(!TextUtils.isEmpty(gender)) {
            final Spinner genderSpinner = GetConcreteView(Spinner.class, view, R.id.spinGender);

            SetSpinnerValue(genderSpinner, gender);
        }

        ViewService.InitializeHeight(
                entity.GetHeightInches(),
                GetConcreteView(EditText.class, view, R.id.txtFeet),
                GetConcreteView(EditText.class, view, R.id.txtInches)
        );
    }
}
