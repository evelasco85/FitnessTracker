package com.codeflowcrafter.LogManagement.Interfaces;

/**
 * Created by aiko on 5/1/17.
 */
public interface ILogManager extends ILogCreator
{
    void SetEmitter(ILogEmitter emitter);
}
