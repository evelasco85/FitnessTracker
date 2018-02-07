package com.codeflowcrafter.PEAA.Mapping;

import com.codeflowcrafter.PEAA.CustomerServices.Customer;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.PEAA.Domain.ForeignKeyMapping;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;

import java.util.List;

import com.codeflowcrafter.PEAA.ReceivableServices.AccountReceivable;
import com.codeflowcrafter.PEAA.ReceivableServices.GetAccountReceivablesByCustomerId;

/**
 * Created by aiko on 4/8/17.
 */

public class FK_Customer_AccountReceivable extends ForeignKeyMapping<Customer, AccountReceivable> {
    private IDataSynchronizationManager _manager;

    public FK_Customer_AccountReceivable()
    {
        _manager = DataSynchronizationManager.GetInstance();
    }

    @Override
    public List<AccountReceivable> RetrieveForeignKeyEntities(Customer parent) {
        IRepository<AccountReceivable> repository = _manager.GetRepository(AccountReceivable.class);
        GetAccountReceivablesByCustomerId.Criteria criteria = new GetAccountReceivablesByCustomerId.Criteria(parent.Number);

        return repository.Matching(criteria);
    }
}
