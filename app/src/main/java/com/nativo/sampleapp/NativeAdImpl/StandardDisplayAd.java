package com.nativo.sampleapp.NativeAdImpl;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import androidx.cardview.widget.CardView;
import com.nativo.sampleapp.R;
import net.nativo.sdk.injector.display.NtvStandardDisplayInjectable;

public class StandardDisplayAd implements NtvStandardDisplayInjectable {

    private CardView layout;
    private WebView webView;
    private View view;

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
}
