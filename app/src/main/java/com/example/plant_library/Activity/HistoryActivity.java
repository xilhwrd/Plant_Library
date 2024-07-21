package com.example.plant_library.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.PlantInstance;
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

public class HistoryActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView recyclerView;
    private List<Plants> plantsList ;
    private PlantsAdapter plantsAdapter;
    private Toolbar toolbar;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        LocalBroadcastManager.getInstance(this).registerReceiver(updateReceiver,
                new IntentFilter("com.example.plant_library.UPDATE_HISTORY"));
        initUI();
    }
    private void initUI(){
        recyclerView = findViewById(R.id.rcv_history);
        setPlantsAdapter();
        toolbar = findViewById(R.id.toolbar_history);
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
        plantsAdapter = new PlantsAdapter(plantsList,this, this, R.id.rcv_history);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(plantsAdapter);
        getListPlants();
    }
    private void getListPlants() {
        String userId = currentUser.getUid();
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("History").child(userId).child("Plants");
        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot gardenSnapshot) {
                List<PlantInstance> plantInstances = new ArrayList<>();
                for (DataSnapshot snapshot : gardenSnapshot.getChildren()) {
                    PlantInstance plantInstance = snapshot.getValue(PlantInstance.class);
                    if (plantInstance != null) {
                        plantInstances.add(plantInstance);
                    }
                }
                if (!plantInstances.isEmpty()) {
                    List<Integer> plantIds = new ArrayList<>();
                    for (PlantInstance instance : plantInstances) {
                        plantIds.add(instance.getPlantID());
                    }
                    fetchPlantsByIds(plantInstances);
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

    private void fetchPlantsByIds(List<PlantInstance> plantInstances) {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        plantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantsList.clear();
                for (PlantInstance instance : plantInstances) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null && plant.getPlantID() == instance.getPlantID()) {
                        plantsList.add(plant);
                    }
                }
                }
                plantsAdapter.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plantsAdapter.setShowShimmer(false);
                    }
                }, 1500);
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