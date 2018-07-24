package com.nativo.nativo_android_unifiedsample.ViewFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.nativo_android_unifiedsample.R;
import com.nativo.nativo_android_unifiedsample.SponsoredContentActivity;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.NtvBaseInterface;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;

public class SingleViewFragment extends Fragment implements NtvSectionAdapter {

    private static String SECTION_URL = "http://www.nativo.net/test/";
    private View convertView;

    public SingleViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        convertView = view.findViewById(R.id.article_container);
        if (!getAd()) {
            bindView(view, 0);
        }
        view.findViewById(R.id.load_ad).setOnClickListener(loadAd);
        view.findViewById(R.id.show_ad).setOnClickListener(showAd);
        view.findViewById(R.id.hide_ad).setOnClickListener(hideAd);
    }

    private boolean getAd() {
        return NativoSDK.getInstance().placeAdInView(convertView, (ViewGroup) getView(), SECTION_URL, 0, this, null);
    }

    private void bindView(View view, int i) {
        if (view != null) {
            if (((ImageView) view.findViewById(R.id.article_image)) != null) {
                ((ImageView) view.findViewById(R.id.article_image)).setImageResource(R.drawable.newsimage);
            }
            if (((ImageView) view.findViewById(R.id.video_sponsored_indicator)) != null) {
                ((ImageView) view.findViewById(R.id.video_sponsored_indicator)).setVisibility(View.INVISIBLE);
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
            if (shouldPlaceAdAtIndex("sample", i)) {
                view.findViewById(R.id.article_container).setBackgroundColor(Color.RED);
            } else {
                view.findViewById(R.id.article_container).setBackgroundColor(Color.WHITE);
            }
        }
    }

    View.OnClickListener loadAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NativoSDK.getInstance().clearAdsInSection(SECTION_URL);
            if (!getAd()) {
                bindView(getView(), 0);
            }
        }
    };

    View.OnClickListener hideAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            convertView.setVisibility(View.INVISIBLE);
        }
    };

    View.OnClickListener showAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            convertView.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public boolean shouldPlaceAdAtIndex(String s, int i) {
        return true;
    }

    @Override
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

    @Override
    public void needsReloadDataSource(String s, int i) {

    }

    @Override
    public void needsDisplayLandingPage(String s, int i) {
        getView().getContext().startActivity(new Intent(getContext(), SponsoredContentActivity.class)
                .putExtra(SponsoredContentActivity.SECTION_URL, s)
                .putExtra(SponsoredContentActivity.CAMPAIGN_ID, i));
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {

    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String s, NtvAdData ntvAdData) {

    }

    @Override
    public void onFail(String s, Exception e) {

    }
}
