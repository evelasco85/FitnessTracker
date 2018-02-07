package com.codeflowcrafter.PEAA.Interfaces;

import com.codeflowcrafter.PEAA.Interfaces.Registries.IMapperRegistry;
import com.codeflowcrafter.PEAA.Interfaces.Registries.IQueryObjectRegistry;
import com.codeflowcrafter.PEAA.Interfaces.Registries.IRepositoryRegistry;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapperConcrete;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aiko on 3/11/17.
 */

public interface IDataSynchronizationManager extends IMapperRegistry, IRepositoryRegistry, IQueryObjectRegistry
{
    <TEntity extends IDomainObject> void RegisterEntity(Class<TEntity> thisClass, IBaseMapperConcrete<TEntity> mapper, List<IBaseQueryObjectConcrete<TEntity>> queryList);
    <TEntity extends IDomainObject> HashMap<String, Field> GetFields(Class<TEntity> thisClass);
}
