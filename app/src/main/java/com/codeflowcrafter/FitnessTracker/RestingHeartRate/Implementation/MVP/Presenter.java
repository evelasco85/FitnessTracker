package com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.MVP;

import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_Presenter;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerApplication;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.MapperInvocationDelegate;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.QueryObjects.QueryByProfileId;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;

import java.util.List;

/**
 * Created by enric on 11/02/2018.
 */

public class Presenter extends Crud_Presenter<RestingHeartRate, IRequests, IView>
        implements IRequests {
    private IView _view;
    private final static IStaticLogEntryWrapper _slc = FitnessTrackerApplication.GetInstance().CreateSLC();

    public Presenter(IView view)
    {
        super(view,
                new MapperInvocationDelegate(_slc));

        _slc.SetComponent("Resting Heart Rate");
        view.SetViewRequest(this);

        _view = view;
    }

    @Override
    public Uri GetContentUri() {
        return FitnessTrackerContentProviders
                .GetInstance()
                .GetRhrProvider()
                .GetContentUri();
    }

    @Override
    public int LoadEntities(List<RestingHeartRate> entityList)
    {
        int size = super.LoadEntities(entityList);

        _slc
                .SetEvent(String.format("Loaded resting heart rate count %d", size))
                .EmitLog(Priority.Info, Status.Success);

        return size;
    }

    @Override
    public void Prompt_AddEntry()
    {
        super.Prompt_AddEntry();
        _slc
                .SetEvent("Open resting heart rate Entry")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void CancelEntry(){
        _slc
                .SetEvent("Cancel resting heart rate entry window")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Add(RestingHeartRate entity)
    {
        super.Add(entity);
        _slc
                .SetEvent("Resting heart rate Added")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Update(RestingHeartRate entity)
    {
        super.Update(entity);
        _slc
                .SetEvent(String.format("Updated resting heart rate id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_EditEntry(RestingHeartRate entity) {
        super.Prompt_EditEntry(entity);
        _slc
                .SetEvent("Open resting heart rate editing")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Delete(RestingHeartRate entity)
    {
        super.Delete(entity);
        _slc
                .SetEvent(String.format("Deleted resting heart rate id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_Detail(RestingHeartRate entity)
    {
        super.Prompt_Detail(entity);
        _slc
                .SetEvent(String.format("Showing details of resting heart rate id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    public int GetMhr(int age)
    {
        return CalculatorService.GetMaximumHeartRate(age);
    }

    public List<RestingHeartRate> GetData(int profileId)
    {
        IDataSynchronizationManager manager= DataSynchronizationManager.GetInstance();
        IRepository<RestingHeartRate> repository = manager.GetRepository(RestingHeartRate.class);
        QueryByProfileId.Criteria criteria = new QueryByProfileId.Criteria(profileId);

        return repository.Matching(criteria);
    }
}