package com.codeflowcrafter.PEAA;

import com.codeflowcrafter.PEAA.CustomerServices.Customer;
import com.codeflowcrafter.PEAA.CustomerServices.CustomerMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;
import com.codeflowcrafter.PEAA.Interfaces.IUnitOfWork;

import java.util.ArrayList;
import java.util.List;

import com.codeflowcrafter.PEAA.CustomerServices.CustomerInvocationDelegates;
import com.codeflowcrafter.PEAA.CustomerServices.GetCustomerByCivilStatusQuery;
import com.codeflowcrafter.PEAA.CustomerServices.GetCustomerByIdQuery;
import com.codeflowcrafter.PEAA.CustomerServices.CustomerUoWDelegate;

import static org.junit.Assert.assertEquals;

/**
 * Created by aiko on 4/8/17.
 */

public class UnitOfWorkTests {
    private IDataSynchronizationManager _manager;
    private CustomerMapper _mapper = new CustomerMapper();

    @org.junit.Before
    public void Initialize()
    {
        _manager = DataSynchronizationManager.GetInstance();

        List<IBaseQueryObjectConcrete<Customer>> customerQueryObjects = new ArrayList<IBaseQueryObjectConcrete<Customer>>();

        customerQueryObjects.add(new GetCustomerByIdQuery());
        customerQueryObjects.add(new GetCustomerByCivilStatusQuery());
        _manager.RegisterEntity(Customer.class, new CustomerMapper(), customerQueryObjects);
    }

    @org.junit.Test
    public void TestCommit_SimpleInsertion()
    {
        IRepository<Customer> repository = _manager.GetRepository(Customer.class);
        GetCustomerByCivilStatusQuery.Criteria criteriaByStatus = new GetCustomerByCivilStatusQuery.Criteria(GetCustomerByCivilStatusQuery.CivilStatus.Married);
        GetCustomerByIdQuery.Criteria criteriaById = new GetCustomerByIdQuery.Criteria(2);
        List<Customer> customerResults = new ArrayList<Customer>();
        IUnitOfWork uow = new UnitOfWork();

        customerResults.addAll(repository.Matching(criteriaByStatus));
        customerResults.addAll(repository.Matching(criteriaById));


        IBaseMapper mapper = DataSynchronizationManager.GetInstance().GetMapper(Customer.class);
        Customer customer1 = new Customer(mapper, "1");
        Customer customer2 = new Customer(mapper, "2");
        Customer customer3 = new Customer(mapper, "3");

        CustomerInvocationDelegates delegates = new CustomerInvocationDelegates();

        try
        {
            //Sequence of observation affects commit order
            uow.RegisterNew(customer1, delegates);
            uow.RegisterNew(customer3, delegates);
            uow.RegisterNew(customer2, delegates);
        }
        catch (NullPointerException ex)
        {
        }

        List<String> sequenceDescription = new ArrayList<String>();

        uow.Commit(new CustomerUoWDelegate(sequenceDescription));


        assertEquals(true, sequenceDescription.contains("1=Insert=com.codeflowcrafter.PEAA.CustomerServices.Customer"));
        assertEquals(true, sequenceDescription.contains("3=Insert=com.codeflowcrafter.PEAA.CustomerServices.Customer"));
        assertEquals(true, sequenceDescription.contains("2=Insert=com.codeflowcrafter.PEAA.CustomerServices.Customer"));
    }
}
