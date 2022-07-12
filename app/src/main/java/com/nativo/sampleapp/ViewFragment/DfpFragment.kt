package com.nativo.sampleapp.ViewFragment;


import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.nativo.sampleapp.activities.SponsoredContentActivity;
import com.nativo.sampleapp.R;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.NtvBaseInterface;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;

import static com.nativo.sampleapp.util.AppConstants.DFP_SECTION_URL;
import static com.nativo.sampleapp.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.sampleapp.util.AppConstants.SP_CONTAINER_HASH;
import static com.nativo.sampleapp.util.AppConstants.SP_SECTION_URL;

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
    View nativoVideoView;
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
        parentView = (ViewGroup) view;
        nativoView = view.findViewById(R.id.article_constraint_layout);
        nativoView.setVisibility(View.GONE);
        nativoVideoView = view.findViewById(R.id.video_constraint_layout);
        nativoVideoView.setVisibility(View.GONE);
        NativoSDK.initWithGAMVersion(this.getContext(), "17.0.0");
        View loadAd = view.findViewById(R.id.load_ad);
        loadAd.setOnClickListener(loadClick);
        loadGAMAd();
    }

    private void loadGAMAd() {
        mPublisherAdView = getView().findViewById(R.id.publisherAdView);
        final AdSize ntvAdSize = new AdSize(3,3);
        mPublisherAdView.setAdSizes(ntvAdSize,AdSize.BANNER);
        // Create an ad request.
        final PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addCustomTargeting("ntvPlacement","1092187").build();

        mPublisherAdView.setAdListener(new AdListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("DFP","adUnit: "+mPublisherAdView.getAdUnitId()+" adSize: "+mPublisherAdView.getAdSize());
                if(mPublisherAdView.getAdSize().equals(ntvAdSize) ) {
                    //call nativo.dfp.bannerexample.sdk method here pass in the mAdView to parse out the html
                    NativoSDK.makeGAMRequestWithPublisherAdView(mPublisherAdView, parentView,DFP_SECTION_URL, 0, fragmentAdapter);
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
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

    @Override
    public void needsDisplayLandingPage(String s, int i) {
        getContext().startActivity(new Intent(getContext(), SponsoredContentActivity.class)
                .putExtra(SP_SECTION_URL, s)
                .putExtra(SP_CAMPAIGN_ID, i)
                .putExtra(SP_CONTAINER_HASH, parentView.hashCode()));
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {
        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s1)));
    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String s, NtvAdData ntvAdData, Integer integer) {
        Log.d("DFP", "Ad loaded");
        if (ntvAdData.getAdType() == NtvAdData.NtvAdType.NATIVE || ntvAdData.getAdType() == NtvAdData.NtvAdType.CLICK_OUT) {
            nativoView.setVisibility(View.VISIBLE);
            NativoSDK.placeAdInView(nativoView, parentView, DFP_SECTION_URL, 0, fragmentAdapter, null);
        } else if (ntvAdData.getAdType() == NtvAdData.NtvAdType.IN_FEED_VIDEO || ntvAdData.getAdType() == NtvAdData.NtvAdType.IN_FEED_AUTO_PLAY_VIDEO) {
            nativoVideoView.setVisibility(View.VISIBLE);
            NativoSDK.placeAdInView(nativoVideoView, parentView, DFP_SECTION_URL, 0, fragmentAdapter, null);
        }
    }

    @Override
    public void onFail(String s, Integer integer) {
        Log.d("DFP", "Ad load failed");
    }

    View.OnClickListener loadClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loadGAMAd();
        }
    };
}
