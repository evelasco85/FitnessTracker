package com.codeflowcrafter.FitnessTracker.Base.Activity;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 05/02/2018.
 */

public class DataContainer<TEntity> implements IDataContainer<TEntity>{
    private ArrayList<TEntity> _entityList;
    public ArrayList<TEntity> GetList(){return _entityList;}

    private ArrayAdapter<TEntity> _entityAdapter;
    public void SetAdapter(ArrayAdapter<TEntity> entityAdapter){_entityAdapter = entityAdapter;}

    private ListFragment _listFragment;
    public void SetListFragment(FragmentManager fragmentManager, int fragmentId){
        _listFragment = (ListFragment) fragmentManager.findFragmentById(fragmentId);
    }

    public DataContainer()
    {
        _entityList = new ArrayList<TEntity>();
    }

    public void PerformListBinding()
    {
        _listFragment.setListAdapter(_entityAdapter);
    }

    public void ResetList(List<TEntity> entities)
    {
        _entityList.clear();
        _entityList.addAll(entities);
        _entityAdapter.notifyDataSetChanged();
    }
}
