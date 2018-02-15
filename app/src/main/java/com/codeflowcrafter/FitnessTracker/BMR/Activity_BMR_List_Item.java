package com.codeflowcrafter.FitnessTracker.BMR;

import android.content.Context;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.BMR.Implementation.Domain.BasalMetabolicRate;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Base.Activity.BaseListItem;
import com.codeflowcrafter.FitnessTracker.Base.Activity.IDataContainer;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.FitnessTracker.Shared.LevelOfActivityService;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 13/02/2018.
 */

public class Activity_BMR_List_Item extends BaseListItem<BasalMetabolicRate, IRequests> {
    private final static int _resource = R.layout.activity_bmr_listitem;
    private String _gender;

    public Activity_BMR_List_Item(Context activityContext,
                                  IRequests viewRequest,
                                  IDataContainer<BasalMetabolicRate> container,
                                  String gender)
    {
        super(activityContext, _resource, viewRequest, container);

        _gender = gender;
    }

    public void SetItemViewHandler(Context activityContext, LinearLayout itemLayout,
                                   IRequests viewRequest, BasalMetabolicRate item) {
        final Context fActivityContext = activityContext;
        final IRequests fViewRequest = viewRequest;
        final BasalMetabolicRate fItem = item;

        final Button btnMenu = GetConcreteView(Button.class, itemLayout, R.id.btnBmrMenu);
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

        String date = fItem.GetDate();

        if(!TextUtils.isEmpty(date)) {
            ActivityService
                    .GetConcreteView(TextView.class, itemLayout, R.id.txtDate)
                    .setText(date);
        }

        double bmrValue = CalculatorService.GetBMR(
                _gender,
                fItem.GetAge(),
                fItem.GetWeightLbs(),
                fItem.GetHeightInches());
        double multiplier = LevelOfActivityService
                .GetInstance()
                .GetMultiplier(fItem.GetLevelOfActivity());
        double totalCalories = CalculatorService
                .CalculateCaloriesByHarrisBenedictEquation(bmrValue, multiplier);

        ActivityService
                .GetConcreteView(TextView.class, itemLayout, R.id.txtLevelOfActivity)
                .setText(fItem.GetLevelOfActivity());
        ActivityService
                .GetConcreteView(TextView.class, itemLayout, R.id.txtBmr)
                .setText(String.format("%.2f", bmrValue));
        ActivityService
                .GetConcreteView(TextView.class, itemLayout, R.id.txtDailyCalories)
                .setText(String.format("%.2f", totalCalories));
    }
}
