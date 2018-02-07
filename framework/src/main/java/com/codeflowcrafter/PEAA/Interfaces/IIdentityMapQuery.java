package com.codeflowcrafter.PEAA.Interfaces;

import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

/**
 * Created by aiko on 3/4/17.
 */

public interface IIdentityMapQuery <TEntity extends IDomainObject> {
    <TEntityPropertyType> IIdentityMapQuery<TEntity> SetFilter(String keyToSearch, Object keyValue)
            throws NoSuchFieldException;
    TEntity GetEntity();
}