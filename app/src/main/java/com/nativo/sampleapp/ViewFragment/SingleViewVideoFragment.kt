package com.nativo.sampleapp.ViewFragment

import net.nativo.sdk.ntvcore.NtvSectionAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.nativo.sampleapp.R
import net.nativo.sdk.NativoSDK
import android.widget.TextView
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import net.nativo.sdk.ntvcore.NtvAdData.NtvAdTemplateType
import com.nativo.sampleapp.activities.SponsoredContentActivity
import com.nativo.sampleapp.databinding.FragmentSingleViewVideoBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.ntvadtype.NtvBaseInterface
import net.nativo.sdk.ntvcore.NtvAdData

/**
 * A simple [Fragment] subclass.
 */
class SingleViewVideoFragment : Fragment(), NtvSectionAdapter {

    private lateinit var binding: FragmentSingleViewVideoBinding

    private var onClickListener = View.OnClickListener {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }
    private var loadAd: View.OnClickListener = View.OnClickListener {
        NativoSDK.prefetchAdForSection(
            AppConstants.SECTION_URL,
            view as ViewGroup?,
            0,
            this@SingleViewVideoFragment,
            null
        )
        Log.d(
            javaClass.name,
            NativoSDK.getAdTypeForIndex(AppConstants.SECTION_URL, view as ViewGroup?, 0).toString()
        )
        if (!ad) {
            bindView(view)
        }
    }
    private var hideAd = View.OnClickListener { binding.videoContainer.visibility = View.INVISIBLE }
    private var showAd = View.OnClickListener { binding.videoContainer.visibility = View.VISIBLE }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSingleViewVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NativoSDK.prefetchAdForSection(AppConstants.SECTION_URL, this, null)
        if (!ad) {
            bindView(view)
        }
        binding.loadAd.setOnClickListener(loadAd)
        binding.showAd.setOnClickListener(showAd)
        binding.hideAd.setOnClickListener(hideAd)
    }

    private val ad: Boolean
        get() = NativoSDK.placeAdInView(
            binding.videoContainer,
            view as ViewGroup?,
            AppConstants.SECTION_URL,
            0,
            this,
            null
        )

    private fun bindView(view: View?) {
        if (view == null) return

        val articleImage: ImageView? = view.findViewById(R.id.article_image)
        val sponsoredAdIndicator: ImageView? = view.findViewById(R.id.sponsored_ad_indicator)
        val articleAuthor: TextView? = view.findViewById(R.id.article_author)
        val articleTitle: TextView? = view.findViewById(R.id.article_title)
        val sponsoredTag: TextView? = view.findViewById(R.id.sponsored_tag)
        val articleContainer: View? = view.findViewById(R.id.article_container)

        articleImage?.setImageResource(R.drawable.newsimage)
        sponsoredAdIndicator?.visibility = View.INVISIBLE
        articleAuthor?.setText(R.string.sample_author)
        articleTitle?.setText(R.string.sample_title)
        sponsoredTag?.visibility = View.INVISIBLE

        articleContainer?.setBackgroundColor(Color.RED)
        view.setOnClickListener(onClickListener)
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

    override fun onReceiveAd(section: String, ntvAdData: NtvAdData, index: Int?) {}
    override fun onFail(section: String, index: Int?) {}
}