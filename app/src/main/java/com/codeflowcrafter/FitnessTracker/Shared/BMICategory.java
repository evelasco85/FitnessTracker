package com.codeflowcrafter.FitnessTracker.Shared;

/**
 * Created by enric on 07/02/2018.
 */

public class BMICategory {
    private String _description;
    private double _upperScore;

    public BMICategory(String description, double upperScore)
    {
        _description = description;
        _upperScore = upperScore;
    }

    public String GetDescription() {
        return _description;
    }

    public double GetUpperScore() {
        return _upperScore;
    }
}
