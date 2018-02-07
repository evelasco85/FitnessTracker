package com.codeflowcrafter.PEAA.CustomerServices;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IInvocationDelegates;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

import java.util.Hashtable;

/**
 * Created by aiko on 4/8/17.
 */

public class CustomerInvocationDelegates implements IInvocationDelegates {
    Hashtable _results;

    public Hashtable GetResults() {
        return _results;
    }

    public void SetResults(Hashtable results) {
        _results = results;
    }

    public void SuccessfulInvocation(IDomainObject domainObject) {

    }

    public void FailedInvocationDelegate(IDomainObject domainObject) {

    }
}
