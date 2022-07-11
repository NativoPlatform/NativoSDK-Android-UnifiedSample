package com.nativo.sampleapp.NativeAdLandingImpl

import android.content.Context
import net.nativo.sdk.ntvadtype.landing.NtvLandingPageInterface
import android.webkit.WebView
import android.widget.TextView
import android.view.ViewGroup
import com.nativo.sampleapp.R
import android.content.Intent
import android.view.View
import android.widget.ImageView
import net.nativo.sdk.NativoSDK
import java.util.*

class NativeLandingPage : NtvLandingPageInterface {
    private var webView: WebView? = null
    private var titleLabel: TextView? = null
    private var authorNameLabel: TextView? = null
    private var adContainerView: View? = null
    private var articleAuthorImage: ImageView? = null
    private val boapIndex = 0
    private var shareButton: ImageView? = null
    private var scrollView: ViewGroup? = null

    override fun getContentWebView(): WebView? {
        return webView
    }

    override fun getTitleLabel(): TextView? {
        return titleLabel
    }

    override fun getAuthorNameLabel(): TextView? {
        return authorNameLabel
    }

    override fun getAuthorImageView(): ImageView? {
        return articleAuthorImage
    }

    override fun getPreviewImageView(): ImageView? {
        return null
    }

    override fun getPreviewTextLabel(): TextView? {
        return null
    }

    override fun getDateLabel(): TextView? {
        return null
    }

    override fun formatDate(date: Date): String? {
        return null
    }

    override fun contentWebViewShouldScroll(): Boolean {
        return false
    }

    override fun contentWebViewOnPageFinished() {}
    override fun contentWebViewOnReceivedError(s: String) {}
    override fun getLayout(context: Context): Int {
        return R.layout.activity_sponsored_content
    }

    override fun setShareAndTrackingUrl(shareUrl: String, adUUID: String) {
        val view = adContainerView?.findViewById<View>(R.id.share_icon)
        if (view is ImageView) {
            shareButton = view
            shareButton?.setOnClickListener { v: View ->
                v.context.startActivity(
                    Intent.createChooser(
                        Intent(Intent.ACTION_SEND)
                            .setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, shareUrl), "Share to..."
                    )
                )
                NativoSDK.trackShareAction(adUUID)
            }
        }
    }

    override fun bindViews(v: View) {
        adContainerView = v
        webView = v.findViewById(R.id.web_view)
        titleLabel = v.findViewById(R.id.title_label)
        authorNameLabel = v.findViewById(R.id.article_author)
        articleAuthorImage = v.findViewById(R.id.article_author_image)
        scrollView = adContainerView?.findViewById(R.id.landing_boap_container)
    }

    override fun getAdContainerView(): View? {
        return adContainerView
    }
}