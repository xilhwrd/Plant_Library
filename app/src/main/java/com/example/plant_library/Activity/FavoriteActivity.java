package com.example.plant_library.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Adapter.PlantsGardenAdapter;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Favorite;
import com.example.plant_library.Object.Garden;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView recyclerView;
    private List<Plants> plantsList ;
     private PlantsAdapter plantsAdapter;
    private Toolbar toolbar;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        LocalBroadcastManager.getInstance(this).registerReceiver(updateReceiver,
                new IntentFilter("com.example.plant_library.UPDATE_FAVORITE"));
        initUI();
    }
    private void initUI(){
        recyclerView = findViewById(R.id.rcv_favorite);
        setPlantsAdapter();
        toolbar = findViewById(R.id.toolbar_favorite);
        setSupportActionBar(toolbar);

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

    private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getListPlants();
        }
    };
    private void setPlantsAdapter(){
        plantsList = new ArrayList<>();
        plantsAdapter = new PlantsAdapter(plantsList,this, this, R.id.rcv_garden_plant);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(plantsAdapter);
        getListPlants();
    }
    private void getListPlants() {
        String userId = currentUser.getUid();
        DatabaseReference favoriteRef = FirebaseDatabase.getInstance().getReference("Favorite").child(userId).child("plantIds");
        favoriteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot gardenSnapshot) {
                List<Integer> plantIds = new ArrayList<>();
                for (DataSnapshot snapshot : gardenSnapshot.getChildren()) {
                    String plantIdString = snapshot.getKey();
                    if (plantIdString != null) {
                        int plantId = Integer.parseInt(plantIdString);
                        plantIds.add(plantId);
                    }
                }
                if (!plantIds.isEmpty()) {
                    fetchPlantsByIds(plantIds);
                } else {
                    plantsList.clear();
                    plantsAdapter.notifyDataSetChanged();
                    plantsAdapter.setShowShimmer(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
                Log.e("getListPlants", "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void fetchPlantsByIds(List<Integer> plantIds) {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        plantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null && plantIds.contains(plant.getPlantID())) {
                        plantsList.add(plant);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
                Log.e("fetchPlantsByIds", "Database error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int recyclerViewId, int position) {

    }
}