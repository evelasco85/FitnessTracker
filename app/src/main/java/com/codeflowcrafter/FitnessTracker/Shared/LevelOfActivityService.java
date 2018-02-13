package com.codeflowcrafter.FitnessTracker.Shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 13/02/2018.
 */

public class LevelOfActivityService {

    private static final String LEVEL_1 = "Little or no exercise";
    private static final String LEVEL_2 = "exercise 1-3 days";
    private static final String LEVEL_3 = "exercise 3-5 days";
    private static final String LEVEL_4 = "exercise 6-7 days";
    private static final String LEVEL_5 = "Very hard exercise";

    private List<LevelOfActivity> _levels =  new ArrayList<>();

    private static LevelOfActivityService s_instance = new LevelOfActivityService();
    public static LevelOfActivityService GetInstance(){return s_instance;}

    private LevelOfActivityService()
    {
        LevelOfActivity level1 = new LevelOfActivity(LEVEL_1, 1.2);
        LevelOfActivity level2 = new LevelOfActivity(LEVEL_2, 1.375);
        LevelOfActivity level3 = new LevelOfActivity(LEVEL_3, 1.55);
        LevelOfActivity level4 = new LevelOfActivity(LEVEL_4, 1.725);
        LevelOfActivity level5 = new LevelOfActivity(LEVEL_5, 1.9);

        _levels.add(level1);
        _levels.add(level2);
        _levels.add(level3);
        _levels.add(level4);
        _levels.add(level5);
    }

    public List<String> GetLevelDescriptions()
    {
        List<String> descriptions =  new ArrayList<>();

        for(int index = 0; index < _levels.size(); ++index)
        {
            LevelOfActivity level = _levels.get(index);

            descriptions.add(level.GetDescription());
        }

        return descriptions;
    }

    public double GetMultiplier(String description)
    {
        double multiplier = 0;

        for(int index = 0; index < _levels.size(); ++index)
        {
            LevelOfActivity level = _levels.get(index);

            if(level.GetDescription().equals(description))
            {
                multiplier = level.GetMultiplier();

                break;
            }
        }

        return multiplier;
    }
}
