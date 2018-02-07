package com.codeflowcrafter.FitnessTracker.Services;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

/**
 * Created by enric on 06/02/2018.
 */

public class ActivityService {
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
}
