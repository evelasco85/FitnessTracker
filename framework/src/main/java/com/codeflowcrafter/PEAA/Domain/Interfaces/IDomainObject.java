package com.codeflowcrafter.PEAA.Domain.Interfaces;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.Domain.Enums.InstantiationType;

import java.util.UUID;

/**
 * Created by aiko on 2/11/17.
 */

public interface IDomainObject {
    UUID GetSystemId();
    IBaseMapper GetMapper();
    InstantiationType GetInstantiation();
}


