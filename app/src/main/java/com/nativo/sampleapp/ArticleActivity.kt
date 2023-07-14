package com.nativo.sampleapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.nativo.sampleapp.databinding.ActivityArticleBinding
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.NtvSectionAdapter
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType


class ArticleActivity : AppCompatActivity(), NtvSectionAdapter {

    private lateinit var binding: ActivityArticleBinding
    private val articleSectionUrl = "www.nativo.com/demoapp?page=article"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nativo Setup
        NativoSDK.initSectionWithAdapter(this,  articleSectionUrl, this)
        NativoSDK.prefetchAdForSection(articleSectionUrl)
    }

    override fun didReceiveAd(didGetFill: Boolean, inSection: String) {
        NativoSDK.placeAdInView(binding.nativoContainer, binding.articleContainer, articleSectionUrl, 0)
    }

    override fun didAssignAdToLocation(
        location: Int,
        adData: NtvAdData,
        inSection: String,
        container: ViewGroup
    ) {
    }

    override fun didFailAd(
        inSection: String,
        atLocation: Int?,
        inView: View?,
        container: ViewGroup?,
        error: Throwable?
    ) {
    }

    override fun didPlaceAdInView(
        view: View,
        adData: NtvAdData,
        injectable: NtvInjectable,
        atLocation: Int,
        inSection: String,
        container: ViewGroup
    ) {
    }

    override fun needsDisplayClickOutURL(url: String, inSection: String, container: ViewGroup) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun needsDisplayLandingPage(
        landingPageIntent: Intent,
        inSection: String,
        container: ViewGroup
    ) {
        startActivity(landingPageIntent)
    }

    override fun <T : NtvInjectable> registerInjectableClassForTemplateType(
        injectableType: NtvInjectableType,
        atLocation: Int?,
        inSection: String?
    ): Class<T>? {
        return null
    }
}