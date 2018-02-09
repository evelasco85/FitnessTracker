package com.codeflowcrafter.FitnessTracker.Exercise;

import android.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.Services.ViewService;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 08/02/2018.
 */

public class Activity_Exercise_Dialog_ReadAddEdit  extends Base_Activity_Dialog_ReadAddEdit<Exercise, IRequests> {
    private final static int _fragmentId = R.layout.activity_exercise_fragment_read_add_edit;
    private final static int _saveCancelConcreteViewId = R.id.saveCancelFragmentPlaceholder;
    public static final String FRAGMENT_NAME = "Add/Edit Exercise";

    private int _id = 0;

    public static Activity_Exercise_Dialog_ReadAddEdit newInstance(
            String action,
            Exercise entity)
    {
        Activity_Exercise_Dialog_ReadAddEdit dialog = new Activity_Exercise_Dialog_ReadAddEdit();
        Bundle args = new Bundle();

        args.putString(Base_Activity_Dialog_ReadAddEdit.KEY_ACTION, action);
        dialog.setArguments(args);
        dialog.SetEntityToEdit(entity);

        return dialog;
    }

    public static void Show(
            FragmentManager manager, IRequests request,
            String action, Exercise entity)
    {
        Activity_Exercise_Dialog_ReadAddEdit dialog = Activity_Exercise_Dialog_ReadAddEdit
                .newInstance(action, entity);

        dialog.SetViewRequest(request);
        dialog.show(manager, FRAGMENT_NAME);
    }

    public Activity_Exercise_Dialog_ReadAddEdit()
    {
        super(_fragmentId, _saveCancelConcreteViewId);
    }

    public void SetConcreteViews(final View view, final String selectedAction){
        Spinner spinIntensity = GetConcreteView(Spinner.class, view, R.id.spinIntensity);
        Spinner spinType = GetConcreteView(Spinner.class, view, R.id.spinType);

        ViewService.InitializeIntensitySpinner(this.getActivity(), spinIntensity);
        ViewService.InitializeExerciseTypeSpinner(this.getActivity(), spinType);

        if(selectedAction == ACTION_READ)
        {
            ViewService.DisableConcreteView(GetConcreteView(EditText.class, view, R.id.txtName));
            ViewService.DisableConcreteView(GetConcreteView(EditText.class, view, R.id.txtDuration));
            spinIntensity.setEnabled(false);
            spinType.setEnabled(false);

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

    public Exercise ViewDataToModel(View view){
        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(Exercise.class);
        String type = (String)ActivityService
                .GetConcreteView(Spinner.class, view, R.id.spinType)
                .getSelectedItem();
        String intensity = (String)ActivityService
                .GetConcreteView(Spinner.class, view, R.id.spinIntensity)
                .getSelectedItem();
        String minutes = GetConcreteView(EditText.class, view, R.id.txtDuration)
                .getText()
                .toString();
        int durationInMinutes = 0;

        if(!TextUtils.isEmpty(minutes)) durationInMinutes = Integer
                .parseInt(minutes);

        return new Exercise(
                mapper,
                _id,
                ActivityService
                        .GetConcreteView(EditText.class, view, R.id.txtName)
                        .getText()
                        .toString(),
                durationInMinutes,
                type,
                intensity
        );
    }

    public void SetModelToViewData(View view, Exercise entity){
        if(entity == null) return;

        _id = entity.GetId();

        ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtName)
                .setText(entity.GetName());
        ActivityService
                .GetConcreteView(EditText.class, view, R.id.txtDuration)
                .setText(String.valueOf(entity.GetDurationMinutes()));

        String intensity = entity.GetIntensity();
        if(!TextUtils.isEmpty(intensity)) {
            final Spinner spinIntensity = ActivityService
                    .GetConcreteView(Spinner.class, view, R.id.spinIntensity);

            ViewService.SetSpinnerValue(spinIntensity, intensity);
        }

        String type = entity.GetType();
        if(!TextUtils.isEmpty(type)) {
            final Spinner spinType = ActivityService
                    .GetConcreteView(Spinner.class, view, R.id.spinType);

            ViewService.SetSpinnerValue(spinType, type);
        }
    }
}
