package com.nativo.nativo_android_unifiedsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nativo.nativo_android_unifiedsample.NativeAdLandingImpl.NativeLandingPage;

import net.nativo.sdk.NativoSDK;

import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SECTION_URL;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_SECTION_URL;

public class SponsoredContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String sectionUrl = getIntent().getStringExtra(SP_SECTION_URL);
        int campaignId = getIntent().getIntExtra(SP_CAMPAIGN_ID, 0);
        //pass in the class that implemented the NtvLandingPageInterface. Can be different layout classes that you switch between
        NativoSDK.getInstance().initLandingPage(this, sectionUrl, campaignId,NativeLandingPage.class);
    }
}
