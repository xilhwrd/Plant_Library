package com.example.plant_library.Fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentFoliage extends Fragment implements RecyclerViewInterface {
    View mView;
    RecyclerView recyclerView;
    private PlantsAdapter plantsAdapter, easyPlantsAdapter, helioPlantsAdapter;
    private ConstraintLayout constraintLayout;
    private List<Plants> plantsList, easyPlantsList, helioPlantsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home_foliage, container, false);
        setEverbloomingAdapter();
        loadPlantsExcludingNonBlooming();
        setEasyCareAdapter();
        loadPlantsEasyCare();
        setHelioAdapter();
        return mView;
    }
    private void setEverbloomingAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_everblooming);
        plantsList = new ArrayList<>();
        plantsAdapter = new PlantsAdapter(plantsList,this, getContext(), R.id.rcv_category_search);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(plantsAdapter);
    }

    private void loadPlantsExcludingNonBlooming() {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        plantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null && plant.getBloomtime().contains("Không nở hoa")) {
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
                }, 1500);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

    private void setEasyCareAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_easy_care);
        easyPlantsList = new ArrayList<>();
        easyPlantsAdapter = new PlantsAdapter(easyPlantsList,this, getContext(), R.id.rcv_category_search);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(easyPlantsAdapter);
    }

    private void loadPlantsEasyCare() {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        Query query = plantsRef.orderByChild("CareRequirements/CareRate").equalTo("1");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                easyPlantsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null && plant.getBloomtime().contains("Không nở hoa")) {
                        easyPlantsList.add(plant);
                    }
                }
                easyPlantsAdapter.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        easyPlantsAdapter.setShowShimmer(false);
                    }
                }, 1500);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

    private void setHelioAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_heliophilous);
        helioPlantsList = new ArrayList<>();
        helioPlantsAdapter = new PlantsAdapter(helioPlantsList,this, getContext(), R.id.rcv_heliophilous);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(helioPlantsAdapter);
        loadPlantsHelio();
    }

    private void loadPlantsHelio() {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        Query query = plantsRef.orderByChild("LightRequirements/LightRate").equalTo("2");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                helioPlantsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null && plant.getBloomtime().contains("Không nở hoa")) {
                        helioPlantsList.add(plant);
                    }
                }
                helioPlantsAdapter.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        helioPlantsAdapter.setShowShimmer(false);
                    }
                }, 1500);

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