package com.codeflowcrafter.FitnessTracker.BMR;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeflowcrafter.FitnessTracker.BMR.Implementation.Domain.BasalMetabolicRate;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Dialog_ReadAddEdit;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;

/**
 * Created by enric on 13/02/2018.
 */

public class Activity_BMR_Dialog_ReadAddEdit extends Base_Activity_Dialog_ReadAddEdit<BasalMetabolicRate, IRequests> {
    private final static int _fragmentId = R.layout.activity_bmr_fragment_read_add_edit;
    private final static int _saveCancelConcreteViewId = R.id.saveCancelFragmentPlaceholder;
    public static final String FRAGMENT_NAME = "Add/Edit BMR";
    public static final String KEY_PROFILE_ID = "Profile Id";

    private int _id = 0;
    private int _profileId = 0;

    public static Activity_BMR_Dialog_ReadAddEdit newInstance(
            String action,
            BasalMetabolicRate entity,
            int profileId)
    {
        Activity_BMR_Dialog_ReadAddEdit dialog = new Activity_BMR_Dialog_ReadAddEdit();
        Bundle args = new Bundle();

        args.putString(Base_Activity_Dialog_ReadAddEdit.KEY_ACTION, action);
        args.putInt(KEY_PROFILE_ID, profileId);
        dialog.setArguments(args);
        dialog.SetEntityToEdit(entity);

        return dialog;
    }

    public static void Show(
            FragmentManager manager, IRequests request,
            String action, BasalMetabolicRate entity,
            int profileId)
    {
        Activity_BMR_Dialog_ReadAddEdit dialog = Activity_BMR_Dialog_ReadAddEdit
                .newInstance(action, entity, profileId);

        dialog.SetViewRequest(request);
        dialog.show(manager, FRAGMENT_NAME);
    }

    public Activity_BMR_Dialog_ReadAddEdit()
    {
        super(_fragmentId, _saveCancelConcreteViewId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        _profileId = getArguments().getInt(KEY_PROFILE_ID);

        return view;
    }

    public void SetConcreteViews(final View view, final String selectedAction) {
        if(selectedAction == ACTION_READ)
        {
            //Disable input contols

            return;
        }
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

//        return new BasalMetabolicRate(mapper);
        return null;
    }

    public void SetModelToViewData(View view, BasalMetabolicRate entity){
        if(entity == null){
            return;
        }

        _id = entity.GetId();
    }
}
