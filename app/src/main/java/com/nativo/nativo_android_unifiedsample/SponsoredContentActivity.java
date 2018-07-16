package com.nativo.nativo_android_unifiedsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nativo.nativo_android_unifiedsample.NativeAdLandingImpl.NativeLandingPage;

import net.nativo.sdk.NativoSDK;

public class SponsoredContentActivity extends AppCompatActivity {

    public static final String SECTION_URL = "SECTION_URL";
    public static final String CAMPAIGN_ID = "CAMPAIGN_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String sectionUrl = getIntent().getStringExtra(SECTION_URL);
        int campaignId = getIntent().getIntExtra(CAMPAIGN_ID, 0);
        //pass in the class that implemented the NtvLandingPageInterface. Can be different layout classes that you switch between
        NativoSDK.getInstance().initLandingPage(this, sectionUrl, campaignId,NativeLandingPage.class);
    }
}
