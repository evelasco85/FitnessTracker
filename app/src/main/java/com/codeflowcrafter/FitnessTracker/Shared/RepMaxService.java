package com.codeflowcrafter.FitnessTracker.Shared;

import android.util.Pair;

import java.util.HashMap;

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
        String description = String.format("Weight(lbs.):%.2f\nReps:%s", weightLbs, reps);

        return description;
    }
}
