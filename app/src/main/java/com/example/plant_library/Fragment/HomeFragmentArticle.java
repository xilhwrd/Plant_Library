package com.example.plant_library.Fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plant_library.Adapter.ArticleAdapter;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Article;
import com.example.plant_library.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentArticle extends Fragment implements RecyclerViewInterface {
    View mView;
    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;
    private ConstraintLayout constraintLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home_article, container, false);
        setArticleAdapter();
        return mView;
    }
    private void setArticleAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_article_fragment);
        int desiredWidth = ViewGroup.LayoutParams.MATCH_PARENT;;  // Thay thế bằng giá trị kích thước mong muốn của bạn
        int desiredHeight = 400; // Thay thế bằng giá trị kích thước mong muốn của bạn

        articleAdapter = new ArticleAdapter(getContext(), desiredWidth, desiredHeight, this, R.id.rcv_article_fragment);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        articleAdapter.setData(getListArticle());
        recyclerView.setAdapter(articleAdapter);
    }
    private List<Article> getListArticle() {
        List<Article> articleList = new ArrayList<>();

        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        return articleList;
    }

    @Override
    public void onItemClick(int recyclerViewId,int position) {

    }
}