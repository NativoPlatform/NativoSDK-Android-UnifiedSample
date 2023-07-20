package com.nativo.sampleapp.ad_injectables

import android.content.Context
import android.view.View
import android.webkit.WebView
import com.nativo.sampleapp.R
import net.nativo.sdk.injectable.NtvStandardDisplayInjectable

class StandardDisplayAd : NtvStandardDisplayInjectable {

    override lateinit var contentWebView: WebView
    override lateinit var view: View

    override fun getLayout(context: Context): Int {
        return R.layout.standard_display
    }

    override fun bindViews(v: View) {
        view = v
        contentWebView = v.findViewById(R.id.standard_display_webview)
    }

    override fun contentWebViewOnPageFinished() {}
    override fun contentWebViewOnReceivedError(description: String?) {
        println("contentWebViewOnReceivedError: $description")
    }
}