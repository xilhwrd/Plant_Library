package com.example.plant_library.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.plant_library.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class ArticleDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ShapeableImageView imgArticle;
    private TextView articleTitle, articleContentIntro, articleDescript, articleContent, articleContent2, articleContent3, articleContent4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);


        initUI();
        getArticleInformation();
        toolbar = findViewById(R.id.toolbar_article_detail);
        setSupportActionBar(toolbar);
        // Thêm nút back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
            Drawable backArrow = getResources().getDrawable(R.drawable.bg_back_button);
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }
    }

    private void initUI() {
        imgArticle = findViewById(R.id.img_article_detail);

        articleContentIntro = findViewById(R.id.tv_article_content_intro);
        articleTitle = findViewById(R.id.tv_article_name_detail);
        articleDescript = findViewById(R.id.tv_article_descript_detail);
        articleContent = findViewById(R.id.tv_article_content_detail);
        articleContent2 = findViewById(R.id.tv_article_content_detail2);
        articleContent3 = findViewById(R.id.tv_article_content_detail3);
        articleContent4 = findViewById(R.id.tv_article_content_detail4);
    }
    private void getArticleInformation(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("article_infor");

        if (bundle != null) {
        int articleId = bundle.getInt("article_id");
        String title = bundle.getString("article_title");
        String image = bundle.getString("article_image");
        String content = bundle.getString("article_content");
        String contentIntro = bundle.getString("article_content_intro");
        String description = bundle.getString("article_description");
        String content2 = bundle.getString("article_content2");
        String content3 = bundle.getString("article_content3");
        String content4 = bundle.getString("article_content4");

        articleTitle.setText(title);
        articleDescript.setText(description);
        Picasso.get().load(image).into(imgArticle);
        articleContentIntro.setText(contentIntro);
        articleContent.setText(content);
        articleContent2.setText(content2);
        articleContent3.setText(content3);
        articleContent4.setText(content4);

    }else {
            Log.e("DetailActivity", "Bundle is null");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
