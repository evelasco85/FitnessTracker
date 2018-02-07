package com.codeflowcrafter.MVP;

/**
 * Created by aiko on 5/1/17.
 */

public interface IView <TRequest> {
    TRequest GetViewRequest();
    void SetViewRequest(TRequest viewRequest);
}
