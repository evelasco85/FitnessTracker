package com.codeflowcrafter.FitnessTracker.RestingHeartRate;

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
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.QueryObjects.QueryByProfileId;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.MVP.IRequests;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.MVP.IView;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.MVP.Presenter;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.TranslatorService;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;

public class Activity_RHR_Main extends Base_Activity_Main<
        RestingHeartRate,
        IRequests,
        Activity_RHR_List_Item>
        implements IView {
    public static final String KEY_PROFILE_ID = "Profile Id";
    public static final String KEY_AGE = "Age";

    private Presenter _presenter;
    private int _profileId = 0;
    private int _age = 0;

    public Activity_RHR_Main()
    {
        super(
                R.layout.activity_rhr_main,
                R.id.fragment_rhrList
        );

        _presenter = new Presenter(
                this,
                TranslatorService.GetInstance().GetRhrTranslator()
        );
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
    public Activity_RHR_List_Item GetListItem(DataContainer<RestingHeartRate> container)
    {
        return new Activity_RHR_List_Item(this, GetViewRequest(), container);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        IDataSynchronizationManager manager= DataSynchronizationManager.GetInstance();
        IRepository<RestingHeartRate> repository = manager.GetRepository(RestingHeartRate.class);
        QueryByProfileId.Criteria criteria = new QueryByProfileId.Criteria(_profileId);

        GetViewRequest().LoadEntities(repository.Matching(criteria));
    }

    @Override
    public void SetViewHandlers()
    {
        View view = findViewById(android.R.id.content);

        ActivityService
                .GetConcreteView(Button.class, view, R.id.btnAddRHR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetViewRequest().Prompt_AddEntry();
                    }
                });
    }

    public void OnPromptExecution_Detail(RestingHeartRate entity){
        Activity_RHR_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_RHR_Dialog_ReadAddEdit.ACTION_READ, entity,
                _profileId, _age);
    }

    public void OnPromptExecution_AddEntry(){
        Activity_RHR_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_RHR_Dialog_ReadAddEdit.ACTION_ADD, null,
                _profileId, _age);
    }

    public void OnPromptExecution_EditEntry(RestingHeartRate entity){
        Activity_RHR_Dialog_ReadAddEdit.Show(
                getFragmentManager(), GetViewRequest(),
                Activity_RHR_Dialog_ReadAddEdit.ACTION_EDIT, entity,
                _profileId, _age);
    }

    public void OnPromptExecution_DeleteEntry(final RestingHeartRate entity){
        AlertDialog.Builder verify = ActivityService.CreateDeleteAlertDialog(this);

        verify.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg) {
                        GetViewRequest().Delete(entity);
                        String message = "RHR id '" + entity.GetId() + "' deleted";
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
