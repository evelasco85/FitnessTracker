package com.codeflowcrafter.PEAA.Interfaces;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IInvocationDelegates;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;


/**
 * Created by aiko on 4/1/17.
 */

public interface IUnitOfWork {
    <TEntity extends IDomainObject> TEntity RegisterNew(TEntity entity, IInvocationDelegates invocationDelegates)
            throws NullPointerException;

    <TEntity extends IDomainObject> TEntity RegisterDirty(TEntity entity, IInvocationDelegates invocationDelegates)
            throws NullPointerException;

    <TEntity extends IDomainObject> TEntity RegisterRemoved(TEntity entity, IInvocationDelegates invocationDelegates)
            throws NullPointerException;

    void Commit(IUoWInvocationDelegates delegates);
    void ClearUnitOfWork();
    boolean PendingCommits();
}
