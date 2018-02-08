package com.codeflowcrafter.FitnessTracker.Exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Main;
import com.codeflowcrafter.FitnessTracker.Base.Activity.DataContainer;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.MVP.IView;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.MVP.Presenter;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.TranslatorService;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

public class Activity_Exercise_Main extends Base_Activity_Main<
        Exercise,
        IRequests,
        Activity_Exercise_List_Item>
        implements IView {

    private Presenter _presenter;

    public Activity_Exercise_Main()
    {
        super(
                R.layout.activity_exercise_main,
                R.id.fragment_exerciseList
        );

        _presenter = new Presenter(
                this,
                TranslatorService.GetInstance().GetExerciseTranslator()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public Activity_Exercise_List_Item GetListItem(DataContainer<Exercise> container)
    {
        return new Activity_Exercise_List_Item(this, GetViewRequest(), container);
    }

    @Override
    public void SetViewHandlers()
    {
        View view = findViewById(android.R.id.content);

        GetConcreteView(Button.class, view, R.id.btnAddExercise)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetViewRequest().Prompt_AddEntry();
                    }
                });
    }

    public void OnPromptExecution_Detail(Exercise entity){
        Activity_Exercise_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_Exercise_Dialog_ReadAddEdit.ACTION_READ, entity);
    }

    public void OnPromptExecution_AddEntry(){
        Activity_Exercise_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_Exercise_Dialog_ReadAddEdit.ACTION_ADD, null);
    }

    public void OnPromptExecution_EditEntry(Exercise entity){
        Activity_Exercise_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_Exercise_Dialog_ReadAddEdit.ACTION_EDIT, entity);
    }

    public void OnPromptExecution_DeleteEntry(final Exercise entity){
        AlertDialog.Builder verify = ActivityService.CreateDeleteAlertDialog(this);

        verify.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg) {
                        GetViewRequest().Delete(entity);
                        String message = "'" + entity.GetName() + "' exercise deleted";
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
