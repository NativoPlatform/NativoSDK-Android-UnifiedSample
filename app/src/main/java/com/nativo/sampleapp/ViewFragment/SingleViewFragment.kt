package com.nativo.sampleapp.ViewFragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nativo.sampleapp.R
import com.nativo.sampleapp.databinding.FragmentSingleViewBinding
import com.nativo.sampleapp.util.AppConstants

class SingleViewFragment : Fragment() {
    private lateinit var binding: FragmentSingleViewBinding

    private var onClickListener = View.OnClickListener {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }
    private var loadAd = View.OnClickListener { }
    private var hideAd = View.OnClickListener { binding.articleContainer.visibility = View.INVISIBLE }
    private var showAd = View.OnClickListener { binding.articleContainer.visibility = View.VISIBLE }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadAd.setOnClickListener(loadAd)
        binding.showAd.setOnClickListener(showAd)
        binding.hideAd.setOnClickListener(hideAd)
        bindViews()
    }

    private fun bindViews() {
        binding.articleImage.setImageResource(R.drawable.newsimage)
        binding.sponsoredAdIndicator.visibility = View.INVISIBLE
        binding.articleAuthor.setText(R.string.sample_author)
        binding.articleTitle.setText(R.string.sample_title)
        binding.sponsoredTag.visibility = View.INVISIBLE

        binding.articleContainer.setBackgroundColor(Color.RED)
        binding.root.setOnClickListener(onClickListener)
    }

}