package com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces;

import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

import java.util.Hashtable;

/**
 * Created by aiko on 3/4/17.
 */

public interface IInvocationDelegates {
    Hashtable GetResults();

    void SetResults(Hashtable results);

    void SuccessfulInvocation(IDomainObject domainObject);

    void FailedInvocationDelegate(IDomainObject domainObject);
}
