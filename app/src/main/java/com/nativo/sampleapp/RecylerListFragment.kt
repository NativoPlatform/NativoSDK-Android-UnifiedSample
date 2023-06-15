package com.nativo.sampleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativo.sampleapp.databinding.FragmentRecylerListBinding


class RecylerListFragment : Fragment() {

    private lateinit var binding: FragmentRecylerListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecylerListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerList.apply {
            adapter = RecyclerViewAdapter(context, this, "http://www.nativo.com/demoapp")
            layoutManager = LinearLayoutManager(context)
        }
    }

}