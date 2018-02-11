package com.codeflowcrafter.FitnessTracker.BMI;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.FitnessTracker.BMI.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Base.Activity.BaseListItem;
import com.codeflowcrafter.FitnessTracker.Base.Activity.IDataContainer;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ViewService;
import com.codeflowcrafter.FitnessTracker.Shared.BMICategoryService;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

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

        final Button btnMenu = GetConcreteView(Button.class, itemLayout, R.id.btnBmiMenu);
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

        int height = item.GetHeightInches();
        double weight = item.GetWeightLbs();
        double idealWeight = BMICategoryService
                .GetInstance()
                .IdealNormalWeightLbs(height);
        double ideaWeightToLose = weight - idealWeight;

        GetConcreteView(TextView.class, itemLayout, R.id.txtBmiId)
                .setText(String.valueOf(item.GetId()));
        GetConcreteView(TextView.class, itemLayout, R.id.txtDate)
                .setText(item.GetDate());
        GetConcreteView(TextView.class, itemLayout, R.id.txtWeight)
                .setText(String.valueOf(weight));
        GetConcreteView(TextView.class, itemLayout, R.id.txtIdealWeight)
                .setText(String.format("%.2f", idealWeight));
        GetConcreteView(TextView.class, itemLayout, R.id.txtIdealWeightToLose)
                .setText(String.format("%.2f", ideaWeightToLose));

        EditText txtFeet = GetConcreteView(EditText.class, itemLayout, R.id.txtFeet);
        EditText txtInches = GetConcreteView(EditText.class, itemLayout, R.id.txtInches);

        ViewService.DisableConcreteView(txtFeet);
        ViewService.DisableConcreteView(txtInches);
        ViewService.DisableConcreteView(GetConcreteView(TextView.class, itemLayout, R.id.txtDate));
        ViewService.SetHeight(height, txtFeet, txtInches);
        ViewService.SetClassification(
                GetConcreteView(TextView.class, itemLayout, R.id.txtClassification),
                weight,
                height
        );
    }
}
