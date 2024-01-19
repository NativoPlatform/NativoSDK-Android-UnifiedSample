package com.nativo.sampleapp.ViewFragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nativo.sampleapp.R
import com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL

class SingleViewFragment : Fragment() {
    private var nativoAdView: View? = null
    var viewFragment: SingleViewFragment? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nativoAdView = view.findViewById(R.id.article_container)
        view.findViewById<View>(R.id.load_ad).setOnClickListener(loadAd)
        view.findViewById<View>(R.id.show_ad).setOnClickListener(showAd)
        view.findViewById<View>(R.id.hide_ad).setOnClickListener(hideAd)
    }

    private fun bindView(view: View?, i: Int) {
        if (view != null) {
            if (view.findViewById<View>(R.id.article_image) as ImageView != null) {
                (view.findViewById<View>(R.id.article_image) as ImageView).setImageResource(R.drawable.newsimage)
            }
            if (view.findViewById<View>(R.id.sponsored_ad_indicator) as ImageView != null) {
                (view.findViewById<View>(R.id.sponsored_ad_indicator) as ImageView).visibility =
                    View.INVISIBLE
            }
            if (view.findViewById<View>(R.id.article_author) as TextView != null) {
                (view.findViewById<View>(R.id.article_author) as TextView).setText(R.string.sample_author)
            }
            if (view.findViewById<View>(R.id.article_title) as TextView != null) {
                (view.findViewById<View>(R.id.article_title) as TextView).setText(R.string.sample_title)
            }
            if (view.findViewById<View>(R.id.sponsored_tag) as TextView != null) {
                (view.findViewById<View>(R.id.sponsored_tag) as TextView).visibility =
                    View.INVISIBLE
            }
            view.findViewById<View>(R.id.article_container).setBackgroundColor(Color.RED)
            view.setOnClickListener(onClickListener)
        }
    }

    var onClickListener = View.OnClickListener { view ->
        view.context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(CLICK_OUT_URL)
            )
        )
    }
    var loadAd = View.OnClickListener { }
    var hideAd = View.OnClickListener { nativoAdView!!.visibility = View.INVISIBLE }
    var showAd = View.OnClickListener { nativoAdView!!.visibility = View.VISIBLE }

    init {
        viewFragment = this
    }
}