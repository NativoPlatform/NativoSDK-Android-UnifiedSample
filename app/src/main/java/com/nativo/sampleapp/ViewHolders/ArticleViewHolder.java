package com.nativo.sampleapp.ViewHolders;

import static com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.sampleapp.R;


public class ArticleViewHolder extends RecyclerView.ViewHolder {
    public ViewGroup parent;
    public ImageView articleImage;
    public TextView articleTitle;
    public TextView articleDescription;
    public TextView articleAuthor;
    public ImageView articleSponsor;

    public ArticleViewHolder(@NonNull View container, ViewGroup viewGroup) {
        super(container);
        parent = viewGroup;
        articleImage = container.findViewById(R.id.article_image);
        articleTitle = container.findViewById(R.id.article_title);
        articleDescription = container.findViewById(R.id.article_description);
        articleAuthor = container.findViewById(R.id.article_author);
        articleSponsor = container.findViewById(R.id.sponsored_ad_indicator);
    }

    @SuppressLint("SetTextI18n")
    public void bindData(int position, String title) {
        articleTitle.setText(title);
        articleDescription.setText("Row "+position);
        articleImage.setImageResource(R.drawable.newsimage);
        articleAuthor.setText(R.string.sample_author);
        itemView.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CLICK_OUT_URL)));
        }
    };
}