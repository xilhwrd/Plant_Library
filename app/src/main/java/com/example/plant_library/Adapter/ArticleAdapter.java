package com.example.plant_library.Adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private Context context;
    private List<Article> articleList;
    private int width;
    private int height;
    private final RecyclerViewInterface recyclerViewInterface;
    private final int recyclerViewId;

    public ArticleAdapter(Context context, int width, int height, RecyclerViewInterface recyclerViewInterface, int recyclerViewId) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.recyclerViewInterface = recyclerViewInterface;
        this.recyclerViewId = recyclerViewId;
    }
    public void setData(List<Article> list){
        this.articleList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);
        if(article == null){
            return;
        }else {
            holder.imgArticle.setImageResource(article.getResourceID());
            holder.tvName.setText(article.getArticleName());
        }
        ViewGroup.LayoutParams layoutParams = holder.constraintLayoutArticle.getLayoutParams();
        layoutParams.width = width;  // Đặt chiều rộng mong muốn, thay thế bằng giá trị của bạn
        layoutParams.height = height; // Đặt chiều cao mong muốn, thay thế bằng giá trị của bạn
        holder.constraintLayoutArticle.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        if(articleList != null){
            return articleList.size();
        }
        return 0;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgArticle;
        private ConstraintLayout constraintLayoutArticle;
        private TextView tvName;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayoutArticle = itemView.findViewById(R.id.constraint_layout_article);
            imgArticle = itemView.findViewById(R.id.img_article);
            tvName = itemView.findViewById(R.id.tv_article_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(recyclerViewId,pos);
                        }
                    }
                }
            });
            constraintLayoutArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
