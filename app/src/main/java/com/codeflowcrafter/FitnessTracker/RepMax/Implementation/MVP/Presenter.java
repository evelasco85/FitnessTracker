package com.codeflowcrafter.FitnessTracker.RepMax.Implementation.MVP;

import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_Presenter;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.QueryObjects.QueryAllStrengthening;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerApplication;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.MapperInvocationDelegate;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.Domain.QueryObjects.QueryByProfileId;
import com.codeflowcrafter.FitnessTracker.RepMax.Implementation.Domain.RepMax;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;

import java.util.List;

/**
 * Created by enric on 22/02/2018.
 */

public class Presenter extends Crud_Presenter<RepMax, IRequests, IView>
        implements IRequests {
    private IView _view;
    private final static IStaticLogEntryWrapper _slc = FitnessTrackerApplication.GetInstance().CreateSLC();

    public Presenter(IView view) {
        super(view,
                new MapperInvocationDelegate(_slc));

        _slc.SetComponent("Exercise Heart Rate");
        view.SetViewRequest(this);

        _view = view;
    }

    @Override
    public Uri GetContentUri() {
        return FitnessTrackerContentProviders
                .GetInstance()
                .GetRepMaxProvider()
                .GetContentUri();
    }

    @Override
    public int LoadEntities(List<RepMax> entityList) {
        int size = super.LoadEntities(entityList);

        _slc
                .SetEvent(String.format("Loaded repmax count %d", size))
                .EmitLog(Priority.Info, Status.Success);

        return size;
    }

    @Override
    public void Prompt_AddEntry() {
        super.Prompt_AddEntry();
        _slc
                .SetEvent("Open Repmax Entry")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void CancelEntry() {
        _slc
                .SetEvent("Cancel repmax entry window")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Add(RepMax entity) {
        super.Add(entity);
        _slc
                .SetEvent("Repmax Added")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Update(RepMax entity) {
        super.Update(entity);
        _slc
                .SetEvent(String.format("Updated repmax id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_EditEntry(RepMax entity) {
        super.Prompt_EditEntry(entity);
        _slc
                .SetEvent("Open repmax editing")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Delete(RepMax entity) {
        super.Delete(entity);
        _slc
                .SetEvent(String.format("Deleted repmax id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_Detail(RepMax entity) {
        super.Prompt_Detail(entity);
        _slc
                .SetEvent(String.format("Showing details of repmax id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    public List<RepMax> GetData(int profileId) {
        IDataSynchronizationManager manager = DataSynchronizationManager.GetInstance();
        IRepository<RepMax> repository = manager.GetRepository(RepMax.class);
        QueryByProfileId.Criteria criteria = new QueryByProfileId.Criteria(profileId);

        return repository.Matching(criteria);
    }

    public List<Exercise> GetStrengtheningExercisesData() {
        IDataSynchronizationManager manager = DataSynchronizationManager.GetInstance();
        IRepository<Exercise> repository = manager.GetRepository(Exercise.class);
        QueryAllStrengthening.Criteria criteria = new QueryAllStrengthening.Criteria();

        return repository.Matching(criteria);
    }
}