package com.codeflowcrafter.LogManagement;

import com.codeflowcrafter.LogManagement.Interfaces.ILogEntry;
import com.codeflowcrafter.LogManagement.Interfaces.ILogManager;

import static org.junit.Assert.assertEquals;

/**
 * Created by aiko on 4/29/17.
 */
public class LogManagerTests {
    GlobalLogExtender _logExtender = new GlobalLogExtender();
    ILogManager _logManager = LogManager.GetInstance();

    @org.junit.Before
    public void Initialize()
    {
        _logManager.SetEmitter(_logExtender);
    }

    @org.junit.Test
    public void TestLogEmission() {
        ILogEntry entry = _logManager.CreateLogEntry(Priority.Alert);

        entry.SetSystem("Security System");
        entry.SetApplication("Security Tester");
        entry.SetComponent("Authentication Component");
        entry.SetEvent("Validation");
        entry.SetDescription("Validation has been invoked but was failed");
        entry.SetStatus(Status.Failure);

        entry.AddParameter("Param 1", "value 1");
        entry.AddParameter("Param 2", "value 2");

        _logManager.EmitLog(entry);

        ILogEntry log = _logExtender.GetOutputLog();

        assertEquals(true, log != null);
    }
}
