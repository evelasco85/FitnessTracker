package com.codeflowcrafter.FitnessTracker.Base.Activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeflowcrafter.MVP.IView;

/**
 * Created by enric on 05/02/2018.
 */

public abstract class Base_Activity_Dialog_ReadAddEdit<TEntity, TRequest>
        extends DialogFragment
    implements IView<TRequest>
{
    private int _fragmentId;
    private int _saveCancelConcreteViewId;

    public static final String ACTION_ADD = "ADD";
    public static final String ACTION_EDIT = "EDIT";
    public static final String ACTION_READ = "READ";
    public static final String KEY_ACTION = "action";

    private TRequest _viewRequest;
    public TRequest GetViewRequest(){return  _viewRequest;}
    public void SetViewRequest(TRequest viewRequest){_viewRequest = viewRequest;}

    private TEntity _entityToEdit;
    public void SetEntityToEdit(TEntity entity){_entityToEdit = entity;}

    public Base_Activity_Dialog_ReadAddEdit(int fragmentId, int saveCancelConcreteViewId)
    {
        _fragmentId = fragmentId;
        _saveCancelConcreteViewId = saveCancelConcreteViewId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(_fragmentId, container, false);
        String selectedAction = getArguments().getString(KEY_ACTION);

        SetViewHandlers(view, selectedAction);

        switch (selectedAction)
        {
            case ACTION_EDIT:
            case ACTION_READ:
                SetModelToViewData(view, _entityToEdit);
                break;
        }

        if(selectedAction == ACTION_READ)
        {
            Fragment childFragment = getFragmentManager().findFragmentById(_saveCancelConcreteViewId);

            if(childFragment != null) {
                childFragment.getView().setVisibility(View.GONE);
            }
        }

        return view;
    }

    @Override
    public void onDestroyView()
    {
        try{
            Fragment childFragment = getFragmentManager().findFragmentById(_saveCancelConcreteViewId);

            if(childFragment == null) return;

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.remove(childFragment);
            transaction.commit();
        }
        catch(Exception e){}

        super.onDestroyView();
    }

    public abstract void SetViewHandlers(final View view, final String selectedAction);
    public abstract TEntity ViewDataToModel(View view);
    public abstract void SetModelToViewData(View view, TEntity entity);
}
