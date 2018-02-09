package com.codeflowcrafter.FitnessTracker.Base.Activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_IRequests;
import com.codeflowcrafter.MVP.IView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enric on 06/02/2018.
 */

public abstract class Base_Activity_Main<
            TEntity,
            TRequest extends Crud_IRequests<TEntity>,
            TListItem extends BaseListItem<TEntity, TRequest>
        >
        extends AppCompatActivity
        implements IView<TRequest>, LoaderManager.LoaderCallbacks<Cursor> {
    private DataContainer<TEntity> _container = new DataContainer();

    private TRequest _viewRequest;
    public TRequest GetViewRequest(){return _viewRequest;}
    public void SetViewRequest(TRequest viewRequest){_viewRequest = viewRequest;}

    private int _layoutId;
    private int _listFragmentId;

    public Base_Activity_Main(int layoutId, int listFragmentId)
    {
        _layoutId = layoutId;
        _listFragmentId = listFragmentId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(_layoutId);

        _container.SetAdapter(GetListItem(_container));
        _container.SetListFragment(getFragmentManager(), _listFragmentId);
        _container.PerformListBinding();

        SetViewHandlers();
        SetDefaultMainViewData();
    }

    public abstract TListItem GetListItem(DataContainer<TEntity> container);
    public abstract void SetViewHandlers();
    public abstract void onLoadFinished(Loader<Cursor> loader, Cursor cursor);

    private void SetDefaultMainViewData()
    {
        getLoaderManager().initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        CursorLoader loader = new CursorLoader(this, _viewRequest.GetContentUri(),
                null, null, null, null
        );

        return  loader;
    }

    public void OnLoadEntitiesViaLoaderCompletion(List<TEntity> entityList){
        _container.ResetList(entityList);
    }

    public void onLoaderReset(Loader<Cursor> loader){}

    @Override
    public void onResume()
    {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }
}
