package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.nativo.sampleapp.ViewHolders.RecyclerListViewHolder
import net.nativo.sdk.ntvadtype.display.NtvStandardDisplayInterface
import androidx.cardview.widget.CardView
import android.webkit.WebView
import com.nativo.sampleapp.R

class StandardDisplayAdRecycler(container: View, viewGroup: ViewGroup) :
    RecyclerListViewHolder(container, viewGroup), NtvStandardDisplayInterface {

    private var layout: CardView? = null
    private var webView: WebView? = null
    private var view: View? = null
    
    override fun getContentWebView(): WebView? {
        return webView
    }

    override fun contentWebViewOnPageFinished() {}
    override fun contentWebViewOnReceivedError(s: String) {}
    override fun getLayout(context: Context): Int {
        return R.layout.standard_display
    }

    override fun bindViews(v: View) {
        view = v
        layout = v.findViewById(R.id.standard_display_layout)
        webView = v.findViewById(R.id.standard_display_webview)
    }

    override fun getAdContainerView(): View? {
        return null
    }
}