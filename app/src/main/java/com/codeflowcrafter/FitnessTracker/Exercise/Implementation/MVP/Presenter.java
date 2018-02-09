package com.codeflowcrafter.FitnessTracker.Exercise.Implementation.MVP;

import android.content.CursorLoader;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_Presenter;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerApplication;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.MapperInvocationDelegate;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;

import java.util.List;

/**
 * Created by enric on 08/02/2018.
 */

public class Presenter extends Crud_Presenter<Exercise, IRequests, IView>
        implements IRequests{
    private IView _view;
    private final static IStaticLogEntryWrapper _slc = FitnessTrackerApplication.GetInstance().CreateSLC();

    public Presenter(IView view, IEntityTranslator<Exercise> translator)
    {
        super(view,
                translator,
                new MapperInvocationDelegate(_slc));

        _slc.SetComponent("Exercise");
        view.SetViewRequest(this);

        _view = view;
    }

    @Override
    public Uri GetContentUri() {
        return FitnessTrackerContentProviders
                .GetInstance()
                .GetExerciseProvider()
                .GetContentUri();
    }

    @Override
    public int LoadEntities(List<Exercise> entityList)
    {
        int size = super.LoadEntities(entityList);

        _slc
                .SetEvent(String.format("Loaded exercise count %d", size))
                .EmitLog(Priority.Info, Status.Success);

        return size;
    }

    @Override
    public void Prompt_AddEntry()
    {
        super.Prompt_AddEntry();
        _slc
                .SetEvent("Open Exercise Entry")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void CancelEntry(){
        _slc
                .SetEvent("Cancel exercise entry window")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Add(Exercise entity)
    {
        super.Add(entity);
        _slc
                .SetEvent("Exercise Added")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Update(Exercise entity)
    {
        super.Update(entity);
        _slc
                .SetEvent(String.format("Updated exercise id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_EditEntry(Exercise entity) {
        super.Prompt_EditEntry(entity);
        _slc
                .SetEvent("Open exercise editing")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Delete(Exercise entity)
    {
        super.Delete(entity);
        _slc
                .SetEvent(String.format("Deleted exercise id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_Detail(Exercise entity)
    {
        super.Prompt_Detail(entity);
        _slc
                .SetEvent(String.format("Showing details of exercise id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }
}
