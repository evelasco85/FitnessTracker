package com.codeflowcrafter.DatabaseAccess;

import android.content.Context;

public abstract class BaseContentProviders {
    public abstract DatabaseHelper GetDatabaseHelper(Context context);
}