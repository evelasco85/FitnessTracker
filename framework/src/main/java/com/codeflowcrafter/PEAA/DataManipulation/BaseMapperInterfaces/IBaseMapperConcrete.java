package com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces;

import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

/**
 * Created by aiko on 3/4/17.
 */

public interface IBaseMapperConcrete<TEntity extends IDomainObject> extends IBaseMapper {
    boolean ConcreteUpdate(TEntity entity, IInvocationDelegates invocationDelegates);
    boolean ConcreteInsert(TEntity entity, IInvocationDelegates invocationDelegates);
    boolean ConcreteDelete(TEntity entity, IInvocationDelegates invocationDelegates);
}