package com.example.plant_library.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlantCategoryAdapter extends RecyclerView.Adapter<PlantCategoryAdapter.PlantsViewHolder> {
    private Context context;
    private List<PlantCategory> plantCategoryList;
    private int layout;
    private final RecyclerViewInterface recyclerViewInterface;
    private final int recyclerViewId;

    public PlantCategoryAdapter(List<PlantCategory> list, Context context, int layout, RecyclerViewInterface recyclerViewInterface, int recyclerViewId) {
        this.plantCategoryList = list;
        this.context = context;
        this.layout = layout;
        this.recyclerViewInterface = recyclerViewInterface;
        this.recyclerViewId = recyclerViewId;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new PlantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder holder, int position) {
        PlantCategory plantCategory = plantCategoryList.get(position);
        holder.tvCategoryName.setText(plantCategory.getCategoryName());
        Picasso.get().load(plantCategory.getCategoryImageURL()).into(holder.imgPlantCategory);
    }

    @Override
    public int getItemCount() {
        return plantCategoryList != null ? plantCategoryList.size() : 0;
    }

    public class PlantsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPlantCategory;
        private TextView tvCategoryName;

        public PlantsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlantCategory = itemView.findViewById(R.id.img_category);
            tvCategoryName = itemView.findViewById(R.id.tv_plant_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(recyclerViewId, pos);
                        }
                    }
                }
            });
        }
    }
}
