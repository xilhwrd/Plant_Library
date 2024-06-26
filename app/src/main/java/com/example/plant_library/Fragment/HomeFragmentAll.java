package com.example.plant_library.Fragment;

import static android.content.ContentValues.TAG;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plant_library.Adapter.ArticleAdapter;
import com.example.plant_library.Adapter.GenreAdapter;
import com.example.plant_library.Adapter.OtherPlantCategoryAdapter;
import com.example.plant_library.Adapter.PlantCategoryAdapter;
import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Article;
import com.example.plant_library.Object.Genre;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragmentAll extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    TextView tvViewAllArticle;
    ArticleAdapter articleAdapter;
    PlantsAdapter interestPlantsAdapter, airPlantAdapter;
    GenreAdapter plantCategoryAdapter;
    OtherPlantCategoryAdapter otherPlantCategoryAdapter;
    private List<Plants> plantList, interestPlant, airPlant;
    private List<Article> articleList;
    private List<Genre> plantCategoryList;
    View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home_all, container, false);
        setArticleAdapter();
        setInterestPlantsAdapter();
        setAirPlantsAdapter();
        setPlantCategoryAdapter();
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
        int desiredWidth = 560;  // Thay thế bằng giá trị kích thước mong muốn của bạn
        int desiredHeight = 410; // Thay thế bằng giá trị kích thước mong muốn của bạn
        articleList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(articleList, getContext(), desiredWidth, desiredHeight, this, R.id.rcv_article);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
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
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        articleAdapter.setShowShimmer(false);
                    }
                }, 3000);
                articleAdapter.notifyDataSetChanged();
                Log.d(TAG, "Updated plantCategoryList: " + articleList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

    private void setInterestPlantsAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_interest);
        interestPlant = new ArrayList<>();
        interestPlantsAdapter = new PlantsAdapter(interestPlant,this, getContext(), R.id.rcv_interest);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(interestPlantsAdapter);
        getListInterestPlants();


    }
    private void getListInterestPlants() {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        plantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null) {
                        interestPlant.add(plant);
                    }
                }
                Collections.shuffle(interestPlant);
                List<Plants> limitedInterestPlant = interestPlant;
                if (interestPlant.size() > 10) {
                    limitedInterestPlant = interestPlant.subList(0, 10);
                }

                // Update the adapter's data and notify changes
                interestPlantsAdapter.updateData(limitedInterestPlant);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        interestPlantsAdapter.setShowShimmer(false);
                    }
                }, 3000);
                Log.d(TAG, "Number of plants loaded: " + interestPlant.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

    private void setAirPlantsAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_air_puring_plants);
        airPlant = new ArrayList<>();
        airPlantAdapter = new PlantsAdapter(airPlant,this, getContext(), R.id.rcv_air_puring_plants);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(airPlantAdapter);
        getListPlants();
    }
    private void getListPlants() {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        plantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                airPlant.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null && plant.getAirPurifying() == 1) {
                        airPlant.add(plant);
                    }
                }
                airPlantAdapter.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        airPlantAdapter.setShowShimmer(false);
                    }
                }, 3000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

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

    private void setPlantCategoryAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_plant_category);
        plantCategoryList = new ArrayList<>();
        plantCategoryAdapter = new GenreAdapter(plantCategoryList,getContext(), R.id.rcv_plant_category,this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(plantCategoryAdapter);
        getListGenre();
    }
    private void getListGenre() {
        DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference("Genre");
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantCategoryList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "DataSnapshot: " + snapshot.toString());
                    Genre genre = snapshot.getValue(Genre.class);
                    if (genre != null && (genre.getGenreID()==2 || genre.getGenreID() ==3)) {
                        Log.d(TAG, "Parsed category: " + genre.getGenreName() + ", " + genre.getGenreImage());
                        plantCategoryList.add(genre);
                    }
                }
                plantCategoryAdapter.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plantCategoryAdapter.setShowShimmer(false);
                    }
                }, 3000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

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