package com.codeflowcrafter.FitnessTracker.Base.MVP;

import android.content.CursorLoader;
import android.net.Uri;

/**
 * Created by enric on 05/02/2018.
 */

public interface Crud_IRequests<TEntity> {
    Uri GetContentUri();
    int LoadEntitiesViaLoader(CursorLoader loader);

    //User prompting operations (See corresponding events)
    void Prompt_Detail(TEntity entity);
    void Prompt_AddEntry();
    void Prompt_EditEntry(TEntity entity);
    void Prompt_DeleteEntry(TEntity entity);

    //Data source interactions
    void Add(TEntity entity);
    void Update(TEntity entity);
    void Delete(TEntity entity);

    void CancelEntry();
}
