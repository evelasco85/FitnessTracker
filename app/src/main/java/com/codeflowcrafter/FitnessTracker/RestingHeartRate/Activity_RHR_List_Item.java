package com.codeflowcrafter.FitnessTracker.RestingHeartRate;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.Base.Activity.BaseListItem;
import com.codeflowcrafter.FitnessTracker.Base.Activity.IDataContainer;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.MVP.IRequests;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

/**
 * Created by enric on 11/02/2018.
 */

public class Activity_RHR_List_Item extends BaseListItem<RestingHeartRate, IRequests> {
    private final static int _resource = R.layout.activity_rhr_listitem;

    public Activity_RHR_List_Item(Context activityContext,
                                  IRequests viewRequest,
                                  IDataContainer<RestingHeartRate> container)
    {
        super(activityContext, _resource, viewRequest, container);
    }

    public void SetItemViewHandler(Context activityContext, LinearLayout itemLayout,
                                   IRequests viewRequest, RestingHeartRate item) {
        final Context fActivityContext = activityContext;
        final IRequests fViewRequest = viewRequest;
        final RestingHeartRate fItem = item;

        final Button btnMenu = GetConcreteView(Button.class, itemLayout, R.id.btnRhrMenu);
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

        int mhr = fViewRequest.GetMhr(item.GetAge());

        GetConcreteView(TextView.class, itemLayout, R.id.txtRhrId)
                .setText(String.valueOf(item.GetId()));
        GetConcreteView(TextView.class, itemLayout, R.id.txtDate)
                .setText(item.GetDate());
        GetConcreteView(TextView.class, itemLayout, R.id.txtMhr)
                .setText(String.valueOf(mhr));
        GetConcreteView(TextView.class, itemLayout, R.id.txtRhr)
                .setText(String.valueOf(item.GetRestingHeartRate()));
    }
}
