package com.codeflowcrafter.PEAA.ReceivableServices;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;
import com.codeflowcrafter.PEAA.IdentityFieldAnnotation;

/**
 * Created by aiko on 4/8/17.
 */

public class AccountReceivable extends DomainObject {

    @IdentityFieldAnnotation
    public String Number;

    public String CustomerNumber ;

    public String Description;

    public AccountReceivable(String customerNumber, String number, IBaseMapper mapper)
    {
        super(mapper);

        CustomerNumber = customerNumber;
        Number = number;
    }
}
