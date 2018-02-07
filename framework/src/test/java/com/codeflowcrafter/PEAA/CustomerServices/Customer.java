package com.codeflowcrafter.PEAA.CustomerServices;

import com.codeflowcrafter.PEAA.IdentityFieldAnnotation;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.DomainObject;

/**
 * Created by aiko on 3/5/17.
 */

public class Customer extends DomainObject {
    public Customer(IBaseMapper mapper)
    {
        super(mapper);
    }

    public Customer(IBaseMapper mapper, String number)
    {
        this(mapper);

        Number = number;
    }

    @IdentityFieldAnnotation
    public String Number;

    public String Name;
}
