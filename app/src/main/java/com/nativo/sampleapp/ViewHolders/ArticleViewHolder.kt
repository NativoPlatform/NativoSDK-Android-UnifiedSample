package com.nativo.sampleapp.ViewHolders

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.util.AppConstants

open class ArticleViewHolder(container: View, var parent: ViewGroup) :
    RecyclerView.ViewHolder(container) {

    private var articleImage: ImageView?
    private var articleTitle: TextView?
    private var articleDescription: TextView?
    private var articleAuthor: TextView?
    //private var articleSponsor: ImageView?

    private var onClickListener = View.OnClickListener {
        it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.CLICK_OUT_URL)))
    }

    init {
        articleImage = container.findViewById(R.id.article_image)
        articleTitle = container.findViewById(R.id.article_title)
        articleDescription = container.findViewById(R.id.article_description)
        articleAuthor = container.findViewById(R.id.article_author)
        //articleSponsor = container.findViewById(R.id.sponsored_ad_indicator)
    }

    @SuppressLint("SetTextI18n")
    fun bindData(position: Int, title: String?) {
        articleTitle?.text = title
        articleDescription?.text = "Row $position"
        articleImage?.setImageResource(R.drawable.newsimage)
        articleAuthor?.setText(R.string.sample_author)
        itemView.setOnClickListener(onClickListener)
    }
}