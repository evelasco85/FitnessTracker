package com.codeflowcrafter.FitnessTracker.ExerciseHeartRate;

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
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain.ExerciseHeartRate;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Shared.IntensityOfExercise;

import java.util.List;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 19/02/2018.
 */

public class Activity_EHR_List_Item extends BaseListItem<ExerciseHeartRate, IRequests> {
    private final static int _resource = R.layout.activity_ehr_listitem;

    private List<Exercise> _exercises;

    public Activity_EHR_List_Item(Context activityContext,
                                  IRequests viewRequest,
                                  IDataContainer<ExerciseHeartRate> container,
                                  List<Exercise> exercises)
    {
        super(activityContext, _resource, viewRequest, container);

        _exercises = exercises;
    }

    public void SetItemViewHandler(Context activityContext, LinearLayout itemLayout,
                                   IRequests viewRequest, ExerciseHeartRate item) {
        final Context fActivityContext = activityContext;
        final IRequests fViewRequest = viewRequest;
        final ExerciseHeartRate fItem = item;

        final Button btnMenu = GetConcreteView(Button.class, itemLayout, R.id.btnEhrMenu);
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

        IntensityOfExercise intensity = fViewRequest
                .GetIntensityOfExercise(_exercises, item.GetExercise());
        String zone = fViewRequest.GetZoneRange(
                fViewRequest.GetMhr(item.GetAge()),
                item.GetRestingHeartRate(),
                intensity
        );

        GetConcreteView(TextView.class, itemLayout, R.id.txtDate)
                .setText(item.GetDate());
        GetConcreteView(TextView.class, itemLayout, R.id.txtExercise)
                .setText(item.GetExercise());
        GetConcreteView(TextView.class, itemLayout, R.id.txtZone)
                .setText(zone);
        GetConcreteView(TextView.class, itemLayout, R.id.txtExerciseHeartRate)
                .setText(String.valueOf(item.GetExerciseHeartRate()));
        GetConcreteView(TextView.class, itemLayout, R.id.txtRecoveryHeartRate)
                .setText(String.valueOf(item.GetRecoveryHeartRate()));
    }
}
