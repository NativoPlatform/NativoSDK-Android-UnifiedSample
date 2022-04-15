package com.nativo.sampleapp.ViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nativo.sampleapp.R;
import com.nativo.sampleapp.ViewAdapter.RecyclerViewAdapter;

public class RecyclerViewFragment extends Fragment {

    RecyclerView recyclerView;

    public RecyclerViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_list_view, container, false);
        RecyclerViewAdapter viewAdapter = new RecyclerViewAdapter(getContext(), recyclerView);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    // calling clear ads in section when your app transitions to new activity or fragment
    @Override
    public void onPause() {
        super.onPause();
    }
}
