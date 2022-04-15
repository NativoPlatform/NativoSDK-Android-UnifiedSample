package com.nativo.sampleapp.ViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.nativo.sampleapp.R;
import com.nativo.sampleapp.ViewAdapter.GridViewAdapter;

public class GridFragment extends Fragment {

    public GridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grid, container, false);
        GridView gridView = root.findViewById(R.id.grid_view);
        gridView.setAdapter(new GridViewAdapter(getContext(), gridView));
        return root;
    }
}
