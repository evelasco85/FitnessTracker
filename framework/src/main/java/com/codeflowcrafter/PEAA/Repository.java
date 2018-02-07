package com.codeflowcrafter.PEAA;

import com.codeflowcrafter.PEAA.Domain.Interfaces.ISystemManipulation;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapperConcrete;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObject;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aiko on 4/1/17.
 */

public class Repository <TEntity extends IDomainObject> implements IRepository<TEntity>
{
    private Class<TEntity> _class;
    private IDataSynchronizationManager _manager;

    public Repository(Class<TEntity> thisClass, IDataSynchronizationManager manager)
    {
        _class = thisClass;
        _manager = manager;
    }

    private void ApplyDomainObjectSettings(List<TEntity> newResult, IBaseQueryObject query)
    {
        if ((newResult == null) || (newResult.size() < 1))
            return;

        IBaseMapperConcrete<TEntity> mapper = DataSynchronizationManager.GetInstance().GetMapper(_class);

        for(int index = 0; index < newResult.size(); ++index)
        {
            TEntity entity = newResult.get(index);

            ((ISystemManipulation)entity).SetQueryObject(query);
            ((ISystemManipulation)entity).SetMapper(mapper);
        }
    }

    private List<TEntity> Matching(IBaseQueryObjectConcrete<TEntity> query) {
        List<TEntity> results = new ArrayList<TEntity>();

        if (query != null)
            results.addAll(query.Execute());

        ApplyDomainObjectSettings(results, query);

        return results;
    }

    public <TSearchInput> List<TEntity> Matching(TSearchInput criteria)
    {
        IBaseQueryObjectConcrete<TEntity> query = _manager.GetQueryBySearchCriteria(_class, criteria.getClass());

        query.SetSearchInputObject(criteria);

        return Matching(query);
    }
}
