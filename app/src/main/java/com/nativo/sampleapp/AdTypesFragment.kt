package com.nativo.sampleapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativo.sampleapp.databinding.FragmentAdTypesListBinding
import com.nativo.sampleapp.util.AppConstants
import com.nativo.sampleapp.util.Reloadable
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.NtvSectionAdapter
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType

class AdTypesFragment : Fragment(), Reloadable, NtvSectionAdapter {

    private lateinit var binding: FragmentAdTypesListBinding

    val articleSectionUrl = "http://www.nativo.com/article"
    val displaySectionUrl = "http://www.nativo.com/display"
    val videoSectionUrl = "http://www.nativo.com/video"
    val storySectionUrl = "http://www.nativo.com/story"
    val stdDisplaySectionUrl = "http://www.nativo.com/standarddisplay"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdTypesListBinding.inflate(inflater, container, false)

        NativoSDK.enablePlaceholderMode(true)
        context?.let {
            NativoSDK.disableAutoPrefetch(true)
            NativoSDK.initSectionWithAdapter(this, articleSectionUrl, it)
            NativoSDK.initSectionWithAdapter(this, displaySectionUrl, it)
            NativoSDK.initSectionWithAdapter(this, videoSectionUrl, it)
            NativoSDK.initSectionWithAdapter(this, storySectionUrl, it)
            NativoSDK.initSectionWithAdapter(this, stdDisplaySectionUrl, it)

            NativoSDK.prefetchAdForSection(articleSectionUrl, 0, binding.adsScrollView)
            NativoSDK.prefetchAdForSection(articleSectionUrl, 1, binding.adsScrollView, mapOf("ntv_a" to "525631", "ntv_pl" to "1211528"))
            NativoSDK.prefetchAdForSection(articleSectionUrl, 2, binding.adsScrollView, mapOf("ntv_a" to "530424", "ntv_pl" to "1211528"))

            NativoSDK.prefetchAdForSection(displaySectionUrl, 0, binding.adsScrollView, mapOf("ntv_a" to "534894", "ntv_pl" to "1211528"))
            NativoSDK.prefetchAdForSection(displaySectionUrl, 1, binding.adsScrollView)
            NativoSDK.prefetchAdForSection(displaySectionUrl, 2, binding.adsScrollView)

            NativoSDK.prefetchAdForSection(videoSectionUrl, 0, binding.adsScrollView, mapOf("ntv_a" to "553602", "ntv_pl" to "1211528"))
            NativoSDK.prefetchAdForSection(videoSectionUrl, 1, binding.adsScrollView, mapOf("ntv_a" to "553601", "ntv_pl" to "1211528"))
            NativoSDK.prefetchAdForSection(videoSectionUrl, 2, binding.adsScrollView, mapOf("ntv_a" to "554152", "ntv_pl" to "1211528"))

            NativoSDK.prefetchAdForSection(storySectionUrl, 0, binding.adsScrollView, mapOf("ntv_a" to "463280", "ntv_pl" to "1211528"))
            NativoSDK.prefetchAdForSection(storySectionUrl, 1, binding.adsScrollView, mapOf("ntv_a" to "445021", "ntv_pl" to "1211528"))
            NativoSDK.prefetchAdForSection(storySectionUrl, 2, binding.adsScrollView, mapOf("ntv_a" to "526013", "ntv_pl" to "1211528"))

            NativoSDK.prefetchAdForSection(stdDisplaySectionUrl, 0, binding.adsScrollView, mapOf("ntv_a" to "553749", "ntv_pl" to "1211528"))
            NativoSDK.prefetchAdForSection(stdDisplaySectionUrl, 1, binding.adsScrollView)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun reload() {
        Log.d("NativoApp", "Reloading dataset")
        NativoSDK.clearAds(articleSectionUrl)
        NativoSDK.clearAds(displaySectionUrl)
        NativoSDK.clearAds(videoSectionUrl)
        NativoSDK.clearAds(storySectionUrl)
        NativoSDK.clearAds(stdDisplaySectionUrl)
        //NativoSDK.initSectionWithAdapter(this, nativoSection, context)

//        NativoSDK.placeAdInView(adView, binding.listArticleLayout, articleSectionUrl, 0)

    }

    override fun didReceiveAd(didGetFill: Boolean, inSection: String) {
        Log.d(AppConstants.NtvTAG, "Did receive ad: $didGetFill")
    }

    override fun didAssignAdToLocation(location: Int, adData: NtvAdData, inSection: String, container: ViewGroup) {
        Log.d(AppConstants.NtvTAG, "didAssignAdToLocation: $location")

        val adView : View? = when (inSection) {
            articleSectionUrl -> {
                when (location) {
                    0 -> binding.articleContainer1
                    1 -> binding.articleContainer2
                    2 -> binding.articleContainer3
                    else -> { null }
                }
            }
            displaySectionUrl -> {
                when (location) {
                    0 -> binding.displayContainer1
                    1 -> binding.displayContainer2
                    2 -> binding.displayContainer3
                    else -> { null }
                }
            }
            videoSectionUrl -> {
                when (location) {
                    0 -> binding.videoContainer1
                    1 -> binding.videoContainer2
                    2 -> binding.videoContainer3
                    else -> { null }
                }
            }
            storySectionUrl -> {
                when (location) {
                    0 -> binding.storyContainer1
                    1 -> binding.storyContainer2
                    2 -> binding.storyContainer3
                    else -> { null }
                }
            }
            stdDisplaySectionUrl -> {
                when (location) {
                    0 -> binding.stdDisplayContainer1
                    1 -> binding.stdDisplayContainer2
                    else -> { null }
                }
            }
            else -> { null }
        }
        if (adView != null) {
            NativoSDK.placeAdInView(adView, binding.adsScrollView, inSection, location)
        }
    }

    override fun didFailAd(inSection: String, atLocation: Int?, inView: View?, container: ViewGroup?, error: Throwable?) {
        Log.d(AppConstants.NtvTAG, "onFail at location: $atLocation Error: ${error?.message}")
    }

    override fun didPlaceAdInView(view: View, adData: NtvAdData, injectable: NtvInjectable, atLocation: Int, inSection: String, container: ViewGroup) {
        Log.d(AppConstants.NtvTAG, "didPlaceAdInView: $atLocation AdData: $adData")
    }

    override fun needsDisplayClickOutURL(url: String, inSection: String, container: ViewGroup) {
        context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun needsDisplayLandingPage(
        landingPageIntent: Intent,
        inSection: String,
        container: ViewGroup
    ) {
        context?.startActivity(landingPageIntent)
    }

    override fun <T : NtvInjectable> registerInjectableClassForTemplateType(
        injectableType: NtvInjectableType,
        atLocation: Int?,
        inSection: String?
    ): Class<T>? {
        return null
    }
}