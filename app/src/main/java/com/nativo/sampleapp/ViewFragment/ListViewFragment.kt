package com.nativo.sampleapp.ViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.nativo.sampleapp.R;
import com.nativo.sampleapp.ViewAdapter.ListViewAdapter;

public class ListViewFragment extends Fragment {

    private ListView listView;
    private ListViewAdapter listViewAdapter;

    public ListViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        listView = view.findViewById(R.id.list_table);
        listViewAdapter = new ListViewAdapter(listView);
        listView.setAdapter(listViewAdapter);
        return view;
    }

}
