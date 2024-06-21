package com.example.plant_library.Fragment;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plant_library.Adapter.ArticleAdapter;
import com.example.plant_library.Adapter.OtherPlantCategoryAdapter;
import com.example.plant_library.Adapter.PlantCategoryAdapter;
import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Article;
import com.example.plant_library.Object.Genre;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentAll extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    TextView tvViewAllArticle;
    ArticleAdapter articleAdapter;
    PlantsAdapter plantsAdapter;
    PlantCategoryAdapter plantCategoryAdapter;
    OtherPlantCategoryAdapter otherPlantCategoryAdapter;
    private List<Plants> plantList;
    View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home_all, container, false);
        setArticleAdapter();
        setPlantsAdapter();
//        setAirPlantsAdapter();
//        setPlantCategoryAdapter();
//        setLowMainPlantsAdapter();
        setOtherPlantCategoryAdapter();
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
        int desiredWidth = 500;  // Thay thế bằng giá trị kích thước mong muốn của bạn
        int desiredHeight = 350; // Thay thế bằng giá trị kích thước mong muốn của bạn

        articleAdapter = new ArticleAdapter(getContext(), desiredWidth, desiredHeight, this, R.id.rcv_article);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        articleAdapter.setData(getListArticle());
        recyclerView.setAdapter(articleAdapter);
    }
    private void setPlantsAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_interest);
        plantList = new ArrayList<>();
        plantsAdapter = new PlantsAdapter(plantList,this, getContext(), R.id.rcv_category_search);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(plantsAdapter);
    }

//    private void setAirPlantsAdapter(){
//        recyclerView = mView.findViewById(R.id.rcv_air_puring_plants);
//        plantsAdapter = new PlantsAdapter(this, getContext(), R.id.rcv_air_puring_plants);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//
//        plantsAdapter.setData(getListPlants());
//        recyclerView.setAdapter(plantsAdapter);
//    }
//    private void setPlantCategoryAdapter(){
//        recyclerView = mView.findViewById(R.id.rcv_plant_category);
//        plantCategoryAdapter = new PlantCategoryAdapter(getContext(), R.layout.item_category_plants, this,R.id.rcv_plant_category);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//
//        plantCategoryAdapter.setData(getListPlantCategory());
//        recyclerView.setAdapter(plantCategoryAdapter);
//    }
//    private void setLowMainPlantsAdapter(){
//        recyclerView = mView.findViewById(R.id.rcv_lowmain_plant);
//        plantsAdapter = new PlantsAdapter(this, getContext(), R.id.rcv_lowmain_plant);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//
//        plantsAdapter.setData(getListPlants());
//        recyclerView.setAdapter(plantsAdapter);
//    }
    private void setOtherPlantCategoryAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_other_plant_category);
        otherPlantCategoryAdapter = new OtherPlantCategoryAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        otherPlantCategoryAdapter.setData(getListOtherPlantCategory());
        recyclerView.setAdapter(otherPlantCategoryAdapter);
    }
//    private List<Plants> getListPlants() {
//        List<Plants> plantsList = new ArrayList<>();
//        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
//        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
//        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
//        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
//        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
//        return plantsList;
//    }

    private List<Article> getListArticle() {
        List<Article> articleList = new ArrayList<>();

        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        articleList.add(new Article(R.drawable.img_onboarding2,"plans name"));
        return articleList;
    }
    private List<PlantCategory> getListPlantCategory() {
        List<PlantCategory> plantCategoryListList = new ArrayList<>();
//        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage"));
//        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage"));
        return plantCategoryListList;
    }
    private List<Genre> getListOtherPlantCategory() {
        List<Genre> genreList = new ArrayList<>();
//        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Drought-tolerant plants"));
//        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Drought-tolerant plants"));
//        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Drought-tolerant plants"));
//        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Drought-tolerant plants"));
        return genreList;
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_home, fragment);
        transaction.commit();
    }

    @Override
    public void onItemClick(int recyclerViewId,int position) {
//        Article articleFragment = new ArticleFrag();
//        Bundle bundle = new Bundle();
//        // Giả sử bạn muốn truyền tên của thể loại từ danh sách thể loại (getListPlants)
//        Article selectedArticle = getListArticle().get(position);
//        PlantCategory selectedCategory = getListPlantCategory().get(position);
//        bundle.putString("article_name", selectedArticle.getArticleName());
//        genreFragment.setArguments(bundle);
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_index));
//
//        fragmentTransaction.add(R.id.frame_index, genreFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

        switch (recyclerViewId) {
            case R.id.rcv_article:
                // Handle article item click
                break;
            case R.id.rcv_interest:
            case R.id.rcv_air_puring_plants:
            case R.id.rcv_lowmain_plant:
                // Handle plants item click
                break;
            case R.id.rcv_plant_category:
                // Handle plant category item click
                break;
            case R.id.rcv_other_plant_category:
                // Handle other plant category item click
                break;
            default:
                break;
        }
    }
}