package com.nativo.sampleapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nativo.sampleapp.NativeAdLandingImpl.NativeLandingPage;

import net.nativo.sdk.NativoSDK;

import static com.nativo.sampleapp.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.sampleapp.util.AppConstants.SP_CONTAINER_HASH;
import static com.nativo.sampleapp.util.AppConstants.SP_SECTION_URL;

public class SponsoredContentActivity extends AppCompatActivity {

    boolean withView = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String sectionUrl = getIntent().getStringExtra(SP_SECTION_URL);
        int campaignId = getIntent().getIntExtra(SP_CAMPAIGN_ID, 0);
        Integer containerHash = getIntent().getIntExtra(SP_CONTAINER_HASH, 0);
        //pass in the class that implemented the NtvLandingPageInterface. Can be different layout classes that you switch between
        setContentView(R.layout.activity_sponsored_content);
        View view = findViewById(R.id.landing_page_container);
        if (withView) {
            NativoSDK.initLandingPage(view, sectionUrl, containerHash, campaignId, NativeLandingPage.class);
        } else {
            NativoSDK.initLandingPage(this, sectionUrl, containerHash, campaignId, NativeLandingPage.class);
        }
    }
}
