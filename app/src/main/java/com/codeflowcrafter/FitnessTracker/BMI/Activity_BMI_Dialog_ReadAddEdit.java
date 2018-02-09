package com.codeflowcrafter.FitnessTracker.BMI;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.FitnessTracker.BMI.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.R;

/**
 * Created by enric on 09/02/2018.
 */

public class Activity_BMI_Dialog_ReadAddEdit extends Base_Activity_Dialog_ReadAddEdit<BodyMassIndex, IRequests> {
    private final static int _fragmentId = R.layout.activity_bmi_fragment_read_add_edit;
    private final static int _saveCancelConcreteViewId = R.id.saveCancelFragmentPlaceholder;
    public static final String FRAGMENT_NAME = "Add/Edit BMI";

    private int _id = 0;

    public static Activity_BMI_Dialog_ReadAddEdit newInstance(
            String action,
            BodyMassIndex entity)
    {
        Activity_BMI_Dialog_ReadAddEdit dialog = new Activity_BMI_Dialog_ReadAddEdit();
        Bundle args = new Bundle();

        args.putString(Base_Activity_Dialog_ReadAddEdit.KEY_ACTION, action);
        dialog.setArguments(args);
        dialog.SetEntityToEdit(entity);

        return dialog;
    }

    public static void Show(
            FragmentManager manager, IRequests request,
            String action, BodyMassIndex entity)
    {
        Activity_BMI_Dialog_ReadAddEdit dialog = Activity_BMI_Dialog_ReadAddEdit
                .newInstance(action, entity);

        dialog.SetViewRequest(request);
        dialog.show(manager, FRAGMENT_NAME);
    }

    public Activity_BMI_Dialog_ReadAddEdit()
    {
        super(_fragmentId, _saveCancelConcreteViewId);
    }

    public void SetConcreteViews(final View view, final String selectedAction) {
    }

    public BodyMassIndex ViewDataToModel(View view){
        return null;
    }

    public void SetModelToViewData(View view, BodyMassIndex entity){
    }
}
