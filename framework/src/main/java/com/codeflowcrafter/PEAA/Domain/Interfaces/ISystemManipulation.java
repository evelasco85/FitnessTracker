package com.codeflowcrafter.PEAA.Domain.Interfaces;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObject;

/**
 * Created by aiko on 3/4/17.
 */

public interface ISystemManipulation {
    void SetQueryObject(IBaseQueryObject queryObject);
    void SetMapper(IBaseMapper mapper);
}
