package com.codeflowcrafter.FitnessTracker.Exercise;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.Base.Activity.BaseListItem;
import com.codeflowcrafter.FitnessTracker.Base.Activity.IDataContainer;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.R;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

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

        final Button btnMenu = GetConcreteView(Button.class, itemLayout, R.id.btnExerciseMenu);
        final PopupMenu popMenu = new PopupMenu(activityContext, btnMenu);

        popMenu.inflate(R.menu.mnu_edit_delete);
        popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mnuItem) {
                switch (mnuItem.getItemId()) {
                    case (R.id.mnuEdit):
                        fViewRequest.Prompt_EditEntry(fItem);
                        return true;
                    case (R.id.mnuDelete):
                        fViewRequest.Prompt_DeleteEntry(fItem);
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu.show();
            }
        });
        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fViewRequest.Prompt_Detail(fItem);
            }
        });

        GetConcreteView(TextView.class, itemLayout, R.id.txtExerciseId)
                .setText(String.valueOf(item.GetId()));
        GetConcreteView(TextView.class, itemLayout, R.id.txtName)
                .setText(item.GetName());
        GetConcreteView(TextView.class, itemLayout, R.id.txtDuration)
                .setText(String.valueOf(item.GetDurationMinutes()));
        GetConcreteView(TextView.class, itemLayout, R.id.txtType)
                .setText(item.GetType());
        GetConcreteView(TextView.class, itemLayout, R.id.txtIntensity)
                .setText(item.GetIntensity());
    }
}
