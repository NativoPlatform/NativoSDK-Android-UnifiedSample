package com.nativo.sampleapp.ViewFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nativo.sampleapp.ViewAdapter.GridViewAdapter
import com.nativo.sampleapp.databinding.FragmentGridBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK

class GridFragment : Fragment() {

    private lateinit var binding: FragmentGridBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gridView.apply {
            adapter = GridViewAdapter(context, this)
        }
    }

    override fun onResume() {
        super.onResume()
        NativoSDK.enablePlaceholderMode(false)
    }
}