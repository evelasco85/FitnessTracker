package com.codeflowcrafter.FitnessTracker.Base.Activity;

import java.util.ArrayList;

/**
 * Created by enric on 05/02/2018.
 */

public interface IDataContainer<TEntity>{
    ArrayList<TEntity> GetList();
}
