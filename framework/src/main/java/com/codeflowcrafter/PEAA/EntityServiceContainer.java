package com.codeflowcrafter.PEAA;

import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapperConcrete;
import com.codeflowcrafter.PEAA.DataManipulation.BaseQueryObjectInterfaces.IBaseQueryObjectConcrete;
import com.codeflowcrafter.PEAA.Interfaces.IIdentityMapConcrete;
import com.codeflowcrafter.PEAA.Interfaces.IRepository;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by aiko on 2/11/17.
 */

public class EntityServiceContainer<TEntity extends IDomainObject> {
    public IBaseMapperConcrete<TEntity> Mapper;
    public IIdentityMapConcrete<TEntity> IdentityMap;
    public IRepository<TEntity> Repository;
    public HashMap<String, IBaseQueryObjectConcrete<TEntity>> QueryDictionary;
    public HashMap<String, Field> PrimitiveFields;
}

