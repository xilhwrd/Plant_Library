package com.example.plant_library.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Interface.FragmentHandler;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;

import java.util.ArrayList;
import java.util.List;

public class GenreFragment extends Fragment implements RecyclerViewInterface {
    private  View mView;
    private RecyclerView recyclerView;
    private PlantsAdapter plantsAdapter;
    private FragmentHandler fragmentHandler;
    Plants plants;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_genre, container, false);
        setPlantsAdapter();
        setData();
        return mView;
    }
    private void setPlantsAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_category_search);
        plantsAdapter = new PlantsAdapter(this, getContext(), R.id.rcv_category_search);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        plantsAdapter.setData(getListPlants());
        recyclerView.setAdapter(plantsAdapter);
    }
    private List<Plants> getListPlants() {
        List<Plants> plantsList = new ArrayList<>();
        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
        plantsList.add(new Plants(R.drawable.img_onboarding3, R.drawable.img_sun_level1, R.drawable.img_water_level1, R.drawable.img_hard_level1, "plants nameeee"));
        return plantsList;
    }


    private void setData(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String categoryName = bundle.getString("category_name");
            // Sử dụng dữ liệu nhận được (ví dụ: hiển thị tên thể loại)
             TextView categoryNameTextView = mView.findViewById(R.id.tv_plant_category);
            categoryNameTextView.setText(categoryName);
        }
        setPlantsAdapter();
    }

    @Override
    public void onItemClick(int recyclerViewId, int position) {

    }
}