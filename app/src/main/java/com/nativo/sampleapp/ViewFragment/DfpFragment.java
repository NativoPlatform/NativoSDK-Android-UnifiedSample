package com.nativo.sampleapp.ViewFragment;


import static com.nativo.sampleapp.util.AppConstants.DFP_SECTION_URL;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.nativo.sampleapp.R;
import com.nativo.sampleapp.util.AppConstants;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.NtvAdData;
import net.nativo.sdk.NtvSectionAdapter;
import net.nativo.sdk.injectable.NtvInjectable;
import net.nativo.sdk.injectable.NtvInjectableType;


/*
* This sample use the Nativo DFP account
* Orders -> "DFP test"
* LineItem -> "Mobile Test Line Item"
* Creative -> "Nativo Tag Creative"
* Campaign ID "c" -> 114921*/
public class DfpFragment extends Fragment implements NtvSectionAdapter {

    PublisherAdView mPublisherAdView;
    View nativoView;
    ViewGroup parentView;

    public DfpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dfp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Nativo init
        NativoSDK.enableTestAdvertisements();
        NativoSDK.enableGAMwithVersion("19.1.0");
        NativoSDK.initSectionWithAdapter(this, DFP_SECTION_URL, requireContext());

        parentView = (ViewGroup) view;
        nativoView = view.findViewById(R.id.nativo_holder);
        View loadAd = view.findViewById(R.id.load_ad);
        loadAd.setOnClickListener(loadClick);
        loadGAMAd();
    }

    private void loadGAMAd() {

        final AdSize ntvAdSize = new AdSize(3,3);
        mPublisherAdView = getView().findViewById(R.id.publisherAdView);
        mPublisherAdView.setAdSizes(ntvAdSize,AdSize.BANNER);

        // Create an ad request.
        // The ad unit Id is set on the PublisherAdView in the fragment_dfp.xml
        final PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addCustomTargeting("ntvPlacement","991150").build();

        mPublisherAdView.setAdListener(new AdListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("DFP","loaded adUnit: "+mPublisherAdView.getAdUnitId()+" adSize: "+mPublisherAdView.getAdSize());
                if(mPublisherAdView.getAdSize().equals(ntvAdSize) ) {
                    nativoView.setVisibility(View.VISIBLE);
                    //call nativo.dfp.bannerexample.sdk method here pass in the mAdView to parse out the html
                    NativoSDK.makeGAMRequestWithPublisherAdView(mPublisherAdView, DFP_SECTION_URL);
                }
                else{
                    Log.d("DFP", "Did receive DFP banner ad");
                }
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("DFP", "onAdFailedToLoad. errorCode: "+errorCode);
            }
        });

        mPublisherAdView.loadAd(adRequest);
    }

    View.OnClickListener loadClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NativoSDK.clearAds(DFP_SECTION_URL, parentView);
            loadGAMAd();
        }
    };

    @Override
    public void didReceiveAd(boolean didGetFill, @NonNull String inSection) {
        Log.d("DFP", "Ad loaded: "+didGetFill);
        if (didGetFill) {
            NativoSDK.placeAdInView(nativoView, parentView, DFP_SECTION_URL, 0, null);
        }
    }

    @Override
    public void didAssignAdToLocation(int location, @NonNull NtvAdData adData, @NonNull String inSection, @NonNull ViewGroup container) {
        Log.d("DFP", "Ad didAssignAdToLocation: "+location);
    }

    @Override
    public void didPlaceAdInView(@NonNull View view, @NonNull NtvAdData adData, @NonNull NtvInjectable injectable, int atLocation, @NonNull String inSection, @NonNull ViewGroup container) {
        Log.d("DFP", "didPlaceAdInView: "+adData);
    }

    @Override
    public void didFailAd(@NonNull String inSection, @Nullable Integer atLocation, @Nullable View inView, @Nullable ViewGroup container, @Nullable Throwable error) {
        Log.e("DFP", "Ad failed", error);
    }

    @Override
    public void needsDisplayLandingPage(@NonNull Intent landingPageIntent, @NonNull String inSection, @NonNull ViewGroup container) {
        startActivity(landingPageIntent);
    }

    @Override
    public void needsDisplayClickOutURL(@NonNull String url, @NonNull String inSection, @NonNull ViewGroup container) {

    }

    @Nullable
    @Override
    public Class<NtvInjectable> registerInjectableClassForTemplateType(@NonNull NtvInjectableType injectableType, @Nullable Integer atLocation, @Nullable String inSection) {
        return null;
    }
}
