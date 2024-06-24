package com.example.plant_library.Fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plant_library.Adapter.ArticleAdapter;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Article;
import com.example.plant_library.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentArticle extends Fragment implements RecyclerViewInterface {
    View mView;
    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;
    private ConstraintLayout constraintLayout;
    private List<Article> articleList;
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
        int desiredHeight = 550; // Thay thế bằng giá trị kích thước mong muốn của bạn
        articleList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(articleList, getContext(), desiredWidth, desiredHeight, this, R.id.rcv_article_fragment);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(articleAdapter);
        getListArticle();
    }

    private void getListArticle() {
        DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference("Article");
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                articleList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "DataSnapshot: " + snapshot.toString());
                    Article article = snapshot.getValue(Article.class);
                    if (article != null) {
                        articleList.add(article);
                    }
                }
                articleAdapter.notifyDataSetChanged();
                Log.d(TAG, "Updated plantCategoryList: " + articleList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int recyclerViewId,int position) {

    }
}