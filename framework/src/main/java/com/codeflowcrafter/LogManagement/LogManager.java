package com.codeflowcrafter.LogManagement;

import com.codeflowcrafter.LogManagement.Interfaces.ILogEmitter;
import com.codeflowcrafter.LogManagement.Interfaces.ILogEntry;
import com.codeflowcrafter.LogManagement.Interfaces.ILogManager;

import java.util.Calendar;
import java.util.TimeZone;

public class LogManager implements ILogManager {
    private static ILogManager s_instance = new LogManager();

    private ILogEmitter _emitter;

    private LogManager()
    {
    }

    public static ILogManager GetInstance()
    {
        return s_instance;
    }

    public void SetEmitter(ILogEmitter emitter)
    {
        if(_emitter != null)  throw new IllegalArgumentException("emitter already setup");      //Set once only to avoid threading complications

        _emitter = emitter;
    }

    public ILogEntry CreateLogEntry(Priority priority)
    {
        ILogEntry log = new LogEntry(
                TimeZone.getDefault(),
                Calendar.getInstance().getTime(),
                priority
        );

        log.SetStatus(Status.None);

        return log;
    }

    public void EmitLog(ILogEntry log)
    {
        if(_emitter != null) _emitter.OnLogEmit(log);
    }
}
