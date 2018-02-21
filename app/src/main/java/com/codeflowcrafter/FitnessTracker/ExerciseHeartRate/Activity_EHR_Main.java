package com.codeflowcrafter.FitnessTracker.ExerciseHeartRate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Main;
import com.codeflowcrafter.FitnessTracker.Base.Activity.DataContainer;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain.ExerciseHeartRate;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.MVP.IView;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.MVP.Presenter;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;

import java.util.List;

public class Activity_EHR_Main extends Base_Activity_Main<
        ExerciseHeartRate,
        IRequests,
        Activity_EHR_List_Item>
        implements IView {
    public static final String KEY_PROFILE_ID = "Profile Id";
    public static final String KEY_AGE = "Age";

    private Presenter _presenter;
    private int _profileId = 0;
    private int _age = 0;
    private List<Exercise> _exercises;

    public Activity_EHR_Main()
    {
        super(
                R.layout.activity_ehr_main,
                R.id.fragment_ehrList
        );

        _presenter = new Presenter(this);
        _exercises = GetViewRequest().GetExercisesData();
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
        }
    }

    @Override
    public Activity_EHR_List_Item GetListItem(DataContainer<ExerciseHeartRate> container)
    {
        return new Activity_EHR_List_Item(this, GetViewRequest(), container, _exercises);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        List<ExerciseHeartRate> data = GetViewRequest().GetData(_profileId);

        GetViewRequest().LoadEntities(data);
    }

    @Override
    public void SetViewHandlers()
    {
        View view = findViewById(android.R.id.content);

        ActivityService
                .GetConcreteView(Button.class, view, R.id.btnAddEHR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetViewRequest().Prompt_AddEntry();
                    }
                });
    }

    public void OnPromptExecution_Detail(ExerciseHeartRate entity){
        Activity_EHR_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_EHR_Dialog_ReadAddEdit.ACTION_READ, entity,
                _profileId, _age, _exercises);
    }

    public void OnPromptExecution_AddEntry(){
        Activity_EHR_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_EHR_Dialog_ReadAddEdit.ACTION_ADD, null,
                _profileId, _age, _exercises);
    }

    public void OnPromptExecution_EditEntry(ExerciseHeartRate entity){
        Activity_EHR_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_EHR_Dialog_ReadAddEdit.ACTION_EDIT, entity,
                _profileId, _age, _exercises);
    }

    public void OnPromptExecution_DeleteEntry(final ExerciseHeartRate entity){
        AlertDialog.Builder verify = ActivityService.CreateDeleteAlertDialog(this);

        verify.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg) {
                        GetViewRequest().Delete(entity);
                        String message = "EHR id '" + entity.GetId() + "' deleted";
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
