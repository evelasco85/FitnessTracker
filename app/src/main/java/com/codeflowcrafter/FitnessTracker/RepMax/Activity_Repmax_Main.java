package com.codeflowcrafter.FitnessTracker.RepMax;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Main;
import com.codeflowcrafter.FitnessTracker.Base.Activity.DataContainer;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.Domain.RepMax;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.MVP.IView;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.MVP.Presenter;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;

import java.util.List;

public class Activity_Repmax_Main extends Base_Activity_Main<
        RepMax,
        IRequests,
        Activity_RepMax_List_Item>
        implements IView {
    public static final String KEY_PROFILE_ID = "Profile Id";

    private Presenter _presenter;
    private int _profileId = 0;

    public Activity_Repmax_Main()
    {
        super(
                R.layout.activity_repmax_main,
                R.id.fragment_repmaxList
        );

        _presenter = new Presenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent invoker = getIntent();

        if (invoker != null) {
            _profileId = invoker.getIntExtra(KEY_PROFILE_ID, 0);
        }
    }

    @Override
    public Activity_RepMax_List_Item GetListItem(DataContainer<RepMax> container)
    {
        return new Activity_RepMax_List_Item(this, GetViewRequest(), container);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        List<RepMax> data = GetViewRequest().GetData(_profileId);

        GetViewRequest().LoadEntities(data);
    }

    @Override
    public void SetViewHandlers()
    {
        View view = findViewById(android.R.id.content);

        ActivityService
                .GetConcreteView(Button.class, view, R.id.btnAddRepMax)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetViewRequest().Prompt_AddEntry();
                    }
                });
    }

    public void OnPromptExecution_Detail(RepMax entity){
        Activity_RepMax_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_RepMax_Dialog_ReadAddEdit.ACTION_READ, entity,
                _profileId);
    }

    public void OnPromptExecution_AddEntry(){
        Activity_RepMax_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_RepMax_Dialog_ReadAddEdit.ACTION_ADD, null,
                _profileId);
    }

    public void OnPromptExecution_EditEntry(RepMax entity){
        Activity_RepMax_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_RepMax_Dialog_ReadAddEdit.ACTION_EDIT, entity,
                _profileId);
    }

    public void OnPromptExecution_DeleteEntry(final RepMax entity){
        AlertDialog.Builder verify = ActivityService.CreateDeleteAlertDialog(this);

        verify.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg) {
                        GetViewRequest().Delete(entity);
                        String message = "BMI id '" + entity.GetId() + "' deleted";
                        Toast
                                .makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        );

        verify.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg) {
                        //No action takes
                    }
                }
        );

        verify.show();
    }
}

