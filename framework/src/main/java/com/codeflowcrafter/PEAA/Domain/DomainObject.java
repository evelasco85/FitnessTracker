package com.codeflowcrafter.PEAA.Domain;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObject;
import com.codeflowcrafter.PEAA.Domain.Enums.InstantiationType;
import com.codeflowcrafter.PEAA.Domain.Interfaces.ISystemManipulation;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

import java.util.UUID;

/**
 * Created by aiko on 3/4/17.
 */

public class DomainObject implements IDomainObject, ISystemManipulation {
    private IBaseMapper _mapper;
    private UUID _systemId;
    private IBaseQueryObject _queryObject;

    public DomainObject(IBaseMapper mapper)
    {
        _mapper = mapper;
        _systemId = UUID.randomUUID();
    }

    @Override
    public UUID GetSystemId() {
        return _systemId;
    }

    @Override
    public IBaseMapper GetMapper() {
        return _mapper;
    }

    @Override
    public InstantiationType GetInstantiation() {
        if(_queryObject == null)
            return InstantiationType.New;
        else
            return InstantiationType.Loaded;
    }

    @Override
    public void SetQueryObject(IBaseQueryObject queryObject) {
        _queryObject = queryObject;
    }

    @Override
    public void SetMapper(IBaseMapper mapper) {
        _mapper = mapper;
    }
}
