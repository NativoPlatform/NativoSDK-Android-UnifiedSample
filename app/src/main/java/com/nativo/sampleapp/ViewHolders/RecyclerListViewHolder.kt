package com.nativo.sampleapp.ViewHolders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.nativo.sampleapp.R

open class RecyclerListViewHolder(
    var container: View, var parent: ViewGroup
) : RecyclerView.ViewHolder(
        container
    ) {

    var articleImage: ImageView? = container.findViewById(R.id.article_image)
    var articleTitle: TextView? = container.findViewById(R.id.article_title)
    var articleAuthor: TextView? = container.findViewById(R.id.article_author)
    var articleSponsor: ImageView? = container.findViewById(R.id.sponsored_ad_indicator)

}