package com.codeflowcrafter.FitnessTracker.BMI.Implementation;

import android.content.Context;
import android.widget.LinearLayout;

import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.FitnessTracker.BMI.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Base.Activity.BaseListItem;
import com.codeflowcrafter.FitnessTracker.Base.Activity.IDataContainer;
import com.codeflowcrafter.FitnessTracker.R;

/**
 * Created by enric on 09/02/2018.
 */

public class Activity_BMI_List_Item extends BaseListItem<BodyMassIndex, IRequests> {
    private final static int _resource = R.layout.activity_bmi_listitem;

    public Activity_BMI_List_Item(Context activityContext,
                                       IRequests viewRequest,
                                       IDataContainer<BodyMassIndex> container)
    {
        super(activityContext, _resource, viewRequest, container);
    }

    public void SetItemViewHandler(Context activityContext, LinearLayout itemLayout,
                                   IRequests viewRequest, BodyMassIndex item) {
        final Context fActivityContext = activityContext;
        final IRequests fViewRequest = viewRequest;
        final BodyMassIndex fItem = item;
    }
}
