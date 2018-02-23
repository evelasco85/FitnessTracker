package com.codeflowcrafter.FitnessTracker.BMR;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.QueryObjects.QueryByProfileId;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.Domain.BasalMetabolicRate;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.FitnessTracker.Services.ViewService;
import com.codeflowcrafter.FitnessTracker.Shared.BMICategoryService;
import com.codeflowcrafter.FitnessTracker.Shared.LevelOfActivity;
import com.codeflowcrafter.FitnessTracker.Shared.LevelOfActivityService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;
import com.codeflowcrafter.UI.Date.Dialog_DatePicker;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 13/02/2018.
 */

public class Activity_BMR_Dialog_ReadAddEdit extends Base_Activity_Dialog_ReadAddEdit<BasalMetabolicRate, IRequests> {
    private final static int _fragmentId = R.layout.activity_bmr_fragment_read_add_edit;
    private final static int _saveCancelConcreteViewId = R.id.saveCancelFragmentPlaceholder;
    public static final String FRAGMENT_NAME = "Add/Edit BMR";
    public static final String KEY_PROFILE_ID = "Profile Id";
    public static final String KEY_AGE = "Age";
    public static final String KEY_GENDER = "Gender";

    private int _id = 0;
    private int _profileId = 0;
    private int _age = 0;
    private String _gender;

    public static Activity_BMR_Dialog_ReadAddEdit newInstance(
            String action,
            BasalMetabolicRate entity,
            int profileId,
            int age,
            String gender)
    {
        Activity_BMR_Dialog_ReadAddEdit dialog = new Activity_BMR_Dialog_ReadAddEdit();
        Bundle args = new Bundle();

        args.putString(Base_Activity_Dialog_ReadAddEdit.KEY_ACTION, action);
        args.putInt(KEY_PROFILE_ID, profileId);
        args.putInt(KEY_AGE, age);
        args.putString(KEY_GENDER, gender);
        dialog.setArguments(args);
        dialog.SetEntityToEdit(entity);

        return dialog;
    }

    public static void Show(
            FragmentManager manager, IRequests request,
            String action, BasalMetabolicRate entity,
            int profileId,
            int age,
            String gender)
    {
        Activity_BMR_Dialog_ReadAddEdit dialog = Activity_BMR_Dialog_ReadAddEdit
                .newInstance(action, entity, profileId, age, gender);

        dialog.SetViewRequest(request);
        dialog.show(manager, FRAGMENT_NAME);
    }

