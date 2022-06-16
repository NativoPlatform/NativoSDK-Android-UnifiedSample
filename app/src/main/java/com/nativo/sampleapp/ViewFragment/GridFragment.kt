package com.nativo.sampleapp.ViewFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nativo.sampleapp.ViewAdapter.GridViewAdapter
import com.nativo.sampleapp.databinding.FragmentGridBinding

class GridFragment: Fragment() {
    private lateinit var binding: FragmentGridBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gridView.adapter = GridViewAdapter(requireContext())
    }
}