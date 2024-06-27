package com.example.plant_library.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Interface.FragmentHandler;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GenreFragment extends Fragment implements RecyclerViewInterface {
    private  View mView;
    private RecyclerView recyclerView;
    private PlantsAdapter plantsAdapter;
    private FragmentHandler fragmentHandler;
    private List<Plants> plantList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_genre, container, false);
        setPlantsAdapter();
        return mView;
    }
    private void setPlantsAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_category_search);
        plantList = new ArrayList<>();
        plantsAdapter = new PlantsAdapter(plantList,this, getContext(), R.id.rcv_category_search);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(plantsAdapter);

        Bundle bundle = getArguments();
        String categoryName = null;
        if (bundle != null) {
            categoryName = bundle.getString("category_name");
            String categoryIMG = bundle.getString("category_img");
            TextView categoryNameTextView = mView.findViewById(R.id.tv_plant_category);
            ImageView categoryIMGview = mView.findViewById(R.id.img_category_detail);
            categoryNameTextView.setText(categoryName);
            Picasso.get().load(categoryIMG).into(categoryIMGview);

        setPlantsAdapter();
        loadPlantsByCategory(categoryName);
        }
    }

    @Override
    public void onItemClick(int recyclerViewId, int position) {

    }



    private void loadPlantsByCategory(String categoryName) {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("PlantInformation");
        plantsRef.orderByChild("Family").equalTo(categoryName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
                    if (plant != null) {
                        plantList.add(plant);
                    }
                }
                plantsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("GenreFragment", "Error: " + error.getMessage());
            }
        });
    }
}