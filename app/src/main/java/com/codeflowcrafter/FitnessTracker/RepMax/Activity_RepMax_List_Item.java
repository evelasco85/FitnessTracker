package com.codeflowcrafter.FitnessTracker.RepMax;

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
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.Domain.RepMax;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.MVP.IRequests;

import java.util.List;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 22/02/2018.
 */

public class Activity_RepMax_List_Item extends BaseListItem<RepMax, IRequests> {
    private final static int _resource = R.layout.activity_repmax_listitem;

    private List<Exercise> _exercises;

    public Activity_RepMax_List_Item(Context activityContext,
                                  IRequests viewRequest,
                                  IDataContainer<RepMax> container,
                                  List<Exercise> exercises)
    {
        super(activityContext, _resource, viewRequest, container);

        _exercises = exercises;
    }

    public void SetItemViewHandler(Context activityContext, LinearLayout itemLayout,
                                   IRequests viewRequest, RepMax item) {
        final Context fActivityContext = activityContext;
        final IRequests fViewRequest = viewRequest;
        final RepMax fItem = item;

        final Button btnMenu = GetConcreteView(Button.class, itemLayout, R.id.btnRepmaxMenu);
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

        GetConcreteView(TextView.class, itemLayout, R.id.txtStartDate)
                .setText(item.GetStartDate());
        GetConcreteView(TextView.class, itemLayout, R.id.txtExercise)
                .setText(item.GetExercise());
        GetConcreteView(TextView.class, itemLayout, R.id.txtWeight)
                .setText(String.valueOf(item.GetWeightLbs()));
        GetConcreteView(TextView.class, itemLayout, R.id.txtRepetitions)
                .setText(String.valueOf(item.GetRepititions()));
    }
}
