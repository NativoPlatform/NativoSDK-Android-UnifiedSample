package com.nativo.sampleapp.ViewAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.ViewHolders.ArticleViewHolder
import com.nativo.sampleapp.util.AppConstants
import com.nativo.sampleapp.util.AppConstants.NtvTAG
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.NativoViewHolder
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.NtvSectionAdapter
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType

class RecyclerViewAdapter(private val context: Context, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), NtvSectionAdapter {

    companion object {
        private const val ITEM_COUNT = 40

        // Item View Type constants
        private const val ITEM_TYPE_ARTICLE = 0
        private const val ITEM_TYPE_NATIVO = 1
    }

    private val articleList: ArrayList<String> = ArrayList()
    private var adsRequestIndex: MutableSet<Int> = HashSet()
    private var initialNativoRequest = true
    private var onClickListener = View.OnClickListener {
        it.context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }

    init {
        NativoSDK.initSectionWithAdapter(this, AppConstants.SECTION_URL, context)

        for ( i in 0 until ITEM_COUNT) {
            if (shouldPlaceNativoAdAtIndex(i)) {
                val title = "Nativo Placeholder $i"
                articleList.add(title)
            } else {
                val title = "Publisher Article $i"
                articleList.add(title)
            }
        }
    }

    // Helper method to determine which indexes should be Nativo ads
    private fun shouldPlaceNativoAdAtIndex(i: Int): Boolean {
        return i % 3 == 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (articleList[position].contains("Nativo")) {
            ITEM_TYPE_NATIVO
        } else {
            ITEM_TYPE_ARTICLE
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_ARTICLE -> {
                Log.d(NtvTAG, "Created new ArticleViewHolder")
                val view: View = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.publisher_article, viewGroup, false)
                ArticleViewHolder(view, viewGroup)
            }
            ITEM_TYPE_NATIVO -> {
                Log.d(NtvTAG, "Created new NativoViewHolder")
                NativoViewHolder(viewGroup.context)
            }
            else -> {
                Log.d(NtvTAG, "Created new ArticleViewHolder")
                val view: View = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.publisher_article, viewGroup, false)
                ArticleViewHolder(view, viewGroup)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var isAdContentAvailable = false
        if (holder is NativoViewHolder) {
            isAdContentAvailable =
                NativoSDK.placeAdInView(holder.itemView, recyclerView, AppConstants.SECTION_URL, position, null)
            Log.d(NtvTAG, "placing ad at position $position, available: $isAdContentAvailable")
        } else if (holder is ArticleViewHolder) {
            val articleTitle = articleList[position]
            holder.bindData(position, articleTitle)
        }

        Log.d("onBindViewHolder", "At pos: $position")
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun didAssignAdToLocation(
        location: Int,
        adData: NtvAdData,
        inSection: String,
        container: ViewGroup
    ) {
        Log.d(NtvTAG, "didAssignAdToLocation: $location")
        notifyItemChanged(location)
    }

    override fun didFailAd(
        inSection: String,
        atLocation: Int?,
        inView: View?,
        container: ViewGroup?,
        error: Throwable?
    ) {
        Log.d(NtvTAG, "onFail at location: $atLocation Error: $error")
        if (atLocation != null && inView != null) {
            Log.w(NtvTAG,"Removing Nativo Ad!")
            articleList.removeAt(atLocation)
            notifyItemRemoved(atLocation)
        }
    }

    override fun didPlaceAdInView(
        view: View,
        adData: NtvAdData,
        injectable: NtvInjectable,
        atLocation: Int,
        inSection: String,
        container: ViewGroup
    ) {
        Log.d(NtvTAG, "didPlaceAdInView: $atLocation AdData: $adData")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun didReceiveAd(didGetFill: Boolean, inSection: String) {
        Log.d(NtvTAG, "Did receive ad: $didGetFill")
        if (didGetFill && initialNativoRequest) {
            Log.w(NtvTAG, "Needs Reload Everything")
            notifyDataSetChanged()
        }
        initialNativoRequest = false
    }

    override fun needsDisplayClickOutURL(url: String, inSection: String, container: ViewGroup) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            context.startActivity(this)
        }
    }

    override fun needsDisplayLandingPage(
        landingPageIntent: Intent,
        inSection: String,
        container: ViewGroup
    ) {
        context.startActivity(landingPageIntent)
    }

    override fun <T : NtvInjectable> registerInjectableClassForTemplateType(
        injectableType: NtvInjectableType,
        atLocation: Int?,
        inSection: String?
    ): Class<T>? {
        return null
    }
}