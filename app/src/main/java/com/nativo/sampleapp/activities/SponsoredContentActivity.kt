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

    /**
     * NtvLandingPageInterface
     */
    override lateinit var view: View
    override lateinit var titleLabel: TextView
    override lateinit var authorImageView: ImageView
    override lateinit var authorNameLabel: TextView
    override lateinit var contentWebView: WebView
    override val dateLabel: TextView? = null
    override val previewImageView: ImageView? = null
    override val previewTextLabel: TextView? = null
    private var shareButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NativoSDK.initLandingPage(this)
    }

    override fun getLayout(context: Context): Int {
        return R.layout.activity_sponsored_content
    }

    override fun bindViews(v: View) {
        view = v
        contentWebView = v.findViewById(R.id.web_view)
        titleLabel = v.findViewById(R.id.title_label)
        authorNameLabel = v.findViewById(R.id.article_author)
        authorImageView = v.findViewById(R.id.article_author_image)
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