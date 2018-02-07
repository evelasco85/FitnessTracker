package com.codeflowcrafter.LogManagement.Interfaces;

import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;

import java.util.HashMap;

/**
 * Created by aiko on 4/29/17.
 */

public interface IStaticLogEntryWrapper_Emitter
{
    void EmitLog(Priority priority, String description, HashMap<String, String> params);
    void EmitLog(Priority priority, Status status);
    void EmitLog(Priority priority, Status status, HashMap<String, String> params);
}
