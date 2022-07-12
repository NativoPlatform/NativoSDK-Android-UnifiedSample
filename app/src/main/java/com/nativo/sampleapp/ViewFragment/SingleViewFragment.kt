package com.nativo.sampleapp.ViewFragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nativo.sampleapp.R
import com.nativo.sampleapp.activities.SponsoredContentActivity
import com.nativo.sampleapp.databinding.FragmentSingleViewBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.ntvadtype.NtvBaseInterface
import net.nativo.sdk.ntvcore.NtvAdData
import net.nativo.sdk.ntvcore.NtvAdData.NtvAdTemplateType
import net.nativo.sdk.ntvcore.NtvSectionAdapter

class SingleViewFragment : Fragment(), NtvSectionAdapter {
    private lateinit var binding: FragmentSingleViewBinding

    private var onClickListener = View.OnClickListener {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }
    private var loadAd = View.OnClickListener {
        NativoSDK.clearAdsInSection(AppConstants.SECTION_URL, view as ViewGroup?)
        NativoSDK.prefetchAdForSection(AppConstants.SECTION_URL, this@SingleViewFragment, null)
    }
    private var hideAd = View.OnClickListener { binding.articleContainer.visibility = View.INVISIBLE }
    private var showAd = View.OnClickListener { binding.articleContainer.visibility = View.VISIBLE }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NativoSDK.prefetchAdForSection(AppConstants.SECTION_URL, this, null)
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

    override fun registerLayoutClassForIndex(
        i: Int,
        ntvAdTemplateType: NtvAdTemplateType
    ): Class<*>? {
        return null
    }

    override fun needsDisplayLandingPage(s: String, i: Int) {
        Intent(context, SponsoredContentActivity::class.java).apply {
            putExtra(AppConstants.SP_CAMPAIGN_ID, s)
            putExtra(AppConstants.SP_CAMPAIGN_ID, i)
            putExtra(AppConstants.SP_CONTAINER_HASH, view.hashCode())

            startActivity(this)
        }
    }

    override fun needsDisplayClickOutURL(s: String, s1: String) {}
    override fun hasbuiltView(
        view: View,
        ntvBaseInterface: NtvBaseInterface,
        ntvAdData: NtvAdData
    ) {
    }

    override fun onReceiveAd(section: String, ntvAdData: NtvAdData, index: Int?) {
        Log.e(this.javaClass.name, "Did receive ad: $ntvAdData")
        val didGetNativoAd = NativoSDK.placeAdInView(binding.articleContainer, view as ViewGroup?, AppConstants.SECTION_URL, 0, this, null)
        if (didGetNativoAd) {
            binding.articleContainer.visibility = View.VISIBLE
        }
    }

    override fun onFail(section: String, index: Int) {
        binding.articleContainer.visibility = View.GONE
    }

}