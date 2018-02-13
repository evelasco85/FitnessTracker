package com.codeflowcrafter.FitnessTracker.Shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 07/02/2018.
 */

public class BMICategoryService {
    private static final String LAST_CATEGORY_DESCRIPTION = "Obese Class III";
    private static final double UPPER_SCORE_UNDERWEIGHT = 18.5;
    private static final double UPPER_SCORE_NORMAL = 25;
    private List<BMICategory> _categories =  new ArrayList<>();

    private static BMICategoryService s_instance = new BMICategoryService();
    public static BMICategoryService GetInstance(){return s_instance;}

    private BMICategoryService()
    {
        BMICategory category1 = new BMICategory("Very severely underweight", 15);
        BMICategory category2 = new BMICategory("Severely underweight", 16);
        BMICategory category3 = new BMICategory("Underweight", UPPER_SCORE_UNDERWEIGHT);
        BMICategory category4 = new BMICategory("Normal", UPPER_SCORE_NORMAL);
        BMICategory category5 = new BMICategory("Overweight", 30);
        BMICategory category6 = new BMICategory("Obese Class I", 35);
        BMICategory category7 = new BMICategory("Obese Class II", 40);
        BMICategory category8 = new BMICategory(LAST_CATEGORY_DESCRIPTION, 100);

        _categories.add(category1);
        _categories.add(category2);
        _categories.add(category3);
        _categories.add(category4);
        _categories.add(category5);
        _categories.add(category6);
        _categories.add(category7);
        _categories.add(category8);
    }

    public String GetBMICategory(double score)
    {
        int categorySize = _categories.size() - 1;
        int currentCategoryLevel = 0;
        String description = LAST_CATEGORY_DESCRIPTION;

        while((description.equals(LAST_CATEGORY_DESCRIPTION)) && (currentCategoryLevel < categorySize))
        {
            BMICategory category = _categories.get(currentCategoryLevel);

            if(score < category.GetUpperScore())
            {
                description = category.GetDescription();
            }

            currentCategoryLevel = currentCategoryLevel + 1;
        }

        return description;
    }

    public double IdealNormalWeightLbs(int heightInches)
    {
        double meanNormalBmiScore = (UPPER_SCORE_UNDERWEIGHT + UPPER_SCORE_NORMAL) / 2;

        return (meanNormalBmiScore * (heightInches * heightInches)) / 703;
    }
}
