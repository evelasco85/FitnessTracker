package com.codeflowcrafter.PEAA.Interfaces.Registries;

import com.codeflowcrafter.PEAA.Interfaces.IIdentityMapConcrete;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

/**
 * Created by aiko on 3/11/17.
 */

public interface IIdentityMapRegistry {
    <TEntity extends IDomainObject> IIdentityMapConcrete<TEntity> GetIdentityMap();
}
