package com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces;

import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

/**
 * Created by aiko on 3/4/17.
 */

public interface IBaseMapper
{
    String GetEntityTypeName();
    boolean Update(IDomainObject entity, IInvocationDelegates invocationDelegates);
    boolean Insert(IDomainObject entity, IInvocationDelegates invocationDelegates);
    boolean Delete(IDomainObject entity, IInvocationDelegates invocationDelegates);
}
