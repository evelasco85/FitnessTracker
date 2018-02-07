package com.codeflowcrafter.FitnessTracker.Base.Domain;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by enric on 05/02/2018.
 */

public interface IEntityTranslator<TEntity> {
    void UpdateColumnOrdinals(Cursor cursor);
    TEntity CursorToEntity(Cursor cursor);
    ContentValues EntityToContentValues(TEntity entity);
}
