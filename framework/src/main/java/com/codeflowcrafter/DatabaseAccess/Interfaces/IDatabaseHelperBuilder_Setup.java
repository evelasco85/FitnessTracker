package com.codeflowcrafter.DatabaseAccess.Interfaces;

import android.content.Context;

import com.codeflowcrafter.DatabaseAccess.DatabaseHelper;

/**
 * Created by aiko on 5/1/17.
 */

public interface IDatabaseHelperBuilder_Setup {
    public IDatabaseHelperBuilder_Setup AddTable(String tableName, String tableScript);
    public DatabaseHelper Create(Context context);
}