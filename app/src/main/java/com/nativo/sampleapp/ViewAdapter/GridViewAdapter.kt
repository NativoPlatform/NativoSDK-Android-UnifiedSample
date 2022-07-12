package com.nativo.sampleapp.ViewAdapter

import android.content.Context
import android.widget.GridView
import android.widget.BaseAdapter
import net.nativo.sdk.ntvcore.NtvSectionAdapter
import android.view.ViewGroup
import net.nativo.sdk.ntvconstant.NativoAdType
import net.nativo.sdk.NativoSDK
import android.view.LayoutInflater
import com.nativo.sampleapp.R
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
import java.util.ArrayList

class GridViewAdapter(private val context: Context, private val gridView: GridView) : BaseAdapter(),
    NtvSectionAdapter {
    private val integerList: MutableList<Int> = ArrayList()

    init {
        for (i in 0..9) {
            integerList.add(i)
        }
    }

    override fun getCount(): Int {
        return integerList.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val myView: View
        if (shouldPlaceNativoAdAtIndex(i)) {
            val adType = NativoSDK.getAdTypeForIndex(AppConstants.SECTION_URL, gridView, i)
            myView = when (adType) {
                NativoAdType.AD_TYPE_NATIVE -> LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.native_article, viewGroup, false)
                NativoAdType.AD_TYPE_VIDEO -> LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.video_layout, viewGroup, false)
                NativoAdType.AD_TYPE_STANDARD_DISPLAY -> LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.standard_display, viewGroup, false)
                else -> LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.native_article, viewGroup, false)
            }
            val isNativoAdAvailable =
                NativoSDK.placeAdInView(myView, gridView, AppConstants.SECTION_URL, i, this, null)

            // Hide if ad could not be placed in view
            if (!isNativoAdAvailable) {
                myView.visibility = View.GONE
            } else {
                myView.visibility = View.VISIBLE
            }
        } else {
            // Publisher article view
            myView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.publisher_article, viewGroup, false)
            bindView(myView, i)
        }

        return myView
    }

    private fun bindView(view: View, i: Int) {
        val articleImage: ImageView? = view.findViewById(R.id.article_image)
        val sponsoredAdIndicator: ImageView? = view.findViewById(R.id.sponsored_ad_indicator)
        val articleAuthor: TextView? = view.findViewById(R.id.article_author)
        val articleTitle: TextView? = view.findViewById(R.id.article_title)

        articleImage?.setImageResource(R.drawable.newsimage)
        sponsoredAdIndicator?.visibility = View.INVISIBLE
        articleAuthor?.setText(R.string.sample_author)
        articleTitle?.setText(R.string.sample_title)

        if (shouldPlaceNativoAdAtIndex(i)) {
            view.findViewById<View>(R.id.article_constraint_layout)
                .setBackgroundColor(Color.RED)
        } else {
            view.findViewById<View>(R.id.article_constraint_layout)
                .setBackgroundColor(Color.WHITE)
        }
        view.setOnClickListener(onClickListener)
    }

    private var onClickListener = View.OnClickListener { view ->
        view.context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }

    private fun shouldPlaceNativoAdAtIndex(i: Int): Boolean {
        return i % 2 == 1
    }

    override fun registerLayoutClassForIndex(
        i: Int,
        ntvAdTemplateType: NtvAdTemplateType
    ): Class<*>? {
        return null
    }

    override fun needsDisplayLandingPage(s: String, i: Int) {
        context.startActivity(
            Intent(context, SponsoredContentActivity::class.java)
                .putExtra(AppConstants.SP_SECTION_URL, s)
                .putExtra(AppConstants.SP_CAMPAIGN_ID, i)
                .putExtra(AppConstants.SP_CONTAINER_HASH, gridView.hashCode())
        )
    }

    override fun needsDisplayClickOutURL(s: String, s1: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(s1)))
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