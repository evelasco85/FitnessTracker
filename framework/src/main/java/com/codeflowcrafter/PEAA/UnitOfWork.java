package com.codeflowcrafter.PEAA;

import com.codeflowcrafter.PEAA.Interfaces.IUoWInvocationDelegates;
import com.codeflowcrafter.PEAA.Interfaces.IUnitOfWork;
import com.codeflowcrafter.PEAA.Interfaces.UnitOfWorkAction;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IBaseMapper;
import com.codeflowcrafter.PEAA.DataManipulation.BaseMapperInterfaces.IInvocationDelegates;
import com.codeflowcrafter.PEAA.Domain.Interfaces.IDomainObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by aiko on 4/1/17.
 */

public class UnitOfWork implements IUnitOfWork {
    private HashMap<UUID, IDomainObject> _insertionObjects = new HashMap<UUID, IDomainObject>();
    private HashMap<UUID, IDomainObject> _updatingObjects = new HashMap<UUID, IDomainObject>();
    private HashMap<UUID, IDomainObject> _deletionObjects = new HashMap<UUID, IDomainObject>();
    private HashMap<UUID, IInvocationDelegates> _invocationDelegates = new HashMap<UUID, IInvocationDelegates>();

    private boolean ContainsKey(HashMap<UUID, IDomainObject> domainDictionary, IDomainObject domainObject)
    {
        return domainDictionary.containsKey(domainObject.GetSystemId());
    }

    private void AddEntity(HashMap<UUID, IDomainObject> domainDictionary, IDomainObject domainObject, IInvocationDelegates invocationDelegates)
    {
        domainDictionary.put(domainObject.GetSystemId(), domainObject);
        _invocationDelegates.put(domainObject.GetSystemId(), invocationDelegates);
    }

    private void RemoveEntity(HashMap<UUID, IDomainObject> domainDictionary, IDomainObject domainObject)
    {
        if (ContainsKey(domainDictionary, domainObject)) {
            domainDictionary.remove(domainObject.GetSystemId());
            _invocationDelegates.remove(domainObject.GetSystemId());
        }
    }

    private <TEntity extends IDomainObject> void ValidateEntityPrerequisites(TEntity entity) throws NullPointerException
    {
        if (entity == null)
            throw new NullPointerException("'entity' parameter is required");

        if (entity.GetMapper() == null)
            throw new NullPointerException("A 'mapper' implementation is required for an entity to be observed");
    }

    public <TEntity extends IDomainObject> TEntity RegisterNew(TEntity entity, IInvocationDelegates invocationDelegates)
            throws NullPointerException
    {
        try {
            ValidateEntityPrerequisites(entity);

            if (ContainsKey(_updatingObjects, entity))
                throw new UnsupportedOperationException("'entity' already registered for update | [Operation Register: New]");

            if (ContainsKey(_deletionObjects, entity))
                throw new UnsupportedOperationException("'entity' already registered for deletion | [Operation Register: New]");

            if (ContainsKey(_insertionObjects, entity))
                return entity;

            AddEntity(_insertionObjects, entity, invocationDelegates);
        }
        catch (NullPointerException exception)
        {
            throw exception;
        }

        return entity;
    }

    public <TEntity extends IDomainObject> TEntity RegisterDirty(TEntity entity, IInvocationDelegates invocationDelegates)
            throws NullPointerException
    {
        try {
            ValidateEntityPrerequisites(entity);

            if (ContainsKey(_deletionObjects, entity))
                throw new UnsupportedOperationException(
                        "'entity' already registered for deletion | [Operation Register: Dirty]");

            if (ContainsKey(_insertionObjects, entity) || ContainsKey(_updatingObjects, entity))
                return entity;

            AddEntity(_updatingObjects, entity, invocationDelegates);
        }
        catch (NullPointerException exception)
        {
            throw exception;
        }

        return entity;
    }

    public <TEntity extends IDomainObject> TEntity RegisterRemoved(TEntity entity, IInvocationDelegates invocationDelegates)
            throws NullPointerException
    {
        try {
            ValidateEntityPrerequisites(entity);

            if (ContainsKey(_insertionObjects, entity) || ContainsKey(_updatingObjects, entity))
            {
                RemoveEntity(_insertionObjects, entity);
                RemoveEntity(_updatingObjects, entity);

                return entity;
            }

            if (ContainsKey(_deletionObjects, entity))
                return entity;

            AddEntity(_deletionObjects, entity, invocationDelegates);
        }
        catch (NullPointerException exception)
        {
            throw exception;
        }

        return entity;
    }

    public void Commit(IUoWInvocationDelegates delegates)
    {
        ;
        ApplyOperation(UnitOfWorkAction.Insert, new ArrayList<IDomainObject>(_insertionObjects.values()), delegates);
        ApplyOperation(UnitOfWorkAction.Update, new ArrayList<IDomainObject>(_updatingObjects.values()), delegates);
        ApplyOperation(UnitOfWorkAction.Delete, new ArrayList<IDomainObject>(_deletionObjects.values()), delegates);
        ClearUnitOfWork();
    }

    public void ClearUnitOfWork()
    {
        _insertionObjects.clear();
        _updatingObjects.clear();
        _deletionObjects.clear();
        _invocationDelegates.clear();
    }

    public boolean PendingCommits(){
        return (!_insertionObjects.isEmpty()) || (!_updatingObjects.isEmpty()) || (!_deletionObjects.isEmpty());
    }

    private void ApplyOperation(
            UnitOfWorkAction action, List<IDomainObject> affectedEntities,
            IUoWInvocationDelegates uoWInvocationDelegates
    )
    {
        for(int index = 0; index < affectedEntities.size(); ++index)
        {
            IDomainObject entity = affectedEntities.get(index);

            if (entity == null)
                continue;

            IBaseMapper mapper = entity.GetMapper();
            IInvocationDelegates invocation = _invocationDelegates.get(entity.GetSystemId());
            boolean success = false;

            if (invocation != null)
                invocation.SetResults(null);

            switch (action) {
                case Insert:
                    success = mapper.Insert(entity, invocation);
                    break;
                case Update:
                    success = mapper.Update(entity, invocation);
                    break;
                case Delete:
                    success = mapper.Delete(entity, invocation);
                    break;
            }

            if (invocation != null)
                uoWInvocationDelegates.SetResults(invocation.GetResults());

            if (success)
                uoWInvocationDelegates.SuccessfulUoWInvocationDelegate(entity, action);
            else
                uoWInvocationDelegates.FailedUoWInvocationDelegate(entity, action);
        }
    }
}
