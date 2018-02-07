package com.codeflowcrafter.PEAA.Interfaces.Registries;

import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

/**
 * Created by aiko on 3/11/17.
 */

public interface IQueryObjectRegistry {
    <TEntity extends IDomainObject, TSearchInput> IBaseQueryObjectConcrete<TEntity> GetQueryBySearchCriteria(Class<TEntity> thisClass, Class<TSearchInput> thisSearch);
}
