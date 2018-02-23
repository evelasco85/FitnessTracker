package com.codeflowcrafter.FitnessTracker.Shared;

import android.util.Pair;

import com.codeflowcrafter.FitnessTracker.Services.CalculatorService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by enric on 22/02/2018.
 */

public class RepMaxService {
    private HashMap<String, Pair<Integer, String>> _configuration = new HashMap<String, Pair<Integer, String>>();
    private static RepMaxService s_instance = new RepMaxService();
    public static RepMaxService GetInstance(){return s_instance;}

    private static final String KEY_FORMAT = "%s|%s";

    public static final String WEEK1 = "Week1";
    public static final String WEEK2 = "Week2";
    public static final String WEEK3 = "Week3";
    public static final String WEEK4 = "Week4";

    public static final String SET1 = "SET1";
    public static final String SET2 = "SET2";
    public static final String SET3 = "SET3";

    private RepMaxService()
    {
        AddConfiguration(WEEK1, SET1, 65, "5");
        AddConfiguration(WEEK1, SET2, 75, "5");
        AddConfiguration(WEEK1, SET3, 85, "5+");

        AddConfiguration(WEEK2, SET1, 70, "3");
        AddConfiguration(WEEK2, SET2, 80, "3");
        AddConfiguration(WEEK2, SET3, 90, "3+");

        AddConfiguration(WEEK3, SET1, 75, "5");
        AddConfiguration(WEEK3, SET2, 85, "3");
        AddConfiguration(WEEK3, SET3, 95, "1+");

        AddConfiguration(WEEK4, SET1, 40, "5");
        AddConfiguration(WEEK4, SET2, 50, "5");
        AddConfiguration(WEEK4, SET3, 60, "5");
    }

    private String GetKey(String week, String set)
    {
        return String.format(KEY_FORMAT, week, set);
    }

    private void AddConfiguration(String week, String set, int percent, String repetitions)
    {
        String key = GetKey(week, set);
        Pair<Integer, String> configuration = new Pair<>(percent, repetitions);

        _configuration.put(key, configuration);
    }

    private Pair<Integer, String> GetConfiguration(String week, String set)
    {
        String key = GetKey(week, set);

        return _configuration.get(key);
    }

    public int GetPercent(String week, String set)
    {
        int percent = 0;
        Pair<Integer, String> configuration = GetConfiguration(week, set);

        if(configuration == null) return percent;

        percent = configuration.first;

        return percent;
    }

    public String GetRepetitions(String week, String set)
    {
        String repetitions = "";
        Pair<Integer, String> configuration = GetConfiguration(week, set);

        if(configuration == null) return repetitions;

        repetitions = configuration.second;

        return repetitions;
    }

    public double Get_1RM(double weightLbs, int repetitions)
    {
        int numerator = 36;
        int denominator = 37 - repetitions;

        if(denominator == 0) denominator = 1;

        double multiplier = (double)numerator / (double)denominator;
        double oneRM = weightLbs * multiplier;

        return oneRM;
    }

    public String WorkoutSetDescription(String week, String set, double oneRM)
    {
        String reps = GetRepetitions(week, set);
        int percentage = GetPercent(week, set);
        double weightLbs = ((double)percentage / (double) 100) * oneRM;
        String description = String.format("%.2f x %s", weightLbs, reps);

        return description;
    }

    public String GetWeek(String date)
    {
        String week = "N/A";

        Date currentDate =  GetCurrentDate();
        List<Pair<String, Date>> weekSet = new ArrayList<>();

        weekSet.add(new Pair<String, Date>(WEEK1, GetEndDate(date, 7 - 1)));
        weekSet.add(new Pair<String, Date>(WEEK2, GetEndDate(date, 14 - 1)));
        weekSet.add(new Pair<String, Date>(WEEK3, GetEndDate(date, 21 - 1)));
        weekSet.add(new Pair<String, Date>(WEEK4, GetEndDate(date, 28 - 1)));

        for(int index = 0 ; index < weekSet.size(); index++)
        {
            Pair<String, Date> set = weekSet.get(index);

            if(set == null) return week;

            String selectedWeek = set.first;
            Date endDateOfWeek = set.second;

            if(endDateOfWeek == null) return week;

            if(currentDate.getTime() <= endDateOfWeek.getTime())
            {
                return selectedWeek;
            }
        }

        return week;
    }

    private Date GetCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(CalculatorService.DateFormat);
        String date = sdf.format(calendar.getTime());

        return GetEndDate(date, 0);
    }

    private Date GetEndDate(String date, int days)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(CalculatorService.DateFormat);
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }

        calendar.add(Calendar.DATE, days);

        return new Date(calendar.getTimeInMillis());
    }
}