    public Activity_BMR_Dialog_ReadAddEdit()
    {
        super(_fragmentId, _saveCancelConcreteViewId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _profileId = getArguments().getInt(KEY_PROFILE_ID);
        _age = getArguments().getInt(KEY_AGE);
        _gender = getArguments().getString(KEY_GENDER);

        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        final ViewGroup content = (ViewGroup) dialog.findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                View inner = content.getChildAt(0);
                content.removeViewAt(0);
                ScrollView scrollView = new ScrollView(getActivity().getApplicationContext());
                scrollView.addView(inner);
                content.addView(scrollView);
            }
        });

        return dialog;
    }

    private void LoadDefaultData(View view)
    {
         /*QUERY BMI (WEIGHT AND HEIGHT) HERE*/
        IDataSynchronizationManager manager= DataSynchronizationManager.GetInstance();
        IRepository<BodyMassIndex> repository = manager.GetRepository(BodyMassIndex.class);
        QueryByProfileId.Criteria criteria = new QueryByProfileId.Criteria(_profileId);
        List<BodyMassIndex> bmiList =  repository.Matching(criteria);
        int latestHeightInches = 0;
        double latestWeightLbs = 0;

        if(bmiList.size() > 0)
        {
            latestHeightInches = bmiList.get(0).GetHeightInches();
            latestWeightLbs = bmiList.get(0).GetWeightLbs();
        }

        //Then load to views
        ViewService.SetHeight(
                latestHeightInches,
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtFeet),
                ActivityService.GetConcreteView(EditText.class, view, R.id.txtInches)
        );
        ActivityService.GetConcreteView(EditText.class, view, R.id.txtWeight)
                .setText(String.valueOf(latestWeightLbs));
        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtAge)
                .setText(String.valueOf(_age));
        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtGender)
                .setText(_gender);
        /************************************/
        SetComputedViews(view);
    }

    public void SetConcreteViews(final View fView, final String selectedAction) {
        EditText txtWeight = ActivityService.GetConcreteView(EditText.class, fView, R.id.txtWeight);
        EditText txtFeet = ActivityService.GetConcreteView(EditText.class, fView, R.id.txtFeet);
        EditText txtInches = ActivityService.GetConcreteView(EditText.class, fView, R.id.txtInches);
        final TextView txtDate = ActivityService.GetConcreteView(TextView.class, fView, R.id.txtDate);
        final Spinner spinLevelOfActivity = ActivityService.GetConcreteView(Spinner.class, fView, R.id.spinLevelOfActivity);

        ViewService.InitializeLevelOfActivitiesSpinner(this.getActivity(), spinLevelOfActivity);
        LoadDefaultData(fView);

        if(selectedAction.equals(ACTION_READ))
        {
            //Disable input contols
            ViewService.DisableConcreteView(txtWeight);
            ViewService.DisableConcreteView(txtFeet);
            ViewService.DisableConcreteView(txtInches);
            ViewService.DisableConcreteView(txtDate);
            spinLevelOfActivity.setEnabled(false);

            return;
        }

        SetBMIChangeListener(fView, txtWeight);
        SetBMIChangeListener(fView, txtFeet);
        SetBMIChangeListener(fView, txtInches);

        spinLevelOfActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinLevelOfActivity.setSelection(position);
                SetComputedViews(fView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
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

    public BasalMetabolicRate ViewDataToModel(View view){
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(BasalMetabolicRate.class);
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

        String levelOfActivity = (String)ActivityService
                .GetConcreteView(Spinner.class, view, R.id.spinLevelOfActivity)
                .getSelectedItem();
        int age = GetAge(view);

        return new BasalMetabolicRate(mapper,
                _id,
                _profileId,
                date,
                age,
                heightInches,
                weightLbs,
                levelOfActivity);
    }

    public void SetModelToViewData(View view, BasalMetabolicRate entity){
        if(entity == null){
            return;
        }

        _id = entity.GetId();
        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtGender)
                .setText(_gender);

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
        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtAge)
                .setText(String.valueOf(entity.GetAge()));

        String levelOfActivity = entity.GetLevelOfActivity();

        if(!TextUtils.isEmpty(levelOfActivity)) {
            final Spinner levelOfActivitySpinner = ActivityService
                    .GetConcreteView(Spinner.class, view, R.id.spinLevelOfActivity);

            ViewService.SetSpinnerValue(levelOfActivitySpinner, levelOfActivity);
        }

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

        String levelOfActivity = (String)ActivityService
                .GetConcreteView(Spinner.class, view, R.id.spinLevelOfActivity)
                .getSelectedItem();
        double multiplier = LevelOfActivityService
                .GetInstance()
                .GetMultiplier(levelOfActivity);
        int age = GetAge(view);
        double bmrValue = CalculatorService.GetBMR(_gender, age, weightLbs, heightInches);
        double totalCalories = CalculatorService
                .CalculateCaloriesByHarrisBenedictEquation(bmrValue, multiplier);

        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtBmr)
                .setText(String.format("%.2f", bmrValue));
        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtTotalCalories)
                .setText(String.format("%.2f", totalCalories));

        ViewService.SetBmiInfo(view, weightLbs, heightInches);

        double idealWeightLbs = BMICategoryService
                .GetInstance()
                .IdealNormalWeightLbs(heightInches);
        double idealBmr = CalculatorService.GetBMR(_gender, age, idealWeightLbs, heightInches);
        double idealDailyCaloriesNeeded = CalculatorService
                .CalculateCaloriesByHarrisBenedictEquation(idealBmr, multiplier);
        double idealWeightToLose = weightLbs - idealWeightLbs;
        double caloriesToLose = CalculatorService.GetCaloriesToBurn(idealWeightToLose);
        int idealDaysToBurnCalories = (int)caloriesToLose / (int)idealDailyCaloriesNeeded;

        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtIdealBmr)
                .setText(String.format("%.2f", idealBmr));
        ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtIdealCaloriesNeeded)
                .setText(String.format("-->%.2f", idealDailyCaloriesNeeded));

        GetConcreteView(TextView.class, view, R.id.txtIdealCaloriesToBurn)
                .setText(String.format("-->%s", idealDaysToBurnCalories)
                );
    }

    private int GetAge(View view)
    {
        String ageValue = ActivityService
                .GetConcreteView(TextView.class, view, R.id.txtAge)
                .getText().toString();

        int age = 0;

        if(!TextUtils.isEmpty(ageValue)) age = Integer.parseInt(ageValue);

        return age;
    }
}
