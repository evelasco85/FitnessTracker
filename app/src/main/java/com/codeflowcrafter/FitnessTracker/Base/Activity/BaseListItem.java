package com.codeflowcrafter.FitnessTracker.Base.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

/**
 * Created by enric on 05/02/2018.
 */

public abstract class BaseListItem<TEntity, TRequest>  extends ArrayAdapter<TEntity> {
    private int _resource;
    private Context _activityContext;
    private IDataContainer<TEntity> _container;
    private TRequest _viewRequest;

    public BaseListItem(Context activityContext,
                        int resource,
                        TRequest viewRequest,
                        IDataContainer<TEntity> container)
    {
        super(activityContext, resource, container.GetList());

        _activityContext = activityContext;
        _resource = resource;
        _viewRequest = viewRequest;
        _container = container;
    }

    private LinearLayout GetLayout(View view, Context context, int resource)
    {
        LinearLayout layout;

        if(view == null)
        {
            LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            layout = new LinearLayout(context);

            li.inflate(resource, layout, true);
        }
        else
            layout = (LinearLayout) view;

        return layout;
    }

    public View getView(int position, final View convertView, final ViewGroup parent)
    {
        final LinearLayout itemLayout = GetLayout(convertView, getContext(), _resource);
        final Context activityContext = _activityContext;
        final TRequest viewRequest = _viewRequest;

        SetItemViewHandler(activityContext, itemLayout, viewRequest, getItem(position));

        return itemLayout;
    }

    public abstract void SetItemViewHandler(
            Context activityContext, LinearLayout itemLayout,
            TRequest viewRequest, TEntity item);
}
