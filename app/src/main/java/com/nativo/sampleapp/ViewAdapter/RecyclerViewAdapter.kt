package com.nativo.sampleapp.ViewAdapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nativo.sampleapp.NativeAdImpl.NativeAdRecycler
import com.nativo.sampleapp.NativeAdImpl.NativeVideoAdRecycler
import com.nativo.sampleapp.NativeAdImpl.StandardDisplayAdRecycler
import com.nativo.sampleapp.R
import com.nativo.sampleapp.SponsoredContentActivity
import com.nativo.sampleapp.ViewHolders.RecyclerListViewHolder
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.ntvadtype.NtvBaseInterface
import net.nativo.sdk.ntvconstant.NativoAdType
import net.nativo.sdk.ntvcore.NtvAdData
import net.nativo.sdk.ntvcore.NtvAdData.NtvAdTemplateType
import net.nativo.sdk.ntvcore.NtvSectionAdapter

class RecyclerViewAdapter(private val context: Context, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<RecyclerListViewHolder>(), NtvSectionAdapter {

    companion object {
        private val TAG = RecyclerViewAdapter::class.java.name
    }

    private var integerList: MutableList<Int> = ArrayList()
    private var adsRequestIndex: MutableSet<Int> = HashSet()
    private var onClickListener = View.OnClickListener {
        it.context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }

    init {
        for (i in 0..19) {
            integerList.add(i)
        }
        //NativoSDK.prefetchAdForSection(SECTION_URL, this, null);
    }

    // Helper method to determine which indexes should be Nativo ads
    private fun shouldPlaceNativoAdAtIndex(i: Int): Boolean {
        return i % 2 == 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (shouldPlaceNativoAdAtIndex(position)) {
            when (NativoSDK.getAdTypeForIndex(AppConstants.SECTION_URL, recyclerView, position)) {
                NativoAdType.AD_TYPE_NATIVE -> 1
                NativoAdType.AD_TYPE_VIDEO -> 2
                NativoAdType.AD_TYPE_STANDARD_DISPLAY -> 3
                else -> 0 // Publisher item view, in case of no ad fill
            }
        } else {
            // Publisher item view
            0
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewTypeInt: Int
    ): RecyclerListViewHolder {
        val viewHolder: RecyclerListViewHolder
        val adViewTry: View
        when (viewTypeInt) {
            1 -> { // Nativo Article
                adViewTry = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.native_article, viewGroup, false)
                viewHolder = NativeAdRecycler(adViewTry, viewGroup)
            }
            2 -> { // Nativo Video
                adViewTry = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.video_layout, viewGroup, false)
                viewHolder = NativeVideoAdRecycler(adViewTry, viewGroup)
            }
            3 -> { // Nativo Banner Ad
                adViewTry = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.standard_display, viewGroup, false)
                viewHolder = StandardDisplayAdRecycler(adViewTry, viewGroup)
            }
            else -> { // Publisher Article Layout
                adViewTry = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.publisher_article, viewGroup, false)
                viewHolder = RecyclerListViewHolder(adViewTry, viewGroup)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(listViewHolder: RecyclerListViewHolder, i: Int) {
        val view = listViewHolder.container
        var isAdContentAvailable = false
        if (shouldPlaceNativoAdAtIndex(i)) {
            Log.e(TAG, "binding index: $i")
            adsRequestIndex.add(i)
            isAdContentAvailable =
                NativoSDK.placeAdInView(view, recyclerView, AppConstants.SECTION_URL, i, this, null)
        }
        if (!isAdContentAvailable) {
            bindView(listViewHolder.container, i)
        }
    }

    private fun bindView(view: View, i: Int) {
        if (view.findViewById<View?>(R.id.article_image) != null) {
            (view.findViewById<View>(R.id.article_image) as ImageView).setImageResource(R.drawable.newsimage)
        }
        if (view.findViewById<View?>(R.id.sponsored_ad_indicator) != null) {
            view.findViewById<View>(R.id.sponsored_ad_indicator).visibility =
                View.INVISIBLE
        }
        if (view.findViewById<View?>(R.id.article_author) != null) {
            (view.findViewById<View>(R.id.article_author) as TextView).setText(R.string.sample_author)
        }
        if (view.findViewById<View?>(R.id.article_title) != null) {
            (view.findViewById<View>(R.id.article_title) as TextView).setText(R.string.sample_title)
        }
        if (view.findViewById<View?>(R.id.article_description) != null) {
            (view.findViewById<View>(R.id.article_description) as TextView).setText(R.string.sample_description)
        }
        if (view.findViewById<View?>(R.id.sponsored_tag) != null) {
            view.findViewById<View>(R.id.sponsored_tag).visibility = View.INVISIBLE
        }

        view.setOnClickListener(onClickListener)
    }

    override fun getItemCount(): Int {
        return integerList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onViewRecycled(holder: RecyclerListViewHolder) {
        if (holder is NativeVideoAdRecycler) {
            val textureView = holder.textureView
            (holder.itemView as ViewGroup).removeView(textureView)
        }
        super.onViewRecycled(holder)
    }

    /**
     * NtvSectionAdapter implementation
     */
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
                .putExtra(AppConstants.SP_CONTAINER_HASH, recyclerView.hashCode())
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
        Log.e(this.javaClass.name, "Index: $index Did receive ad: $ntvAdData")
        integerList.add(index)
        notifyDataSetChanged()
    }

    override fun onFail(section: String, index: Int) {
        // Remove failed Nativo ads
        val adTypeForIndex = NativoSDK.getAdTypeForIndex(AppConstants.SECTION_URL, recyclerView, index)
        if (adTypeForIndex == NativoAdType.AD_TYPE_NONE) {
            integerList.remove(index)
            notifyItemRemoved(index)
            notifyItemChanged(index)
        }
    }
}