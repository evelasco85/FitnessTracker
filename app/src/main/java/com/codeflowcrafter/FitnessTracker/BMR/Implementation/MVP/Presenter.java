package com.codeflowcrafter.FitnessTracker.BMR.Implementation.MVP;

import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_Presenter;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerApplication;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.MapperInvocationDelegate;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.Domain.BasalMetabolicRate;
import com.codeflowcrafter.FitnessTracker.BMR.Implementation.Domain.QueryObjects.QueryByProfileId;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;

import java.util.List;

/**
 * Created by enric on 13/02/2018.
 */

public class Presenter extends Crud_Presenter<BasalMetabolicRate, IRequests, IView>
        implements IRequests {
    private IView _view;
    private final static IStaticLogEntryWrapper _slc = FitnessTrackerApplication.GetInstance().CreateSLC();

    public Presenter(IView view)
    {
        super(view, new MapperInvocationDelegate(_slc));

        _slc.SetComponent("BMR");
        view.SetViewRequest(this);

        _view = view;
    }

    @Override
    public Uri GetContentUri() {
        return FitnessTrackerContentProviders
                .GetInstance()
                .GetBmrProvider()
                .GetContentUri();
    }

    @Override
    public int LoadEntities(List<BasalMetabolicRate> entityList)
    {
        int size = super.LoadEntities(entityList);

        _slc
                .SetEvent(String.format("Loaded BMR count %d", size))
                .EmitLog(Priority.Info, Status.Success);

        return size;
    }

    @Override
    public void Prompt_AddEntry()
    {
        super.Prompt_AddEntry();
        _slc
                .SetEvent("Open BMR Entry")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void CancelEntry(){
        _slc
                .SetEvent("Cancel BMR entry window")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Add(BasalMetabolicRate entity)
    {
        super.Add(entity);
        _slc
                .SetEvent("BMR Added")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Update(BasalMetabolicRate entity)
    {
        super.Update(entity);
        _slc
                .SetEvent(String.format("Updated BMR id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_EditEntry(BasalMetabolicRate entity) {
        super.Prompt_EditEntry(entity);
        _slc
                .SetEvent("Open BMR editing")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Delete(BasalMetabolicRate entity)
    {
        super.Delete(entity);
        _slc
                .SetEvent(String.format("Deleted BMR id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_Detail(BasalMetabolicRate entity)
    {
        super.Prompt_Detail(entity);
        _slc
                .SetEvent(String.format("Showing details of BMR id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }



    public List<BasalMetabolicRate> GetData(int profileId)
    {
        IDataSynchronizationManager manager= DataSynchronizationManager.GetInstance();
        IRepository<BasalMetabolicRate> repository = manager.GetRepository(BasalMetabolicRate.class);
        QueryByProfileId.Criteria criteria = new QueryByProfileId.Criteria(profileId);

        return repository.Matching(criteria);
    }
}