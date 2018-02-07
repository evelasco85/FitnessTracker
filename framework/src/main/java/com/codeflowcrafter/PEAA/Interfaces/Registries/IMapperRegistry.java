package com.codeflowcrafter.PEAA.Interfaces.Registries;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapperConcrete;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

/**
 * Created by aiko on 3/11/17.
 */

public interface IMapperRegistry {
    <TEntity extends IDomainObject> IBaseMapperConcrete<TEntity> GetMapper(Class<TEntity> thisClass);
}
