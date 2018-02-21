package com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.MVP;

import android.net.Uri;
import android.text.TextUtils;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_Presenter;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.Exercise;
import com.codeflowcrafter.FitnessTracker.Exercise.Implementation.Domain.QueryObjects.QueryAll;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain.ExerciseHeartRate;
import com.codeflowcrafter.FitnessTracker.ExerciseHeartRate.Implementation.Domain.QueryObjects.QueryByProfileId;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerApplication;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.MapperInvocationDelegate;
import com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.RestingHeartRate;
import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;
import com.codeflowcrafter.FitnessTracker.Shared.IntensityOfExercise;
import com.codeflowcrafter.FitnessTracker.Shared.IntensityOfExerciseService;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;

import java.util.List;

/**
 * Created by enric on 19/02/2018.
 */

public class Presenter extends Crud_Presenter<ExerciseHeartRate, IRequests, IView>
        implements IRequests {
    private IView _view;
    private final static IStaticLogEntryWrapper _slc = FitnessTrackerApplication.GetInstance().CreateSLC();

    public Presenter(IView view)
    {
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
                .GetEhrProvider()
                .GetContentUri();
    }

    @Override
    public int LoadEntities(List<ExerciseHeartRate> entityList)
    {
        int size = super.LoadEntities(entityList);

        _slc
                .SetEvent(String.format("Loaded exercise heart rate count %d", size))
                .EmitLog(Priority.Info, Status.Success);

        return size;
    }

    @Override
    public void Prompt_AddEntry()
    {
        super.Prompt_AddEntry();
        _slc
                .SetEvent("Open exercise heart rate Entry")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void CancelEntry(){
        _slc
                .SetEvent("Cancel exercise heart rate entry window")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Add(ExerciseHeartRate entity)
    {
        super.Add(entity);
        _slc
                .SetEvent("Exercise heart rate Added")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Update(ExerciseHeartRate entity)
    {
        super.Update(entity);
        _slc
                .SetEvent(String.format("Updated exercise heart rate id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_EditEntry(ExerciseHeartRate entity) {
        super.Prompt_EditEntry(entity);
        _slc
                .SetEvent("Open exercise heart rate editing")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Delete(ExerciseHeartRate entity)
    {
        super.Delete(entity);
        _slc
                .SetEvent(String.format("Deleted exercise heart rate id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_Detail(ExerciseHeartRate entity)
    {
        super.Prompt_Detail(entity);
        _slc
                .SetEvent(String.format("Showing details of exercise heart rate id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    public List<ExerciseHeartRate> GetData(int profileId)
    {
        IDataSynchronizationManager manager= DataSynchronizationManager.GetInstance();
        IRepository<ExerciseHeartRate> repository = manager.GetRepository(ExerciseHeartRate.class);
        QueryByProfileId.Criteria criteria = new QueryByProfileId.Criteria(profileId);

        return repository.Matching(criteria);
    }

    public int GetMhr(int age)
    {
        return CalculatorService.GetMaximumHeartRate(age);
    }

    public List<Exercise> GetExercisesData()
    {
        IDataSynchronizationManager manager= DataSynchronizationManager.GetInstance();
        IRepository<Exercise> repository = manager.GetRepository(Exercise.class);
        QueryAll.Criteria criteria = new QueryAll.Criteria();

        return repository.Matching(criteria);
    }

    public RestingHeartRate GetLatestRestingHeartRate(int profileId)
    {
        IDataSynchronizationManager manager= DataSynchronizationManager.GetInstance();
        IRepository<RestingHeartRate> repository = manager.GetRepository(RestingHeartRate.class);
        com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.QueryObjects.QueryByProfileId.Criteria criteria = new com.codeflowcrafter.FitnessTracker.RestingHeartRate.Implementation.Domain.QueryObjects.QueryByProfileId.Criteria(profileId);

        List<RestingHeartRate> rhrList = repository.Matching(criteria);

        if((rhrList == null) || (rhrList.isEmpty())) return null;

        return rhrList.get(0);
    }

    public IntensityOfExercise GetIntensityOfExercise(List<Exercise> exercises, String exerciseName)
    {
        IntensityOfExercise intensity = null;

        if((exercises == null) || (exercises.isEmpty()) || TextUtils.isEmpty(exerciseName))
            return intensity;

        for(int index = 0; index < exercises.size(); index++)
        {
            Exercise exercise = exercises.get(index);

            if(exercise == null) continue;

            if(exerciseName.equals(exercise.GetName()))
            {
                intensity = IntensityOfExerciseService
                        .GetInstance()
                        .GetIntensityByDescription(exercise.GetIntensity());
                break;
            }
        }

        return intensity;
    }

    public String GetZoneRange(
            int maximumHeartRate, int restingHeartRate,
            IntensityOfExercise intensity)
    {
        if(intensity == null) return "N/A";

        int lowerLimit = CalculatorService.CalculateZonedHeartRate(
                maximumHeartRate,
                restingHeartRate,
                intensity.GetLowerPercentLimit()
        );
        int upperLimit = CalculatorService.CalculateZonedHeartRate(
                maximumHeartRate,
                restingHeartRate,
                intensity.GetUpperPercentLimit()
        );

        return String.format("%d - %d", lowerLimit, upperLimit);
    }
}