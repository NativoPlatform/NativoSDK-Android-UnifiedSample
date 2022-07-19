package com.nativo.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nativo.sampleapp.R
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.injectable.NtvLandingPageInjectable
import java.util.*

class SponsoredContentActivity : AppCompatActivity(), NtvLandingPageInjectable {

    private var withView = true

    /**
     * NtvLandingPageInterface
     */
    private lateinit var _webView: WebView
    private var _titleLabel: TextView? = null
    private var _authorNameLabel: TextView? = null
    private var _articleAuthorImage: ImageView? = null
    private var shareButton: ImageView? = null
    private lateinit var _view: View

    override val authorImageView: ImageView?
        get() = _articleAuthorImage
    override val authorNameLabel: TextView?
        get() = _authorNameLabel
    override val contentWebView: WebView
        get() = _webView
    override val dateLabel: TextView? = null
    override val previewImageView: ImageView? = null
    override val previewTextLabel: TextView? = null
    override val titleLabel: TextView?
        get() = _titleLabel
    override val view: View
        get() = _view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NativoSDK.initLandingPage(this)
    }

    override fun bindViews(v: View) {
        _view = v
        _webView = v.findViewById(R.id.web_view)
        _titleLabel = v.findViewById(R.id.title_label)
        _authorNameLabel = v.findViewById(R.id.article_author)
        _articleAuthorImage = v.findViewById(R.id.article_author_image)
    }

    override fun contentWebViewOnPageFinished() {
    }

    override fun contentWebViewOnReceivedError(description: String?) {
    }

    override fun contentWebViewShouldScroll(): Boolean {
        return false
    }

    override fun formatDate(date: Date?): String? {
        return null
    }

    override fun getLayout(context: Context): Int {
        return R.layout.activity_sponsored_content
    }

    override fun setShareAndTrackingUrl(shareUrl: String?, adUUID: String?) {
        shareButton = findViewById<View>(R.id.share_icon) as ImageView
        shareButton?.let {
            it.setOnClickListener {
                startActivity(
                    Intent.createChooser(
                        Intent(Intent.ACTION_SEND)
                            .setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, shareUrl), "Share to..."
                    )
                )
            }
        }
    }
}