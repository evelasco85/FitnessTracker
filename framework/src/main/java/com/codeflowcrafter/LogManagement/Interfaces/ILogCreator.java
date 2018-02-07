package com.codeflowcrafter.LogManagement.Interfaces;

import com.codeflowcrafter.LogManagement.Priority;

/**
 * Created by aiko on 4/30/17.
 */

public interface ILogCreator {
    ILogEntry CreateLogEntry(Priority priority);
    void EmitLog(ILogEntry log);
}
