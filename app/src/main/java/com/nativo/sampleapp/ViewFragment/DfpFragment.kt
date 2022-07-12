package com.nativo.sampleapp.ViewFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.doubleclick.PublisherAdView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.activities.SponsoredContentActivity
import com.nativo.sampleapp.databinding.FragmentDfpBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.ntvadtype.NtvBaseInterface
import net.nativo.sdk.ntvcore.NtvAdData
import net.nativo.sdk.ntvcore.NtvAdData.NtvAdTemplateType
import net.nativo.sdk.ntvcore.NtvSectionAdapter

/*
* This sample use the Nativo DFP account
* Orders -> "DFP test"
* LineItem -> "Mobile Test Line Item"
* Creative -> "Nativo Tag Creative"
* Campaign ID "c" -> 114921*/
class DfpFragment : Fragment(), NtvSectionAdapter {

    companion object {
        private val TAG = DfpFragment::class.java.simpleName
    }

    lateinit var binding: FragmentDfpBinding

    var mPublisherAdView: PublisherAdView? = null
    private val mNativoSDK: NativoSDK? = null
    var mAdLoader: AdLoader? = null
    var mMesssage: String? = null
    var nativoView: View? = null
    var nativoVideoView: View? = null
    var parentView: ViewGroup? = null

    private var loadClick = View.OnClickListener { loadGAMAd() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDfpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentView = binding.root
        nativoView = view.findViewById(R.id.article_constraint_layout)
        nativoView?.visibility = View.GONE
        nativoVideoView = view.findViewById(R.id.video_constraint_layout)
        nativoVideoView?.visibility = View.GONE
        NativoSDK.initWithGAMVersion(this.context, "17.0.0")
        val loadAd = view.findViewById<View>(R.id.load_ad)
        loadAd.setOnClickListener(loadClick)
        loadGAMAd()
    }

    private fun loadGAMAd() {
        mPublisherAdView = requireView().findViewById(R.id.publisherAdView)
        val ntvAdSize = AdSize(3, 3)
        mPublisherAdView?.setAdSizes(ntvAdSize, AdSize.BANNER)
        // Create an ad request.
        val adRequest = PublisherAdRequest.Builder()
            .addCustomTargeting("ntvPlacement", "1092187").build()
        mPublisherAdView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d(
                    TAG,
                    "adUnit: " + mPublisherAdView?.adUnitId + " adSize: " + mPublisherAdView?.adSize
                )
                if (mPublisherAdView?.adSize == ntvAdSize) {
                    //call nativo.dfp.bannerexample.sdk method here pass in the mAdView to parse out the html
                    NativoSDK.makeGAMRequestWithPublisherAdView(
                        mPublisherAdView,
                        parentView,
                        AppConstants.DFP_SECTION_URL,
                        0,
                        this@DfpFragment
                    )
                } else {
                    Log.d(TAG, "Did receive DFP banner ad")
                }
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "onAdFailedToLoad: errorCode: $errorCode")
            }
        }
        mPublisherAdView?.loadAd(adRequest)
    }

    override fun registerLayoutClassForIndex(
        i: Int,
        ntvAdTemplateType: NtvAdTemplateType
    ): Class<*>? {
        return null
    }

    override fun needsDisplayLandingPage(s: String, i: Int) {
        startActivity(
            Intent(context, SponsoredContentActivity::class.java)
                .putExtra(AppConstants.SP_SECTION_URL, s)
                .putExtra(AppConstants.SP_CAMPAIGN_ID, i)
                .putExtra(AppConstants.SP_CONTAINER_HASH, parentView.hashCode())
        )
    }

    override fun needsDisplayClickOutURL(s: String, s1: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(s1)))
    }

    override fun hasbuiltView(
        view: View,
        ntvBaseInterface: NtvBaseInterface,
        ntvAdData: NtvAdData
    ) {
    }

    override fun onReceiveAd(s: String, ntvAdData: NtvAdData, integer: Int) {
        Log.d(TAG, "Ad loaded")
        if (ntvAdData.adType == NtvAdData.NtvAdType.NATIVE || ntvAdData.adType == NtvAdData.NtvAdType.CLICK_OUT) {
            nativoView!!.visibility = View.VISIBLE
            NativoSDK.placeAdInView(
                nativoView,
                parentView,
                AppConstants.DFP_SECTION_URL,
                0,
                this,
                null
            )
        } else if (ntvAdData.adType == NtvAdData.NtvAdType.IN_FEED_VIDEO || ntvAdData.adType == NtvAdData.NtvAdType.IN_FEED_AUTO_PLAY_VIDEO) {
            nativoVideoView!!.visibility = View.VISIBLE
            NativoSDK.placeAdInView(
                nativoVideoView,
                parentView,
                AppConstants.DFP_SECTION_URL,
                0,
                this,
                null
            )
        }
    }

    override fun onFail(s: String, integer: Int) {
        Log.d(TAG, "Ad load failed")
    }
}