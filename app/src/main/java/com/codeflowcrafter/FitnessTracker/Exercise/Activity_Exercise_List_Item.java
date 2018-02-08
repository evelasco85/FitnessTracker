package com.codeflowcrafter.FitnessTracker.Exercise;

import android.content.Context;
import android.widget.LinearLayout;

import com.codeflowcrafter.FitnessTracker.Base.Activity.BaseListItem;
import com.codeflowcrafter.FitnessTracker.Base.Activity.IDataContainer;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.R;

/**
 * Created by enric on 08/02/2018.
 */

public class Activity_Exercise_List_Item extends BaseListItem<Exercise, IRequests> {
    private final static int _resource = R.layout.activity_exercise_listitem;

    public Activity_Exercise_List_Item(Context activityContext,
                                      IRequests viewRequest,
                                      IDataContainer<Exercise> container)
    {
        super(activityContext, _resource, viewRequest, container);
    }

    public void SetItemViewHandler(Context activityContext, LinearLayout itemLayout,
                                   IRequests viewRequest, Exercise item) {
        final Context fActivityContext = activityContext;
        final IRequests fViewRequest = viewRequest;
        final Exercise fItem = item;
    }
}
