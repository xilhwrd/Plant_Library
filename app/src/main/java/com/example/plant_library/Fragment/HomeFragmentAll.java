package com.example.plant_library.Fragment;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.plant_library.Adapter.ArticleAdapter;
import com.example.plant_library.Object.Article;
import com.example.plant_library.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentAll extends Fragment {
    RecyclerView recyclerView;
    TextView tvViewAllArticle;
    ArticleAdapter articleAdapter;
    View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home_all, container, false);
        setArticleAdapter();
        initUI();
        return mView;

    }
    private void initUI(){
        tvViewAllArticle = mView.findViewById(R.id.tv_viewall);
        tvViewAllArticle.setPaintFlags(tvViewAllArticle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvViewAllArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment parentFragment = getParentFragment();
                if (parentFragment instanceof HomeFragment) {
                    ((HomeFragment) parentFragment).selectTab(1);
                }
            }
        });
    }
    private void setArticleAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_article);
        articleAdapter = new ArticleAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);

        articleAdapter.setData(getListUser());
        recyclerView.setAdapter(articleAdapter);
    }

    private List<Article> getListUser() {
        List<Article> articleList = new ArrayList<>();

        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        return articleList;
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_home, fragment);
        transaction.commit();
    }
}