package com.nativo.sampleapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.nativo.sampleapp.databinding.FragmentDfpBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.NtvSectionAdapter
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType

/*
* An example showing how to load a Nativo ad that is flighted through the
* Google Ad Manager platform using the Nativo SDK
* */
class GamFragment : Fragment(), NtvSectionAdapter {

    lateinit var binding: FragmentDfpBinding
    private var loadClick = View.OnClickListener { loadGAMAd() }
    private val adLocation = 0

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
        NativoSDK.enableGAMwithVersion("8.8.8")
        NativoSDK.initSectionWithAdapter(this, AppConstants.GAM_SECTION_URL, this.requireContext())
        loadGAMAd()

        binding.loadAd.setOnClickListener {
            NativoSDK.clearAds(AppConstants.GAM_SECTION_URL)
            loadGAMAd()
        }
    }

    private fun loadGAMAd() {
        // Set 3x3 ad size for Nativo ads
        val ntvAdSize = AdSize(3, 3)
        binding.publisherAdView.setAdSizes(ntvAdSize, AdSize.BANNER)
        binding.adHolder.visibility = View.VISIBLE

        // Create an ad request.
        val adRequest = AdManagerAdRequest.Builder()
            .addCustomTargeting("ntvPlacement", "1092187").build()

        // Create callback listener for GAM
        binding.publisherAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d(
                    "GAMExample",
                    "adUnit: " + binding.publisherAdView.adUnitId + " adSize: " + binding.publisherAdView.adSize
                )
                if (binding.publisherAdView.adSize == ntvAdSize) {
                    //call nativo.dfp.bannerexample.sdk method here pass in the mAdView to parse out the html
                    NativoSDK.makeGAMRequestWithPublisherAdView(binding.publisherAdView, AppConstants.GAM_SECTION_URL)
                } else {
                    binding.publisherAdView.visibility = View.VISIBLE
                    Log.d("GAMExample", "Did receive DFP banner ad")
                }
            }
        }
        // Load the request
        binding.publisherAdView.loadAd(adRequest)
    }

    override fun didReceiveAd(didGetFill: Boolean, inSection: String) {
        Log.d("GAMExample", "didReceiveAd: $didGetFill")
        if (didGetFill) {
            binding.adHolder.visibility = View.VISIBLE
            binding.publisherAdView.visibility = View.GONE
            NativoSDK.placeAdInView(binding.adHolder, binding.root, AppConstants.GAM_SECTION_URL, adLocation)
        } else {
            binding.adHolder.visibility = View.GONE
        }
    }

    override fun needsDisplayClickOutURL(url: String, inSection: String, container: ViewGroup) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun needsDisplayLandingPage(landingPageIntent: Intent, inSection: String, container: ViewGroup) {
        startActivity(landingPageIntent)
    }

    override fun didAssignAdToLocation(
        location: Int,
        adData: NtvAdData,
        inSection: String,
        container: ViewGroup
    ) {
        Log.d("GAMExample", "didAssignAdToLocation: $location")
    }

    override fun didFailAd(
        inSection: String,
        atLocation: Int?,
        inView: View?,
        container: ViewGroup?,
        error: Throwable?
    ) {
        Log.d("GAMExample", "didFailAd: $atLocation error: $error")
    }

    override fun didPlaceAdInView(
        view: View,
        adData: NtvAdData,
        injectable: NtvInjectable,
        atLocation: Int,
        inSection: String,
        container: ViewGroup
    ) {
        Log.d("GAMExample", "didPlaceAdInView: $atLocation")
    }

    override fun <T : NtvInjectable> registerInjectableClassForTemplateType(
        injectableType: NtvInjectableType,
        atLocation: Int?,
        inSection: String?
    ): Class<T>? {
        return null
    }
}