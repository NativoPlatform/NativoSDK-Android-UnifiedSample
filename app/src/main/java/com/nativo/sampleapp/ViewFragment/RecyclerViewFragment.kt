package com.nativo.sampleapp.ViewFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativo.sampleapp.ViewAdapter.RecyclerViewAdapter
import com.nativo.sampleapp.databinding.FragmentRecyclerListViewBinding
import com.nativo.sampleapp.util.AppConstants.SECTION_URL
import com.nativo.sampleapp.util.Reloadable
import net.nativo.sdk.NativoSDK


class RecyclerViewFragment: Fragment(), Reloadable {
    private lateinit var binding: FragmentRecyclerListViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerListViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RecyclerViewAdapter(context, this)
        }

    }


    override fun reload() {
        val adapter = binding.recyclerListView.adapter as RecyclerViewAdapter
        NativoSDK.clearAds(SECTION_URL)
        NativoSDK.prefetchAdForSection(SECTION_URL)
        adapter.notifyDataSetChanged()
    }
}