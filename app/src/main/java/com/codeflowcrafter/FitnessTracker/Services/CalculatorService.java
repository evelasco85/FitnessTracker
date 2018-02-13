package com.codeflowcrafter.FitnessTracker.Services;

import com.codeflowcrafter.FitnessTracker.Shared.Gender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by enric on 07/02/2018.
 */

public class CalculatorService {
    public static final String DateFormat = "yyyy-MM-dd";

    public static int CalculateAge(Calendar dateOfBirth){
        Calendar today = Calendar.getInstance();

        int currentYear = today.get(Calendar.YEAR);
        int dateOfBirthYear = dateOfBirth.get(Calendar.YEAR);

        int age = currentYear - dateOfBirthYear;

        // if dob is month or day is behind today's month or day
        // reduce age by 1
        int currentMonth = today.get(Calendar.MONTH);
        int dateOfBirthMonth = dateOfBirth.get(Calendar.MONTH);
        if (dateOfBirthMonth > currentMonth) { // this year can't be counted!
            age--;
        } else if (dateOfBirthMonth == currentMonth) { // same month? check for day
            int currentDay = today.get(Calendar.DAY_OF_MONTH);
            int dateOfBirthDay = dateOfBirth.get(Calendar.DAY_OF_MONTH);
            if (dateOfBirthDay > currentDay) { // this year can't be counted!
                age--;
            }
        }

        return age;
    }

    public static Calendar ConvertToCalendar(String dateString)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
        Date date = new Date();

        try{
            date = sdf.parse(dateString);// all done
        }
        catch (ParseException ex){}

        return sdf.getCalendar();
    }

    public static int GetMaximumHeartRate(int age)
    {
        return 220 - age;
    }

    public static double GetBMI(double weightInPounds, int heightInInches)
    {
        int safeHeightInInches = (heightInInches == 0) ? 1 : heightInInches;

        double bmi = (703 * weightInPounds) / (safeHeightInInches * safeHeightInInches);

        return bmi;
    }

    public static double GetBMR(String gender, int age, double weightInPounds, int heightInInches)
    {
        double weightMultiplier = 0;
        double heightMultiplier = 0;
        double ageMultiplier = 0;
        int adder = 0;

        if(gender.equals(Gender.MALE))
        {
            adder = 66;
            weightMultiplier = 6.23;
            heightMultiplier = 12.7;
            ageMultiplier = 6.8;
        }
        else if(gender.equals(Gender.FEMALE))
        {
            adder = 655;
            weightMultiplier = 4.35;
            heightMultiplier = 4.7;
            ageMultiplier = 4.7;
        }

        double bmr = adder + (weightMultiplier * weightInPounds) + (heightMultiplier * heightInInches) -
                (ageMultiplier * age);

        return bmr;
    }

    public static double CalculateCaloriesByHarrisBenedictEquation(double bmr, double multiplier)
    {
        return bmr * multiplier;
    }

    public static int CalculateZonedHeartRate(
            int maximumHeartRate,
            int restingHeartRate,
            int percent)
    {
        int zone = (maximumHeartRate - restingHeartRate) * (percent / 100);
        int zonedHeartRate = zone + restingHeartRate;

        return zonedHeartRate;
    }
}
