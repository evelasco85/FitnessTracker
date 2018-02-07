package com.codeflowcrafter.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aiko on 4/29/17.
 */

public class DateHelper {
    public static String DateToString(Date date)
    {
        return DateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String DateToString(Date date, String customizedFormat)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                customizedFormat, Locale.getDefault());

        return dateFormat.format(date);
    }

    public static Date StringToDate(String dateString)
    {
        return  StringToDate(dateString, "yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    public static Date StringToDate(String dateString, String customizedFormat)
    {
        Date date = new Date();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(customizedFormat);
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return  date;
    }
}