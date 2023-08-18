package com.nativo.sampleapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nativo.sampleapp.adapters.ListViewAdapter
import com.nativo.sampleapp.databinding.FragmentTableBinding
import net.nativo.sdk.NativoSDK

class ListViewFragment : Fragment() {

    private lateinit var binding: FragmentTableBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTableBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listTable.apply {
            adapter = ListViewAdapter(requireContext(), this)
        }
    }

    override fun onResume() {
        super.onResume()
        NativoSDK.enablePlaceholderMode(false)
    }


}