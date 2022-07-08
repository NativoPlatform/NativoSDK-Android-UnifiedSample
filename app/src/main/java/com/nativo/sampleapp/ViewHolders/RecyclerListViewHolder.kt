package com.nativo.sampleapp.ViewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.sampleapp.R;


public class RecyclerListViewHolder extends RecyclerView.ViewHolder {
    View container;
    ViewGroup parent;
    ImageView articleImage;
    TextView articleTitle;
    TextView articleAuthor;
    ImageView articleSponsor;

    public RecyclerListViewHolder(@NonNull View container, ViewGroup viewGroup) {
        super(container);
        this.container = container;
        parent = viewGroup;
        articleImage = container.findViewById(R.id.article_image);
        articleTitle = container.findViewById(R.id.article_title);
        articleAuthor = container.findViewById(R.id.article_author);
        articleSponsor = container.findViewById(R.id.sponsored_ad_indicator);
    }

    public View getContainer() {
        return container;
    }


}