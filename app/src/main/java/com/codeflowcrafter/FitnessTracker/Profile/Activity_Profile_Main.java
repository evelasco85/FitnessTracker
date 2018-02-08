package com.codeflowcrafter.FitnessTracker.Profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Main;
import com.codeflowcrafter.FitnessTracker.Base.Activity.DataContainer;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP.IView;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP.Presenter;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.TranslatorService;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

public class Activity_Profile_Main extends Base_Activity_Main<
        Profile,
        IRequests,
        Activity_Profile_List_Item>
        implements IView
{
    private Presenter _presenter;

    public Activity_Profile_Main()
    {
        super(
                R.layout.activity_profile_main,
                R.id.fragment_profileList
        );

        _presenter = new Presenter(
                this,
                TranslatorService.GetInstance().GetProfileTranslator()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public Activity_Profile_List_Item GetListItem(DataContainer<Profile> container)
    {
        return new Activity_Profile_List_Item(this, GetViewRequest(), container);
    }

    @Override
    public void SetViewHandlers()
    {
        View view = findViewById(android.R.id.content);

        GetConcreteView(Button.class, view, R.id.btnAddProfile)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetViewRequest().Prompt_AddEntry();
                    }
                });
    }

    public void OnPromptExecution_Detail(Profile entity){
        Activity_Profile_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_Profile_Dialog_ReadAddEdit.ACTION_READ, entity);
    }

    public void OnPromptExecution_AddEntry(){
        Activity_Profile_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_Profile_Dialog_ReadAddEdit.ACTION_ADD, null);
    }

    public void OnPromptExecution_EditEntry(Profile entity){
        Activity_Profile_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_Profile_Dialog_ReadAddEdit.ACTION_EDIT, entity);
    }

    public void OnPromptExecution_DeleteEntry(final Profile entity){
        AlertDialog.Builder verify = ActivityService.CreateDeleteAlertDialog(this);

        verify.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg) {
                        GetViewRequest().Delete(entity);
                        String message = "'" + entity.GetName() + "' profile deleted";
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
