package com.nativo.sampleapp.ViewAdapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL

class ListViewAdapter(private val listView: ViewGroup) : BaseAdapter() {

    companion object {
        const val ITEM_COUNT = 30
    }

    var integerList: MutableList<Int> = ArrayList()
    init {
        for (i in 0..ITEM_COUNT) {
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
        val convertView: View = view
            ?: LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.publisher_article, viewGroup, false)
        bindView(convertView, i)
        return convertView
    }

    private fun bindView(container: View, i: Int) {
        val articleImage = container.findViewById<ImageView>(R.id.article_image)
        val sponsoredIndicator = container.findViewById<ImageView>(R.id.sponsored_ad_indicator)
        val articleAuthor = container.findViewById<TextView>(R.id.article_author)
        val articleTitle = container.findViewById<TextView>(R.id.article_title)
        val sponsoredTag = container.findViewById<TextView>(R.id.sponsored_tag)

        articleImage?.setImageResource(R.drawable.newsimage)
        sponsoredIndicator?.visibility = View.INVISIBLE
        articleAuthor?.setText(R.string.sample_author)
        articleTitle?.setText(R.string.sample_title)
        sponsoredTag?.visibility = View.INVISIBLE

        container.setOnClickListener {
            container.context?.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(CLICK_OUT_URL)
                )
            )
        }
    }
}
