package com.codeflowcrafter.PEAA;

import com.codeflowcrafter.PEAA.Interfaces.ITransactionScriptConcrete;
import com.codeflowcrafter.PEAA.Interfaces.IUnitOfWork;
import com.codeflowcrafter.PEAA.Interfaces.Registries.IRepositoryRegistry;
import com.codeflowcrafter.PEAA.Interfaces.Registries.IMapperRegistry;

/**
 * Created by aiko on 4/1/17.
 */

public abstract class TransactionScript<TInput, TOutput> implements ITransactionScriptConcrete<TInput, TOutput> {
    private TInput _input;

        public TInput GetInput()
        {
        return _input;
        }

        public void SetInput(TInput input)
        {
            _input = input;
        }

    private TOutput _output;

    public TOutput GetOutput()
    {
     return _output;
    }

    public void SetOutput(TOutput output)
    {
        _output = output;
    }

    private IRepositoryRegistry _repositoryRegistry;
    private IMapperRegistry _mapperRegistry;

    public IRepositoryRegistry GetRepositoryRegistry(){return _repositoryRegistry;}
    public IMapperRegistry GetMapperRegistry(){return _mapperRegistry;}

    protected TransactionScript(IRepositoryRegistry repositoryRegistry, IMapperRegistry mapperRegistry)
    {
        _repositoryRegistry = repositoryRegistry;
        _mapperRegistry = mapperRegistry;
    }

    public void PreExecuteBody(){}

    public abstract TOutput ExecutionBody();

    public void PostExecuteBody(){}

    public void RunScript()
    {
        PreExecuteBody();

        _output = ExecutionBody();

        PostExecuteBody();
    }

    public IUnitOfWork CreateUnitOfWork()
    {
        return new UnitOfWork();
    }
}
