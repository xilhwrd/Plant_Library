package com.example.plant_library.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.plant_library.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Object.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.UserViewHolder> {
    private Context context;
    private List<Article> articleList;

    public ArticleAdapter(Context context) {
        this.context = context;
    }
     public void setData(List<Article> list){
        this.articleList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Article article = articleList.get(position);
        if(article == null){
            return;
        }else {
            holder.imgArticle.setImageResource(article.getResourceID());
            holder.tvName.setText(article.getArticleName());
        }
    }

    @Override
    public int getItemCount() {
        if(articleList != null){
            return articleList.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgArticle;
        private TextView tvName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imgArticle = itemView.findViewById(R.id.img_article);
            tvName = itemView.findViewById(R.id.tv_article_name);

        }
    }
}
