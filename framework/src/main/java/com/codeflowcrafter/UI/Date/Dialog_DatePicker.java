package com.codeflowcrafter.UI.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.DatePicker;

import com.codeflowcrafter.Utilities.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aiko on 5/1/17.
 */

public class Dialog_DatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener
{

    private int _selectedYear;
    private int _selectedMonth;
    private int _selectedDay;
    private Date _selectedDate;

    public int GetSelectedYear(){return _selectedYear;}
    public int GetSelectedMonth() {return  _selectedMonth;}
    public int GetSelectedDay(){return _selectedDay;}

    private DatePickerDialog.OnDateSetListener _onDateSetListener;

    public void SetOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener)
    {
        _onDateSetListener = onDateSetListener;
    }

    public String GetDateString()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(_selectedDate);
    }

    public void SetDefaultDate(String dateString)
    {
        if(TextUtils.isEmpty(dateString))
            SetDefaultDate();
        else
            SetDefaultDate(DateHelper.StringToDate(dateString, "yyyy-MM-dd"));
    }

    public  void SetDefaultDate()
    {
        // Use the current date as the default date in the picker
        SetDefaultDate(Calendar.getInstance().getTime());
    }

    public void SetDefaultDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        SetDefaultDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    public void SetDefaultDate(int year, int month, int dayOfMonth)
    {
        _selectedYear = year;
        _selectedMonth = month;
        _selectedDay = dayOfMonth;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(_onDateSetListener == null)
            _onDateSetListener = this;

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), _onDateSetListener, _selectedYear, _selectedMonth, _selectedDay);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        _selectedMonth = month;
        _selectedDay = day;
        _selectedYear = year;

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, _selectedYear);
        cal.set(Calendar.MONTH, _selectedMonth);
        cal.set(Calendar.DAY_OF_MONTH, _selectedDay);

        _selectedDate = cal.getTime();
    }
}
