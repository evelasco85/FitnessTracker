package com.codeflowcrafter.FitnessTracker;

import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IInvocationDelegates;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by aiko on 5/6/17.
 */

public class MapperInvocationDelegate implements IInvocationDelegates {
    private Hashtable _results;
    private IStaticLogEntryWrapper _slc;

    public MapperInvocationDelegate(IStaticLogEntryWrapper slc)
    {
        _slc = slc;
    }
    @Override
    public Hashtable GetResults() {
        return _results;
    }

    @Override
    public void SetResults(Hashtable results) {
        _results = results;
    }

    @Override
    public void SuccessfulInvocation(IDomainObject domainObject) {
        _slc
                .SetEvent("Successful Mapper Invocation")
                .EmitLog(Priority.Info, Status.Success, Convert(_results));
    }

    @Override
    public void FailedInvocationDelegate(IDomainObject domainObject) {

    }

    HashMap<String, String> Convert(Hashtable results)
    {
        HashMap<String, String> params = new HashMap<String, String>(results);

        return params;
    }
}
