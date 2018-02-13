package com.codeflowcrafter.FitnessTracker.Base.MVP;

import android.net.Uri;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IInvocationDelegates;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

import java.util.List;

/**
 * Created by enric on 05/02/2018.
 */

public abstract class Crud_Presenter<
        TEntity extends IDomainObject,
        TIRequests extends Crud_IRequests<TEntity>,
        TIView extends Crud_IView<TEntity, TIRequests>
        >
        implements Crud_IRequests<TEntity> {
    private TIView _view;
    private IInvocationDelegates _invocationDelegate;

    public Crud_Presenter(TIView view,
                          IInvocationDelegates invocationDelegate)
    {
        _view = view;
        _invocationDelegate = invocationDelegate;
    }

    public int LoadEntities(List<TEntity> entityList)
    {
        int entityCount = (entityList == null) ? 0 : entityList.size();

        _view.OnLoadEntitiesViaLoaderCompletion(entityList);

        return entityCount;
    }

    public void Prompt_AddEntry()
    {
        _view.OnPromptExecution_AddEntry();
    }

    public void CancelEntry(){}

    public void Add(TEntity entity)
    {
        IBaseMapper mapper = entity.GetMapper();

        mapper.Insert(entity, _invocationDelegate);
    }

    public void Update(TEntity entity)
    {
        IBaseMapper mapper = entity.GetMapper();

        mapper.Update(entity, _invocationDelegate);
    }

    public void Prompt_EditEntry(TEntity entity) {
        _view.OnPromptExecution_EditEntry(entity);
    }

    public void Prompt_DeleteEntry(TEntity entity)
    {
        _view.OnPromptExecution_DeleteEntry(entity);
    }

    public void Delete(TEntity entity)
    {
        IBaseMapper mapper = entity.GetMapper();

        mapper.Delete(entity, _invocationDelegate);
    }

    public void Prompt_Detail(TEntity entity)
    {
        _view.OnPromptExecution_Detail(entity);
    }

    public abstract Uri GetContentUri();
}
