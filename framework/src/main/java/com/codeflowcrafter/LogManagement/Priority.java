package com.codeflowcrafter.LogManagement;

/**
 * Created by aiko on 4/29/17.
 */

public enum Priority
{
    /// <summary>
    /// Technical facing
    /// </summary>
    Debug,

    /// <summary>
    /// Displays expected or business as usual logs
    /// </summary>
    Info,

    /// <summary>
    /// Shows deviation from expectation
    /// </summary>
    Notice,

    /// <summary>
    /// Shows recurrent deviations or threshold exceeding limits
    /// </summary>
    Warning,

    /// <summary>
    /// Shows business logic error
    /// </summary>
    Error,

    /// <summary>
    /// Technical error or exception-handling
    /// </summary>
    Critical,

    /// <summary>
    /// Notification for constant attack, non-showstopper exceptions, or fail-safe invocations
    /// </summary>
    Alert,

    /// <summary>
    /// Notify showstopper or breaches
    /// </summary>
    Emergency
}
