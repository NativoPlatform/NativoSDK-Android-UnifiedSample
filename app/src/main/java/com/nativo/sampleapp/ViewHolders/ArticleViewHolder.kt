package com.nativo.sampleapp.ViewHolders

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nativo.sampleapp.ArticleActivity
import com.nativo.sampleapp.R
import com.nativo.sampleapp.util.AppConstants
import java.text.DateFormat
import java.util.Date

open class ArticleViewHolder(container: View, private var parent: ViewGroup) :
    RecyclerView.ViewHolder(container) {


    private var onClickListener = View.OnClickListener {
        val intent = Intent(this.parent.context, ArticleActivity::class.java)
        it.context.startActivity(intent)
    }

    init {
        container.setOnClickListener(onClickListener)
    }

}