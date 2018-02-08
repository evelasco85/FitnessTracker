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
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.FitnessTracker.Services.ViewService;

import java.util.Calendar;

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

        final Button btnProfileMenu = GetConcreteView(Button.class, itemLayout, R.id.btnProfileMenu);
        final PopupMenu popMenu = new PopupMenu(activityContext, btnProfileMenu);

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

        GetConcreteView(TextView.class, itemLayout, R.id.txtProfileId)
                .setText(String.valueOf(item.GetId()));
        GetConcreteView(TextView.class, itemLayout, R.id.txtName)
                .setText(item.GetName());
        GetConcreteView(TextView.class, itemLayout, R.id.txtGender)
                .setText(item.GetGender());
        GetConcreteView(TextView.class, itemLayout, R.id.txtBirthday)
                .setText(item.GetDateOfBirth());

        TextView txtAge = GetConcreteView(TextView.class, itemLayout, R.id.txtAge);
        TextView txtMhr = GetConcreteView(TextView.class, itemLayout, R.id.txtMhr);
        Calendar dobCalendar = CalculatorService.ConvertToCalendar(item.GetDateOfBirth());
        int age = CalculatorService.CalculateAge(dobCalendar);

        ViewService.SetAge(txtAge, age);
        ViewService.SetMHR(txtMhr, age);

        btnProfileMenu.setOnClickListener(new View.OnClickListener() {
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
    }
}
