package com.nativo.sampleapp.ViewFragment;


import static com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nativo.sampleapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleViewVideoFragment extends Fragment  {
    private View convertView;
    SingleViewVideoFragment viewFragment;

    public SingleViewVideoFragment() {
        viewFragment = this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_view_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        convertView = view.findViewById(R.id.layout);

        bindView(view, 0);
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
            if (view.findViewById(R.id.article_container) != null) {
                    view.findViewById(R.id.article_container).setBackgroundColor(Color.RED);
            } else {
                if (view.findViewById(R.id.article_container) != null) {
                    view.findViewById(R.id.article_container).setBackgroundColor(Color.WHITE);
                }
            }
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


}
