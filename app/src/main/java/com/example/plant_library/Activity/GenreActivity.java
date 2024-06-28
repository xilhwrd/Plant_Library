package com.example.plant_library.Activity;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Fragment.SearchFragment;
import com.example.plant_library.Interface.FragmentHandler;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GenreActivity extends AppCompatActivity implements RecyclerViewInterface {
    private static final String TAG = "Genreacti";
    private RecyclerView recyclerView;
    private PlantsAdapter plantsAdapter;
    private FragmentHandler fragmentHandler;
    private List<Plants> plantList;
    private Toolbar toolbarGenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        toolbarGenre = findViewById(R.id.toolbar_genre);
        setSupportActionBar(toolbarGenre);
        setPlantsAdapter();
        setData();


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
            Drawable backArrow = getResources().getDrawable(R.drawable.bg_back_button);
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void setPlantsAdapter(){
        recyclerView = findViewById(R.id.rcv_category_search);
        plantList = new ArrayList<>();
        plantsAdapter = new PlantsAdapter(plantList,this, this, R.id.rcv_category_search);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(plantsAdapter);
    }



    private void setData() {
//
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        String categoryName = null;
//        int categoryID = 0;
//        if (bundle != null) {
//            categoryName = bundle.getString("category_name");
//            String categoryIMG = bundle.getString("category_img");
//            categoryID = bundle.getInt("category_id");
//
//            TextView categoryNameTextView = findViewById(R.id.tv_plant_category);
//            ImageView categoryIMGview = findViewById(R.id.img_category_detail);
//            categoryNameTextView.setText(categoryName);
//            Picasso.get().load(categoryIMG).into(categoryIMGview);
//        }
//        setPlantsAdapter();
//        loadPlantsByCategoryAndFamily(categoryID, categoryName);
//        loadPlantsByCategory(categoryName);

        Intent intent = getIntent();
        String categoryName = null;
        int categoryID = 0;
        Bundle bundle = intent.getBundleExtra("category_infor");
        if (bundle != null) {
            categoryName = bundle.getString("category_name");
            String categoryIMG = bundle.getString("category_img");
            categoryID = bundle.getInt("category_id");

            TextView categoryNameTextView = findViewById(R.id.tv_plant_category);
            ImageView categoryIMGview = findViewById(R.id.img_category_detail);
            categoryNameTextView.setText(categoryName);
            Picasso.get().load(categoryIMG).into(categoryIMGview);
        }
        setPlantsAdapter();
        loadPlantsByCategoryAndFamily(categoryID, categoryName);
    }


    @Override
    public void onItemClick(int recyclerViewId, int position) {
    }



    private void loadPlantsByCategoryAndFamily(int categoryID, String family) {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        plantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null && plant.getCategoryID() == categoryID || family.equals(plant.getFamily())) {
                        plantList.add(plant);
                    }
                }
                plantsAdapter.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plantsAdapter.setShowShimmer(false);
                    }
                }, 3000);
                Log.d(TAG, "Number of plants loaded: " + plantList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

    private void loadPlantsByCategory(String categoryName) {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        plantsRef.orderByChild("Family").equalTo(categoryName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "Number of plants loaded: " + snapshot.toString());
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null) {
                        plantList.add(plant);
                    }
                }
                plantsAdapter.notifyDataSetChanged();
                Log.d(TAG, "Number of plants loaded: " + plantList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("GenreFragment", "Error: " + error.getMessage());
            }
        });
    }
}