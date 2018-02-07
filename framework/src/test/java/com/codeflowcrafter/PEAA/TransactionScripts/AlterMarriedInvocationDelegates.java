package com.codeflowcrafter.PEAA.TransactionScripts;

import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;
import com.codeflowcrafter.PEAA.Interfaces.UnitOfWorkAction;
import com.codeflowcrafter.PEAA.Interfaces.IUoWInvocationDelegates;

import java.util.Hashtable;

import com.codeflowcrafter.PEAA.CustomerServices.Customer;

/**
 * Created by aiko on 4/8/17.
 */

public class AlterMarriedInvocationDelegates implements IUoWInvocationDelegates {
    Hashtable _results;
    AlterMarriedStatusIntoSingleTS.TransactionResult _transactionResult;

    public AlterMarriedInvocationDelegates(AlterMarriedStatusIntoSingleTS.TransactionResult transactionResult) {
        _transactionResult = transactionResult;
    }

    public Hashtable GetResults() {
        return _results;
    }

    public void SetResults(Hashtable results) {
        _results = results;
    }

    public void SuccessfulUoWInvocationDelegate(IDomainObject domainObject, UnitOfWorkAction action) {
        _transactionResult.SuccessfullyAlteredCustomers.add((Customer) domainObject);
    }

    public void FailedUoWInvocationDelegate(IDomainObject domainObject, UnitOfWorkAction action) {

    }
}
