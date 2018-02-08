package com.codeflowcrafter.FitnessTracker.Exercise;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.R;

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
    }

    public Exercise ViewDataToModel(View view){
        return null;
    }

    public void SetModelToViewData(View view, Exercise entity){
    }
}
