package com.codeflowcrafter.LogManagement;

import com.codeflowcrafter.LogManagement.Interfaces.ILogEntry;
import com.codeflowcrafter.LogManagement.Interfaces.ILogManager;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper_Emitter;

import java.util.HashMap;

public class StaticLogEntryWrapper implements IStaticLogEntryWrapper {
    private ILogManager _manager;

    private String _system;
    private String _application;
    private String _component;
    private String _event;

    public IStaticLogEntryWrapper SetSystem(String system) {
        _system = system;

        return this;
    }

    public IStaticLogEntryWrapper SetApplication(String application) {
        _application = application;

        return this;
    }

    public IStaticLogEntryWrapper SetComponent(String component) {
        _component = component;

        return this;
    }

    public IStaticLogEntryWrapper_Emitter SetEvent(String event) {
        _event = event;

        return this;
    }

    public StaticLogEntryWrapper(ILogManager manager) {
        this(manager, "", "", "", "");
    }

    public StaticLogEntryWrapper(ILogManager manager, String system) {
        this(manager, system, "", "", "");
    }

    public StaticLogEntryWrapper(ILogManager manager,
                                 String system,
                                 String application
    ) {
        this(manager, system, application, "", "");
    }

    public StaticLogEntryWrapper(ILogManager manager,
                                 String system,
                                 String application,
                                 String component
    ) {
        this(manager, system, application, component, "");
    }

    public StaticLogEntryWrapper(ILogManager manager,
                                 String system,
                                 String application,
                                 String component,
                                 String event
    ) {
        _manager = manager;

        _system = system;
        _application = application;
        _component = component;
        _event = event;
    }

    public ILogEntry CreateLogEntry(Priority priority) {
        ILogEntry entry = _manager.CreateLogEntry(priority);

        entry.SetSystem(_system);
        entry.SetApplication(_application);
        entry.SetComponent(_component);
        entry.SetEvent(_event);

        return entry;
    }

    public ILogEntry CreateLogEntry(Priority priority, String description)
    {
        ILogEntry entry = CreateLogEntry(priority);

        entry.SetDescription(description);

        return entry;
    }

    public void EmitLog(ILogEntry log)
    {
        _manager.EmitLog(log);
    }

    public void EmitLog(Priority priority, String description)
    {
        ILogEntry entry = CreateLogEntry(priority, description);

        EmitLog(entry);
    }

    public void EmitLog(Priority priority, Status status)
    {
        ILogEntry entry = CreateLogEntry(priority, "");

        entry.SetStatus(status);

        EmitLog(entry);
    }

    public void EmitLog(Priority priority, Status status, HashMap<String, String> params)
    {
        ILogEntry entry = CreateLogEntry(priority, "");

        entry.SetStatus(status);
        entry.SetParameters(params);

        EmitLog(entry);
    }

    public void EmitLog(Priority priority, String description, HashMap<String, String> params)
    {
        ILogEntry entry = CreateLogEntry(priority, description);

        entry.SetParameters(params);

        EmitLog(entry);
    }
}