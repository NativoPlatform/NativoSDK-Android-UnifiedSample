package com.nativo.nativo_android_unifiedsample.ViewFragment;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.nativo.nativo_android_unifiedsample.R;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.NtvBaseInterface;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;

import static com.nativo.nativo_android_unifiedsample.util.AppConstants.DFP_SECTION_URL;

/*
* This sample use the Nativo DFP account
* Orders -> "DFP test"
* LineItem -> "Mobile Test Line Item"
* Creative -> "Nativo Tag Creative"
* Campaign ID "c" -> 114921*/
public class DfpFragment extends Fragment implements NtvSectionAdapter {

    PublisherAdView mPublisherAdView;
    private NativoSDK mNativoSDK;
    AdLoader mAdLoader;
    String mMesssage;
    DfpFragment fragmentAdapter;
    View nativoView;
    ViewGroup parentView;

    public DfpFragment() {
        fragmentAdapter = this;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_dfp, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parentView = (ViewGroup) view.getParent();
        nativoView = view.findViewById(R.id.article_constraint_layout);
        nativoView.setVisibility(View.INVISIBLE);
        NativoSDK.getInstance().enableDFPRequestsWithVersion("17.0.0");
        View loadAd = view.findViewById(R.id.load_ad);
        loadAd.setOnClickListener(loadClick);
        loadDfpAd();
    }

    private void loadDfpAd() {
        mPublisherAdView = getView().findViewById(R.id.publisherAdView);
        final AdSize ntvAdSize = new AdSize(3,3);
        mPublisherAdView.setAdSizes(ntvAdSize,AdSize.BANNER);
        // Create an ad request.
        final PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addCustomTargeting("ntvPlacement","991150").build();

        mPublisherAdView.setAdListener(new AdListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("DFP","adUnit: "+mPublisherAdView.getAdUnitId()+" adSize: "+mPublisherAdView.getAdSize());
                if(mPublisherAdView.getAdSize().equals(ntvAdSize) ) {
                    //call nativo.dfp.bannerexample.sdk method here pass in the mAdView to parse out the html
                    NativoSDK.getInstance().makeDFPRequestWithPublisherAdView(mPublisherAdView, DFP_SECTION_URL, 0, fragmentAdapter);
                }
                else{
                    Log.d("DFP", "Did receive DFP banner ad");
                }
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("DFP", "onAdFailedToLoad: errorCode: "+errorCode);
            }
        });

        mPublisherAdView.loadAd(adRequest);
    }

    @Override
    public boolean shouldPlaceAdAtIndex(String s, int i) {
        return true;
    }

    @Override
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

    @Override
    public void needsDisplayLandingPage(String s, int i) {

    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {

    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String s, int i, NtvAdData ntvAdData) {
        Log.d("DFP", "Ad loaded");
        nativoView.setVisibility(View.VISIBLE);
        NativoSDK.getInstance().placeAdInView(nativoView, parentView, DFP_SECTION_URL, i, fragmentAdapter, null);
    }

    @Override
    public void onFail(String s, int i) {
        Log.d("DFP", "Ad load failed");
    }

    View.OnClickListener loadClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loadDfpAd();
        }
    };
}
