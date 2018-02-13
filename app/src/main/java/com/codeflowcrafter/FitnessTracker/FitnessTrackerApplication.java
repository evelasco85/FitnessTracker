package com.codeflowcrafter.FitnessTracker;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.codeflowcrafter.LogManagement.Interfaces.ILogEmitter;
import com.codeflowcrafter.LogManagement.Interfaces.ILogEntry;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.LogManager;
import com.codeflowcrafter.LogManagement.StaticLogEntryWrapper;
import com.codeflowcrafter.PEAA.DataSynchronizationManager;
import com.codeflowcrafter.PEAA.Interfaces.IDataSynchronizationManager;
import com.codeflowcrafter.Utilities.DateHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by aiko on 5/1/17.
 */

public class FitnessTrackerApplication
        extends Application
    implements ILogEmitter
{
    private IDataSynchronizationManager _dsManager = DataSynchronizationManager.GetInstance();

    private static FitnessTrackerApplication s_instance;
    public static FitnessTrackerApplication GetInstance()
    {
        return s_instance;
    }

    public IStaticLogEntryWrapper CreateSLC()
    {
        return new StaticLogEntryWrapper(
                LogManager.GetInstance(),
                "Mobile",
                "com.codeflowcrafter.Sample"
        );
    }

    @Override
    public void OnLogEmit(ILogEntry log) {
        if (log == null) return;

        switch (log.GetPriority()) {
            case Debug:
            case Info:
            case Notice:
                Log.i(log.GetComponent(), GetInfoLogDetail(log));
                break;

            case Warning:
                Log.w(log.GetComponent(), GetInfoLogDetail(log));
                break;

            case Error:
            case Critical:
            case Alert:
            case Emergency:
                Log.e(log.GetComponent(), GetCompleteLogDetail(log));
                break;
        }

        HashMap<String, String> params = log.GetParameters();

        if ((params != null) && (!params.isEmpty())) EmitLogParams(log.GetEvent(), params);
    }

    private String GetInfoLogDetail(ILogEntry log)
    {
        JSONObject jsonLog = new JSONObject();
        String logString = "";

        try {
            TimeZone tz = log.GetTimeZone();

            jsonLog.put("Occurence", DateHelper.DateToString(log.GetOccurence()));

            jsonLog.put("Component", log.GetComponent());
            jsonLog.put("Event", log.GetEvent());

            jsonLog.put("Description", log.GetDescription());
            jsonLog.put("Status", log.GetStatus());

            logString = jsonLog.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logString = e.toString();
        }

        return logString;
    }

    private String GetCompleteLogDetail(ILogEntry log)
    {
        JSONObject jsonLog = new JSONObject();
        String logString = "";

        try {
            TimeZone tz = log.GetTimeZone();
            String timezone = "TimeZone   "+tz.getDisplayName(false, TimeZone.SHORT)+" Timezon id :: " +tz.getID();

            jsonLog.put("Occurence", DateHelper.DateToString(log.GetOccurence()));
            jsonLog.put("Timezone", timezone);

            jsonLog.put("System", log.GetSystem());
            jsonLog.put("Application", log.GetApplication());
            jsonLog.put("Component", log.GetComponent());

            jsonLog.put("Event", log.GetEvent());
            jsonLog.put("Priority", log.GetPriority());
            jsonLog.put("Description", log.GetDescription());
            jsonLog.put("Status", log.GetStatus());

            logString = jsonLog.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logString = e.toString();
        }

        return logString;
    }

    private void EmitLogParams(String event, HashMap<String, String> params)
    {
        for(Map.Entry<String, String> entry : params.entrySet()) {

            Log.i(String.format("Event[%s] Param", event), String.format("%s = %s", entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public final void onCreate()
    {
        s_instance = this;

        LogManager.GetInstance().SetEmitter(this);
        super.onCreate();

        FitnessTrackerContentProviders contentProviders = FitnessTrackerContentProviders
                .GetInstance();
        Context context = getApplicationContext();
        ContentResolver resolver = getContentResolver();

        contentProviders.RegisterDomains(context, resolver, _dsManager);
    }
}
