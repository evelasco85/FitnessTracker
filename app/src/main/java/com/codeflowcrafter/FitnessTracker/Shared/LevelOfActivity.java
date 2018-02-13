package com.codeflowcrafter.FitnessTracker.Shared;

/**
 * Created by enric on 13/02/2018.
 */

public class LevelOfActivity {
    private String _description;
    private double _multiplier;

    public LevelOfActivity(
            String description,
            double multiplier)
    {
        SetDescription(description);
        SetMultiplier(multiplier);
    }

    public String GetDescription() {
        return _description;
    }

    public void SetDescription(String _description) {
        this._description = _description;
    }

    public double GetMultiplier() {
        return _multiplier;
    }

    public void SetMultiplier(double _multiplier) {
        this._multiplier = _multiplier;
    }
}
