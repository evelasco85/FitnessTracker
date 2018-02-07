package com.codeflowcrafter.LogManagement;

import com.codeflowcrafter.LogManagement.Interfaces.ILogEmitter;
import com.codeflowcrafter.LogManagement.Interfaces.ILogEntry;

/**
 * Created by aiko on 4/29/17.
 */

public class GlobalLogExtender implements ILogEmitter {

    private ILogEntry _outputLog;

    public ILogEntry GetOutputLog()
    {
        return _outputLog;
    }

    @Override
    public void OnLogEmit(ILogEntry log)
    {
        _outputLog = log;
    }
}
