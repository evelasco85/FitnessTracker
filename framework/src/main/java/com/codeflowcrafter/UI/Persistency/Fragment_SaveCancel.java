package com.codeflowcrafter.UI.Persistency;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeflowcrafter.peaa.R;

/**
 * Created by aiko on 5/1/17.
 */

public class Fragment_SaveCancel extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_savecancel_layout, container, false);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnNewItemAddedListener");
        }
    }
}
