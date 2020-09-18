package com.nativo.sampleapp.NativeAdImpl;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.nativo.sampleapp.ViewHolders.RecyclerListViewHolder;
import com.nativo.sampleapp.R;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.display.NtvStandardDisplayInterface;

public class StandardDisplayAdRecycler extends RecyclerListViewHolder implements NtvStandardDisplayInterface {

    private CardView layout;
    private WebView webView;
    private ImageView shareButton;
    private View view;

    @Override
    public void setShareAndTrackingUrl(final String shareUrl, final String trackUrl) {
        shareButton = (ImageView) view.findViewById(R.id.share_icon);
        shareButton.setOnClickListener(v -> {
            v.getContext().startActivity(Intent.createChooser(
                    new Intent(Intent.ACTION_SEND)
                            .setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, shareUrl), "Share to..."));
            NativoSDK.getInstance().trackShareAction(trackUrl);
        });
    }

    @Override
    public WebView getContentWebView() {
        return webView;
    }

    @Override
    public void contentWebViewOnPageFinished() {

    }

    @Override
    public void contentWebViewOnReceivedError(String s) {

    }

    @Override
    public int getLayout(Context context) {
        return R.layout.standard_display;
    }

    @Override
    public void bindViews(View v) {
        view = v;
        layout = v.findViewById(R.id.standard_display_layout);
        webView = v.findViewById(R.id.standard_display_webview);
    }

    @Override
    public View getAdContainerView() {
        return null;
    }

    public StandardDisplayAdRecycler(@NonNull View container, ViewGroup viewGroup) {
        super(container, viewGroup);
    }
}
