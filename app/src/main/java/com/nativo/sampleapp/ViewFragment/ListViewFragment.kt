package com.nativo.sampleapp.ViewFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nativo.sampleapp.ViewAdapter.ListViewAdapter
import com.nativo.sampleapp.databinding.FragmentTableBinding

class ListViewFragment: Fragment() {
    private lateinit var binding: FragmentTableBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listTable.adapter = ListViewAdapter(requireContext(), binding.listTable)
    }
}