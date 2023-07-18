package com.nativo.sampleapp.ViewFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativo.sampleapp.ViewAdapter.RecyclerViewAdapter
import com.nativo.sampleapp.databinding.FragmentRecyclerListViewBinding
import com.nativo.sampleapp.util.AppConstants
import com.nativo.sampleapp.util.Reloadable
import net.nativo.sdk.NativoSDK

class RecyclerViewFragment : Fragment(), Reloadable {

    private lateinit var binding: FragmentRecyclerListViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerListView.apply {
            adapter = RecyclerViewAdapter(context, this)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onResume() {
        super.onResume()
        NativoSDK.enablePlaceholderMode(true)
    }

    override fun reload() {
        val adapter = binding.recyclerListView.adapter as RecyclerViewAdapter
        NativoSDK.clearAds(AppConstants.SECTION_URL)
        NativoSDK.prefetchAdForSection(AppConstants.SECTION_URL)
        adapter.notifyDataSetChanged()
    }
}