package com.codeflowcrafter.PEAA.Interfaces.Registries;

import com.codeflowcrafter.PEAA.Interfaces.IRepository;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

/**
 * Created by aiko on 3/11/17.
 */

public interface IRepositoryRegistry {
    <TEntity extends IDomainObject> IRepository<TEntity> GetRepository(Class<TEntity> thisClass);
}
