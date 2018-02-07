package com.codeflowcrafter.LogManagement;

import com.codeflowcrafter.LogManagement.Interfaces.ILogEntry;
import com.codeflowcrafter.LogManagement.Interfaces.ILogManager;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;

import static org.junit.Assert.assertEquals;

/**
 * Created by aiko on 4/29/17.
 */

@org.junit.Ignore
public class StaticLogEntryWrapperTests {
    GlobalLogExtender _logExtender = new GlobalLogExtender();
    ILogManager _manager = LogManager.GetInstance();

    @org.junit.Before
    public void Initialize()
    {
        _manager = LogManager.GetInstance();

        _manager.SetEmitter(_logExtender);
    }

    @org.junit.Test
    public void TestLogEmission() {
        IStaticLogEntryWrapper staticLogCreator = new StaticLogEntryWrapper(_manager);

        staticLogCreator.SetSystem("Security System");
        staticLogCreator.SetApplication("Security Tester");
        staticLogCreator.SetComponent("Authentication Component");
        staticLogCreator.SetEvent("Validation");

        ILogEntry entry = staticLogCreator.CreateLogEntry(Priority.Alert);

        _manager.EmitLog(entry);

        ILogEntry log = _logExtender.GetOutputLog();

        assertEquals(true, log != null);
    }

    @org.junit.Test
    public void TestLogEmission2() {
        IStaticLogEntryWrapper staticLogCreator = new StaticLogEntryWrapper(
                _manager,
                "Security System"
        );

        staticLogCreator.SetApplication("Security Tester");
        staticLogCreator.SetComponent("Authentication Component");
        staticLogCreator.SetEvent("Validation");

        ILogEntry entry = staticLogCreator.CreateLogEntry(Priority.Alert);

        _manager.EmitLog(entry);

        ILogEntry log = _logExtender.GetOutputLog();

        assertEquals(true, log != null);
    }

    @org.junit.Test
    public void TestLogEmission3() {
        IStaticLogEntryWrapper staticLogCreator = new StaticLogEntryWrapper(
                _manager,
                "Security System",
                "Security Tester"
        );

        staticLogCreator.SetComponent("Authentication Component");
        staticLogCreator.SetEvent("Validation");

        ILogEntry entry = staticLogCreator.CreateLogEntry(Priority.Alert);

        _manager.EmitLog(entry);

        ILogEntry log = _logExtender.GetOutputLog();

        assertEquals(true, log != null);
    }

    @org.junit.Test
    public void TestLogEmission4() {
        IStaticLogEntryWrapper staticLogCreator = new StaticLogEntryWrapper(
                _manager,
                "Security System",
                "Security Tester",
                "Authentication Component"
        );

        staticLogCreator.SetEvent("Validation");

        ILogEntry entry = staticLogCreator.CreateLogEntry(Priority.Alert);

        _manager.EmitLog(entry);

        ILogEntry log = _logExtender.GetOutputLog();

        assertEquals(true, log != null);
    }

    @org.junit.Test
    public void TestLogEmission5() {
        IStaticLogEntryWrapper staticLogCreator = new StaticLogEntryWrapper(
                _manager,
                "Security System",
                "Security Tester",
                "Authentication Component",
                "Validation"
        );

        ILogEntry entry = staticLogCreator.CreateLogEntry(Priority.Alert);

        _manager.EmitLog(entry);

        ILogEntry log = _logExtender.GetOutputLog();

        assertEquals(true, log != null);
    }
}
