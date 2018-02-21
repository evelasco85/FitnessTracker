package com.codeflowcrafter.FitnessTracker.Profile;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.Base.Activity.BaseListItem;
import com.codeflowcrafter.FitnessTracker.Base.Activity.IDataContainer;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.R;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 05/02/2018.
 */

public class Activity_Profile_List_Item extends BaseListItem<Profile, IRequests> {
    private final static int _resource = R.layout.activity_profile_listitem;

    public Activity_Profile_List_Item(Context activityContext,
                                      IRequests viewRequest,
                                      IDataContainer<Profile> container)
    {
        super(activityContext, _resource, viewRequest, container);
    }

    public void SetItemViewHandler(Context activityContext, LinearLayout itemLayout,
                                   IRequests viewRequest, Profile item)
    {
        final Context fActivityContext = activityContext;
        final IRequests fViewRequest = viewRequest;
        final Profile fItem = item;
        final int age = fViewRequest.GetAge(item.GetDateOfBirth());

        final Button btnMenu = GetConcreteView(Button.class, itemLayout, R.id.btnProfileMenu);
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

        final Button btnOptions = GetConcreteView(Button.class, itemLayout, R.id.btnOptions);
        final PopupMenu popMenuOptions = new PopupMenu(activityContext, btnOptions);

        popMenuOptions.inflate(R.menu.mnu_profile_options);
        popMenuOptions.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mnuItem) {
                switch (mnuItem.getItemId()) {
                    case (R.id.mnuEhr):
                        fViewRequest.Prompt_ExerciseHeartRate(fItem.GetId(), age);
                        return true;
                    case (R.id.mnuBmr):
                        fViewRequest.Prompt_BMR(
                                fItem.GetId(),
                                age,
                                fItem.GetGender());
                        return true;
                    case (R.id.mnuRestingHeartRate):
                        fViewRequest.Prompt_RestingHeartRate(fItem.GetId(), age);
                        return true;
                    case (R.id.mnuBmi):
                        fViewRequest.Prompt_BMI(fItem.GetId(), fItem.GetHeightInches());
                        return true;
                    default:
                        return false;
                }
            }
        });
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenuOptions.show();
            }
        });


        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fViewRequest.Prompt_Detail(fItem);
            }
        });

        GetConcreteView(TextView.class, itemLayout, R.id.txtProfileId)
                .setText(String.valueOf(item.GetId()));
        GetConcreteView(TextView.class, itemLayout, R.id.txtName)
                .setText(item.GetName());
        GetConcreteView(TextView.class, itemLayout, R.id.txtGender)
                .setText(item.GetGender());
        GetConcreteView(TextView.class, itemLayout, R.id.txtBirthday)
                .setText(item.GetDateOfBirth());
        GetConcreteView(TextView.class, itemLayout, R.id.txtAge)
                .setText(String.format("(%s)", String.valueOf(age)));

        int maximumHeartRate = fViewRequest.GetMhr(age);

        GetConcreteView(TextView.class, itemLayout, R.id.txtMhr)
                .setText(String.format("%s bpm", String.valueOf(maximumHeartRate)));
    }
}
