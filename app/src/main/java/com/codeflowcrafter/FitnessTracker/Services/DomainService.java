package com.codeflowcrafter.FitnessTracker.Services;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by enric on 13/02/2018.
 */

public class DomainService {
    public static void SetColumnOrdinal(
            Cursor cursor,
            HashMap<String, Integer> ordinals,
            String columnName)
    {
        ordinals.put(columnName ,cursor.getColumnIndexOrThrow(columnName));
    }
}
