package com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP;

import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_Presenter;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerApplication;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.MapperInvocationDelegate;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.QueryObjects.QueryAll;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;

import java.util.Calendar;
import java.util.List;

/**
 * Created by enric on 04/02/2018.
 */

public class Presenter extends Crud_Presenter<Profile, IRequests, IView>
    implements IRequests
{
    private IView _view;
    private final static IStaticLogEntryWrapper _slc = FitnessTrackerApplication.GetInstance().CreateSLC();

    public Presenter(IView view)
    {
        super(view,
                new MapperInvocationDelegate(_slc));

        _slc.SetComponent("Profile");
        view.SetViewRequest(this);

        _view = view;
    }

    @Override
    public Uri GetContentUri() {
        return FitnessTrackerContentProviders
                .GetInstance()
                .GetProfileProvider()
                .GetContentUri();
    }

    @Override
    public int LoadEntities(List<Profile> entityList)
    {
        int size = super.LoadEntities(entityList);

        _slc
                .SetEvent(String.format("Loaded project count %d", size))
                .EmitLog(Priority.Info, Status.Success);

        return size;
    }

    @Override
    public void Prompt_AddEntry()
    {
        super.Prompt_AddEntry();
        _slc
                .SetEvent("Open Profile Entry")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void CancelEntry(){
        _slc
                .SetEvent("Cancel profile entry window")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Add(Profile entity)
    {
        super.Add(entity);
        _slc
                .SetEvent("Profile Added")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Update(Profile entity)
    {
        super.Update(entity);
        _slc
                .SetEvent(String.format("Updated profile id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_EditEntry(Profile entity) {
        super.Prompt_EditEntry(entity);
        _slc
                .SetEvent("Open profile editing")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
   public void Delete(Profile entity)
    {
        super.Delete(entity);
        _slc
                .SetEvent(String.format("Deleted profile id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_Detail(Profile entity)
    {
        super.Prompt_Detail(entity);
        _slc
                .SetEvent(String.format("Showing details of profile id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_BMI(int profileId, int heightInches)
    {
        _view.OnPromptExecution_BMI(profileId, heightInches);
        _slc
                .SetEvent(String.format("Showing BMI list"))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_BMR(int profileId, int age, String gender)
    {
        _view.OnPromptExecution_BMR(profileId, age, gender);
        _slc
                .SetEvent(String.format("Showing BMR list"))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_RestingHeartRate(int profileId, int age)
    {
        _view.OnPromptExecution_RestingHeartRate(profileId, age);
        _slc
                .SetEvent(String.format("Showing Resting Heart Rate list"))
                .EmitLog(Priority.Info, Status.Success);
    }

    public int GetAge(String dateOfBirth)
    {
        Calendar dobCalendar = CalculatorService.ConvertToCalendar(dateOfBirth);

        return GetAge(dobCalendar);
    }

    public int GetAge(Calendar dateOfBirthCalendar)
    {
        return CalculatorService.CalculateAge(dateOfBirthCalendar);
    }

    public int GetMhr(int age)
    {
        return CalculatorService.GetMaximumHeartRate(age);
    }

    public List<Profile> GetData()
    {
        IDataSynchronizationManager manager= DataSynchronizationManager.GetInstance();
        IRepository<Profile> repository = manager.GetRepository(Profile.class);
        QueryAll.Criteria criteria = new QueryAll.Criteria();

        return repository.Matching(criteria);
    }
}
