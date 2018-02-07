package com.codeflowcrafter.FitnessTracker.Shared;

/**
 * Created by enric on 07/02/2018.
 */

public class IntensityOfExercise {
    private String _description;
    private int _lowerMhrPercentLimit;
    private int _upperMhrPercentLimit;

    public IntensityOfExercise(
            String description,
            int lowerMhrPercentLimit,
            int upperMhrPercentLimit
    )
    {
        _description = description;
        _lowerMhrPercentLimit = lowerMhrPercentLimit;
        _upperMhrPercentLimit = upperMhrPercentLimit;
    }

    public String GetDescription() {
        return _description;
    }

    public int GetLowerMhrPercentLimit() {
        return _lowerMhrPercentLimit;
    }

    public int GetUpperMhrPercentLimit() {
        return _upperMhrPercentLimit;
    }
}
