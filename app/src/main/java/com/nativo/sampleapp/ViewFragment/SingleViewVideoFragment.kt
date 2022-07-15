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
import com.nativo.sampleapp.databinding.FragmentSingleViewVideoBinding
import com.nativo.sampleapp.util.AppConstants

/**
 * A simple [Fragment] subclass.
 */
class SingleViewVideoFragment : Fragment() {

    private lateinit var binding: FragmentSingleViewVideoBinding

    private var onClickListener = View.OnClickListener {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }
    private var loadAd: View.OnClickListener = View.OnClickListener { }
    private var hideAd = View.OnClickListener { binding.videoContainer.visibility = View.INVISIBLE }
    private var showAd = View.OnClickListener { binding.videoContainer.visibility = View.VISIBLE }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSingleViewVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView(view)
        binding.loadAd.setOnClickListener(loadAd)
        binding.showAd.setOnClickListener(showAd)
        binding.hideAd.setOnClickListener(hideAd)
    }

    private fun bindView(view: View?) {
        if (view == null) return

        val articleImage: ImageView? = view.findViewById(R.id.article_image)
        val sponsoredAdIndicator: ImageView? = view.findViewById(R.id.sponsored_ad_indicator)
        val articleAuthor: TextView? = view.findViewById(R.id.article_author)
        val articleTitle: TextView? = view.findViewById(R.id.article_title)
        val sponsoredTag: TextView? = view.findViewById(R.id.sponsored_tag)
        val articleContainer: View? = view.findViewById(R.id.article_container)

        articleImage?.setImageResource(R.drawable.newsimage)
        sponsoredAdIndicator?.visibility = View.INVISIBLE
        articleAuthor?.setText(R.string.sample_author)
        articleTitle?.setText(R.string.sample_title)
        sponsoredTag?.visibility = View.INVISIBLE

        articleContainer?.setBackgroundColor(Color.RED)
        view.setOnClickListener(onClickListener)
    }
}