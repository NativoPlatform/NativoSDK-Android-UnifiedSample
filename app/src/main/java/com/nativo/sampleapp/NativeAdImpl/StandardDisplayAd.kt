package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.view.View
import android.webkit.WebView
import androidx.cardview.widget.CardView
import com.nativo.sampleapp.R
import net.nativo.sdk.injectable.NtvStandardDisplayInjectable

class StandardDisplayAd : NtvStandardDisplayInjectable {

    private lateinit var _view: View
    private var layout: CardView? = null
    private lateinit var _webView: WebView

    override val contentWebView: WebView
        get() = _webView

    override val view: View
        get() = _view

    override fun contentWebViewOnPageFinished() {}
    override fun contentWebViewOnReceivedError(description: String?) {}

    override fun getLayout(context: Context): Int {
        return R.layout.standard_display
    }

    override fun bindViews(v: View) {
        _view = v
        layout = v.findViewById(R.id.standard_display_layout)
        _webView = v.findViewById(R.id.standard_display_webview)
    }
}