package com.example.plant_library.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plant_library.Activity.ArticleDetailActivity;
import com.example.plant_library.Activity.DetailActivity;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.R;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Object.Article;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private Context context;
    private List<Article> articleList;
    private int width;
    private int height;
    private final RecyclerViewInterface recyclerViewInterface;
    private final int recyclerViewId;
    private boolean showShimmer = true;

    public ArticleAdapter(List<Article> list, Context context, int width, int height, RecyclerViewInterface recyclerViewInterface, int recyclerViewId) {
        this.articleList = list;
        this.context = context;
        this.width = width;
        this.height = height;
        this.recyclerViewInterface = recyclerViewInterface;
        this.recyclerViewId = recyclerViewId;
    }
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        if (showShimmer) {
            holder.shimmerLayout.startShimmer();
            holder.shimmerLayout.setAlpha((float) 1);

        } else {
            holder.shimmerLayout.stopShimmer();
            holder.shimmerLayout.setShimmer(null);
        Article article = articleList.get(position);
            Picasso.get().load(article.getArticleImage()).into(holder.imgArticle);
            holder.tvName.setText(article.getArticleTitle());
        }
        ViewGroup.LayoutParams layoutParams = holder.shimmerLayout.getLayoutParams();
        layoutParams.width = width;  // Đặt chiều rộng mong muốn, thay thế bằng giá trị của bạn
        layoutParams.height = height; // Đặt chiều cao mong muốn, thay thế bằng giá trị của bạn
        holder.shimmerLayout.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        if(articleList != null){
            return showShimmer ? 5 : articleList.size();
        }
        return 0;
    }
    public void setShowShimmer(boolean showShimmer) {
        this.showShimmer = showShimmer;
        notifyDataSetChanged();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgArticle;
        private ConstraintLayout constraintLayoutArticle;
        private TextView tvName;
        private ShimmerFrameLayout shimmerLayout;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayoutArticle = itemView.findViewById(R.id.constraint_layout_article);
            imgArticle = itemView.findViewById(R.id.img_article);
            tvName = itemView.findViewById(R.id.tv_article_name);
            shimmerLayout = itemView.findViewById(R.id.shimmer_layout_article);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION && pos < articleList.size() && !showShimmer){
                            recyclerViewInterface.onItemClick(recyclerViewId,pos);
                            Article article = articleList.get(pos);
                            Bundle bundle = new Bundle();

                            bundle.putInt("article_id", article.getArticleID());
                            bundle.putString("article_title",article.getArticleTitle());
                            bundle.putString("article_image",article.getArticleImage());
                            bundle.putString("article_content",article.getArticleContent());
                            bundle.putString("article_content_intro", article.getArtileContentIntro());
                            bundle.putString("article_description", article.getArticleDescrip());
                            bundle.putString("article_content2",article.getArticleContent2());
                            bundle.putString("article_content3",article.getArticleContent3());
                            bundle.putString("article_content4",article.getArticleContent4());

                            Intent intent = new Intent(context, ArticleDetailActivity.class);
                            intent.putExtra("article_infor", bundle);
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}
