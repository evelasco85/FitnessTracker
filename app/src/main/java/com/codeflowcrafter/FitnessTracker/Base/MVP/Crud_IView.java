package com.codeflowcrafter.FitnessTracker.Base.MVP;

import com.codeflowcrafter.MVP.IView;
import java.util.List;

/**
 * Created by enric on 05/02/2018.
 */

public interface Crud_IView<
        TEntity,
        TIRequests extends Crud_IRequests<TEntity>
        >
        extends IView<TIRequests> {
    void OnLoadEntitiesViaLoaderCompletion(List<TEntity> entityList);

    //UI Prompting Events
    void OnPromptExecution_Detail(TEntity entity);
    void OnPromptExecution_AddEntry();
    void OnPromptExecution_EditEntry(TEntity entity);
    void OnPromptExecution_DeleteEntry(final TEntity entity);
}
