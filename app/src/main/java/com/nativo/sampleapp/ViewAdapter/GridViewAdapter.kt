package com.nativo.sampleapp.ViewAdapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.nativo.sampleapp.R
import com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL

class GridViewAdapter(context: Context) :
    ArrayAdapter<Int>(context, R.layout.publisher_article) {

    var integerList: MutableList<Int> = ArrayList()

    init {
        for (i in 0..9) {
            integerList.add(i)
        }
    }

    override fun getCount(): Int {
        return integerList.size
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        // Publisher article view
        val convertView: View
        val holder: ItemViewHolder

        if (view == null) {
            convertView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.publisher_article, viewGroup, false)
            holder = ItemViewHolder(convertView)
            convertView.tag = holder
        } else {
            convertView = view
            holder = view.tag as ItemViewHolder
        }

        holder.bind(integerList[i])
        return convertView
    }

    inner class ItemViewHolder(private val view: View) {
        private val articleImage: ImageView? = view.findViewById(R.id.article_image)
        private val articleTitle: TextView? = view.findViewById(R.id.article_title)
        private val articleAutor: TextView? = view.findViewById(R.id.article_description)
        private val articleSponsor: ImageView? = view.findViewById(R.id.sponsored_ad_indicator)

        fun bind(item: Int) {
            articleImage?.setImageResource(R.drawable.newsimage)
            articleSponsor?.visibility = View.INVISIBLE
            articleAutor?.setText(R.string.sample_author)
            articleTitle?.setText(R.string.sample_title)

            view.setOnClickListener {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(CLICK_OUT_URL)
                    )
                )
            }
        }
    }
}
