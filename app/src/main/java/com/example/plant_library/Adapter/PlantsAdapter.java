package com.example.plant_library.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Activity.DetailActivity;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;

import java.util.List;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.UserViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private List<Plants> plantsList;
    private final int recyclerViewId;
    public PlantsAdapter(RecyclerViewInterface recyclerViewInterface, Context context, int recyclerViewId) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.recyclerViewId = recyclerViewId;
    }
     public void setData(List<Plants> list){
        this.plantsList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plants, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Plants plants = plantsList.get(position);
        if(plants == null){
            return;
        }else {
            holder.imgPlants.setImageResource(plants.getResourceID());
            holder.tvName.setText(plants.getPlantName());
            holder.imgHard.setImageResource(plants.getResourceHard());
            holder.imgSun.setImageResource(plants.getResourceSun());
            holder.imgWater.setImageResource(plants.getResourceWater());
        }
    }

    @Override
    public int getItemCount() {
        if(plantsList != null){
            return plantsList.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPlants, imgSun, imgHard, imgWater;
        private TextView tvName;
        private ConstraintLayout plantsLayout;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            plantsLayout = itemView.findViewById(R.id.plants_layout);
            imgSun = itemView.findViewById(R.id.img_sun);
            imgHard = itemView.findViewById(R.id.img_hard);
            imgWater = itemView.findViewById(R.id.img_water);
            imgPlants = itemView.findViewById(R.id.img_article);
            tvName = itemView.findViewById(R.id.tv_article_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(recyclerViewId,pos);
                        }
                    }
                }
            });
            plantsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
