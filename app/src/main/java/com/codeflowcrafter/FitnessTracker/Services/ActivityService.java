package com.codeflowcrafter.FitnessTracker.Services;

import android.app.AlertDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.view.View;

/**
 * Created by enric on 06/02/2018.
 */

public class ActivityService {
    private static final ToneGenerator _measurementCompletionTone = new ToneGenerator(
            AudioManager.STREAM_ALARM, 100);
    public static <TConcreteView> TConcreteView GetConcreteView(Class<TConcreteView> type,
                                                                View view, int concreteViewId)
    {
        return type.cast(view.findViewById(concreteViewId));
    }

    public static AlertDialog.Builder CreateDeleteAlertDialog(Context activityContext)
    {
        AlertDialog.Builder verify = new AlertDialog.Builder(activityContext);

        verify.setTitle("Are you sure you want to delete?");
        verify.setMessage("You are about to delete this item");

        return verify;
    }

    public static void Beep()
    {
        _measurementCompletionTone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }
}
