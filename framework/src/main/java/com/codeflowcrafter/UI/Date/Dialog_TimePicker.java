package com.codeflowcrafter.UI.Date;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.codeflowcrafter.Utilities.DateHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by aiko on 5/1/17.
 */

public class Dialog_TimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private int _selectedHour, _selectedMinute;

    private TimePickerDialog.OnTimeSetListener _onTimeSetListener;

    public void SetOnDateSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener)
    {
        _onTimeSetListener = onTimeSetListener;
    }

    public void SetDefaultTime(String timeString)
    {
        if(TextUtils.isEmpty(timeString))
            SetDefaultTime();
        else
            SetDefaultTime(DateHelper.StringToDate(timeString, "HH:mm"));
    }

    public  void SetDefaultTime()
    {
        SetDefaultTime(Calendar.getInstance().getTime());
    }

    public void SetDefaultTime(Date date)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        SetDefaultTime(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
        );
    }

    public void SetDefaultTime(int hourOfDay, int minute)
    {
        _selectedHour = hourOfDay;
        _selectedMinute = minute;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(_onTimeSetListener == null)
            _onTimeSetListener = this;

        return new TimePickerDialog(getActivity(), _onTimeSetListener, _selectedHour, _selectedMinute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        _selectedHour = hourOfDay;
        _selectedMinute = minute;
    }
}
