package com.codeflowcrafter.LogManagement.Interfaces;

import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;

import java.util.HashMap;

public interface IStaticLogEntryWrapper extends ILogCreator, IStaticLogEntryWrapper_Emitter{
    IStaticLogEntryWrapper SetSystem(String system);
    IStaticLogEntryWrapper SetApplication(String application);
    IStaticLogEntryWrapper SetComponent(String component);
    IStaticLogEntryWrapper_Emitter SetEvent(String event);

    ILogEntry CreateLogEntry(Priority priority, String description);
}
