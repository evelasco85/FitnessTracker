package com.codeflowcrafter.FitnessTracker.Shared;

/**
 * Created by enric on 07/02/2018.
 */

public class IntensityOfExercise {
    private String _description;
    private int _lowerPercentLimit;
    private int _upperPercentLimit;

    public IntensityOfExercise(
            String description,
            int lowerPercentLimit,
            int upperPercentLimit
    )
    {
        _description = description;
        _lowerPercentLimit = lowerPercentLimit;
        _upperPercentLimit = upperPercentLimit;
    }

    public String GetDescription() {
        return _description;
    }

    public int GetLowerPercentLimit() {
        return _lowerPercentLimit;
    }

    public int GetUpperPercentLimit() {
        return _upperPercentLimit;
    }
}
