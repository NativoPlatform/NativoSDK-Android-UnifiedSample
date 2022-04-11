package com.nativo.sampleapp.ViewFragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.sampleapp.SponsoredContentActivity;
import com.nativo.sampleapp.R;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.NtvIntent;
import net.nativo.sdk.NtvNotificationAdapter;
import net.nativo.sdk.adtype.NtvBaseInterface;
import net.nativo.sdk.NtvAdData;

import static com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL;
import static com.nativo.sampleapp.util.AppConstants.SECTION_URL;
import static com.nativo.sampleapp.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.sampleapp.util.AppConstants.SP_CONTAINER;

public class SingleViewFragment extends Fragment implements NtvNotificationAdapter {

    private View nativoAdView;
    SingleViewFragment viewFragment = null;

    public SingleViewFragment() {
        viewFragment = this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NativoSDK.prefetchAdForSection(SECTION_URL, null);
        View view = inflater.inflate(R.layout.fragment_single_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Nativo init
        NativoSDK.setNotificationAdapterForSection(SECTION_URL, this, getContext());
        nativoAdView = view.findViewById(R.id.article_container);

        view.findViewById(R.id.load_ad).setOnClickListener(loadAd);
        view.findViewById(R.id.show_ad).setOnClickListener(showAd);
        view.findViewById(R.id.hide_ad).setOnClickListener(hideAd);
    }

    private void bindView(View view, int i) {
        if (view != null) {
            if (((ImageView) view.findViewById(R.id.article_image)) != null) {
                ((ImageView) view.findViewById(R.id.article_image)).setImageResource(R.drawable.newsimage);
            }
            if (((ImageView) view.findViewById(R.id.sponsored_ad_indicator)) != null) {
                ((ImageView) view.findViewById(R.id.sponsored_ad_indicator)).setVisibility(View.INVISIBLE);
            }
            if (((TextView) view.findViewById(R.id.article_author)) != null) {
                ((TextView) view.findViewById(R.id.article_author)).setText(R.string.sample_author);
            }
            if (((TextView) view.findViewById(R.id.article_title)) != null) {
                ((TextView) view.findViewById(R.id.article_title)).setText(R.string.sample_title);
            }
            if (((TextView) view.findViewById(R.id.sponsored_tag)) != null) {
                ((TextView) view.findViewById(R.id.sponsored_tag)).setVisibility(View.INVISIBLE);
            }
            view.findViewById(R.id.article_container).setBackgroundColor(Color.RED);
            view.setOnClickListener(onClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CLICK_OUT_URL)));
        }
    };

    View.OnClickListener loadAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NativoSDK.clearAdsInSection(SECTION_URL, (ViewGroup) getView());
            NativoSDK.prefetchAdForSection(SECTION_URL, null);
        }
    };

    View.OnClickListener hideAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nativoAdView.setVisibility(View.INVISIBLE);
        }
    };

    View.OnClickListener showAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nativoAdView.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

//    @Override
//    public void needsDisplayLandingPage(String s, int i) {
//        getView().getContext().startActivity(new Intent(getContext(), SponsoredContentActivity.class)
//                .putExtra(SP_CAMPAIGN_ID, s)
//                .putExtra(SP_CAMPAIGN_ID, i)
//                .putExtra(SP_CONTAINER, getView().hashCode()));
//    }

    @Override
    public void needsDisplayLandingPage(String sectionUrl, NtvIntent landingPageIntent) {
        getView().getContext().startActivity(landingPageIntent);
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {

    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String section, NtvAdData ntvAdData, Integer index) {
        Log.e(this.getClass().getName(), "Did receive ad: "+ntvAdData);
        boolean didGetNativoAd = NativoSDK.placeAdInView(nativoAdView, (ViewGroup) getView(), SECTION_URL, 0, null);
        if (didGetNativoAd) {
            nativoAdView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFail(String section, Integer index) {
        nativoAdView.setVisibility(View.GONE);
    }
}
