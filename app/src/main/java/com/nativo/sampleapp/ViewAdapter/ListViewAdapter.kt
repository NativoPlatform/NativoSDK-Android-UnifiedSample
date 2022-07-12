package com.nativo.sampleapp.ViewAdapter

import android.view.ViewGroup
import android.widget.BaseAdapter
import net.nativo.sdk.ntvcore.NtvSectionAdapter
import android.view.LayoutInflater
import com.nativo.sampleapp.R
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.ntvconstant.NativoAdType
import android.widget.TextView
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import net.nativo.sdk.ntvcore.NtvAdData.NtvAdTemplateType
import com.nativo.sampleapp.activities.SponsoredContentActivity
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.ntvadtype.NtvBaseInterface
import net.nativo.sdk.ntvcore.NtvAdData

/**
 * Example of Nativo SDK implemented using ListView
 * Ads are placed according to rule in link`shouldPlaceAdAtIndex()`.
 * If an ad is not placed(eg no fill scenario) the cell is marked with red
 */
class ListViewAdapter(private val listView: ViewGroup) : BaseAdapter(), NtvSectionAdapter {

    private var onClickListener = View.OnClickListener { view ->
        view.context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }

    override fun getCount(): Int {
        return 20
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var myView = view
        if (myView == null) {
            myView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.publisher_article, viewGroup, false)
        }
        if (NativoSDK.getAdTypeForIndex(AppConstants.SECTION_URL, listView, i) == NativoAdType.AD_TYPE_VIDEO) {
            myView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.video_layout, viewGroup, false)
        } else if (NativoSDK.getAdTypeForIndex(
                AppConstants.SECTION_URL,
                listView,
                i
            ) == NativoAdType.AD_TYPE_NATIVE
        ) {
            myView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.native_article, viewGroup, false)
        }
        val ad = NativoSDK.placeAdInView(myView, listView, AppConstants.SECTION_URL, i, this, null)
        if (!ad) {
            myView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.native_article, viewGroup, false)
            bindView(myView, i)
        }

        return myView!!
    }

    private fun bindView(view: View, i: Int) {
        val articleImage: ImageView? = view.findViewById(R.id.article_image)
        val sponsoredAdIndicator: ImageView? = view.findViewById(R.id.sponsored_ad_indicator)
        val articleAuthor: TextView? = view.findViewById(R.id.article_author)
        val articleTitle: TextView? = view.findViewById(R.id.article_title)
        val sponsoredTag: TextView? = view.findViewById(R.id.sponsored_tag)

        articleImage?.setImageResource(R.drawable.newsimage)
        sponsoredAdIndicator?.visibility = View.INVISIBLE
        articleAuthor?.setText(R.string.sample_author)
        articleTitle?.setText(R.string.sample_title)
        sponsoredTag?.visibility = View.INVISIBLE

        if (shouldPlaceNativoAdAtIndex(i)) {
            view.findViewById<View>(R.id.article_constraint_layout)
                .setBackgroundColor(Color.RED)
        } else {
            view.findViewById<View>(R.id.article_constraint_layout)
                .setBackgroundColor(Color.WHITE)
        }
        view.setOnClickListener(onClickListener)
    }

    fun shouldPlaceNativoAdAtIndex(i: Int): Boolean {
        return i % 3 == 0
    }

    override fun registerLayoutClassForIndex(
        i: Int,
        ntvAdTemplateType: NtvAdTemplateType
    ): Class<*>? {
        return null
    }

    override fun needsDisplayLandingPage(s: String, i: Int) {
        listView.context.startActivity(
            Intent(listView.context, SponsoredContentActivity::class.java)
                .putExtra(AppConstants.SP_SECTION_URL, s)
                .putExtra(AppConstants.SP_CAMPAIGN_ID, i)
                .putExtra(AppConstants.SP_CONTAINER_HASH, listView.hashCode())
        )
    }

    override fun needsDisplayClickOutURL(s: String, s1: String) {
        listView.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(s1)))
    }

    override fun hasbuiltView(
        view: View,
        ntvBaseInterface: NtvBaseInterface,
        ntvAdData: NtvAdData
    ) {
    }

    override fun onReceiveAd(section: String, ntvAdData: NtvAdData, index: Int) {
        notifyDataSetChanged()
    }

    override fun onFail(section: String, index: Int) {
        notifyDataSetChanged()
    }
}