package com.example.plant_library.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Activity.IndexActivity;
import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Adapter.PlantsGardenAdapter;
import com.example.plant_library.Interface.FragmentHandler;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Garden;
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

import java.util.ArrayList;
import java.util.List;


public class GardenFragment extends Fragment implements RecyclerViewInterface {
    private View mView;
    private RecyclerView recyclerView;
    private List<Plants> plantsList;
    private TextView tvCount;
    private AppCompatButton btnAdd;
    private PlantsGardenAdapter plantsAdapter;
    private FragmentHandler fragmentHandler;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_garden, container, false);
        tvCount = mView.findViewById(R.id.tv_garden_count);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(updateReceiver,
                new IntentFilter("com.example.plant_library.UPDATE_GARDEN"));
        setAirPlantsAdapter();
        setClickButton();

        return  mView;
    }
    public void setFragmentHandler(FragmentHandler fragmentHandler) {
        this.fragmentHandler = fragmentHandler;
    }
    public void setClickButton(){
        btnAdd = mView.findViewById(R.id.btn_add_gardenfragment);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentHandler != null) {
                    fragmentHandler.showFragment(new SearchFragment());
                    ((IndexActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.search_menu);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy đăng ký khi fragment bị hủy
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(updateReceiver);
    }

    private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getListPlants();
        }
    };

    private void setAirPlantsAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_garden_plant);
        plantsList = new ArrayList<>();
        plantsAdapter = new PlantsGardenAdapter(plantsList,this, getContext(), R.id.rcv_garden_plant);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(plantsAdapter);
        getListPlants();
    }
    private void getListPlants() {
        DatabaseReference gardenRef = FirebaseDatabase.getInstance().getReference("Garden").child(currentUser.getUid()).child("Plants");
        gardenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot gardenSnapshot) {
                List<PlantInstance> plantInstances = new ArrayList<>();
                for (DataSnapshot snapshot : gardenSnapshot.getChildren()) {
                    PlantInstance plantInstance = snapshot.getValue(PlantInstance.class);
                    if (plantInstance != null) {
                        plantInstances.add(plantInstance);
                        Log.e("getListPlants", "Database error: " + plantInstance.getStageName());
                    }
                }
                if (!plantInstances.isEmpty()) {
                    List<Integer> plantIds = new ArrayList<>();
                    for (PlantInstance instance : plantInstances) {
                        plantIds.add(instance.getPlantID());
                    }
                    fetchPlantsByIds(plantInstances);
                }  else {
                    plantsList.clear();
                    plantsAdapter.notifyDataSetChanged();
                    plantsAdapter.setShowShimmer(false);
                    tvCount.setText("Hãy tìm cho mình một cây trồng ưa thích");
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
                        tvCount.setText("Bạn đang có " + plantsList.size() + " cây trồng");
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