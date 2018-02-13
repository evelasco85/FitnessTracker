package com.codeflowcrafter.FitnessTracker.BMR;

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

import com.codeflowcrafter.FitnessTracker.BMR.Implementation.Domain.BasalMetabolicRate;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.MVP.IView;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.MVP.Presenter;
import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Main;
import com.codeflowcrafter.FitnessTracker.Base.Activity.DataContainer;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;

import java.util.List;

/**
 * Created by enric on 13/02/2018.
 */

public class Activity_BMR_Main extends Base_Activity_Main<
        BasalMetabolicRate,
        IRequests,
        Activity_BMR_List_Item>
        implements IView {
    public static final String KEY_PROFILE_ID = "Profile Id";
    public static final String KEY_AGE = "Age";
    public static final String KEY_GENDER = "Gender";

    private Presenter _presenter;
    private int _profileId = 0;
    private int _age = 0;
    private String _gender;

    public Activity_BMR_Main()
    {
        super(
                R.layout.activity_bmr_main,
                R.id.fragment_bmrList
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
            _age = invoker.getIntExtra(KEY_AGE, 0);
            _gender = invoker.getStringExtra(KEY_GENDER);
        }
    }

    @Override
    public Activity_BMR_List_Item GetListItem(DataContainer<BasalMetabolicRate> container)
    {
        return new Activity_BMR_List_Item(this, GetViewRequest(), container);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        List<BasalMetabolicRate> data = GetViewRequest().GetData(_profileId);

        GetViewRequest().LoadEntities(data);
    }

    @Override
    public void SetViewHandlers()
    {
        View view = findViewById(android.R.id.content);

        ActivityService
                .GetConcreteView(Button.class, view, R.id.btnAddBMR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetViewRequest().Prompt_AddEntry();
                    }
                });
    }

    public void OnPromptExecution_Detail(BasalMetabolicRate entity){
        Activity_BMR_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_BMR_Dialog_ReadAddEdit.ACTION_READ, entity,
                _profileId, _age, _gender);
    }

    public void OnPromptExecution_AddEntry(){
        Activity_BMR_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_BMR_Dialog_ReadAddEdit.ACTION_ADD, null,
                _profileId, _age, _gender);
    }

    public void OnPromptExecution_EditEntry(BasalMetabolicRate entity){
        Activity_BMR_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_BMR_Dialog_ReadAddEdit.ACTION_EDIT, entity,
                _profileId, _age, _gender);
    }

    public void OnPromptExecution_DeleteEntry(final BasalMetabolicRate entity){
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