package com.nativo.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nativo.sampleapp.NativeAdLandingImpl.NativeLandingPage
import com.nativo.sampleapp.databinding.ActivitySponsoredContentBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK

class SponsoredContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySponsoredContentBinding
    private var withView = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySponsoredContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionUrl = intent.getStringExtra(AppConstants.SP_SECTION_URL)
        val campaignId = intent.getIntExtra(AppConstants.SP_CAMPAIGN_ID, 0)
        val containerHash = intent.getIntExtra(AppConstants.SP_CONTAINER_HASH, 0)

        // Pass in the class that implemented the NtvLandingPageInterface. Can be different layout classes that you switch between
        if (withView) {
            NativoSDK.initLandingPage(
                binding.landingPageContainer,
                sectionUrl,
                containerHash,
                campaignId,
                NativeLandingPage::class.java
            )
        } else {
            NativoSDK.initLandingPage(
                this,
                sectionUrl,
                containerHash,
                campaignId,
                NativeLandingPage::class.java
            )
        }
    }
}