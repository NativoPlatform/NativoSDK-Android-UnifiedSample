package com.nativo.sampleapp.ViewFragment

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
import com.nativo.sampleapp.databinding.FragmentDfpBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK

/*
* This sample use the Nativo DFP account
* Orders -> "DFP test"
* LineItem -> "Mobile Test Line Item"
* Creative -> "Nativo Tag Creative"
* Campaign ID "c" -> 114921*/
class DfpFragment : Fragment() {

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
                        mPublisherAdView!!,
                        parentView!!,
                        AppConstants.DFP_SECTION_URL,
                        0
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
}