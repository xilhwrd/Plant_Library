package com.example.plant_library.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Activity.DetailActivity;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.LightRequirements;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlantsSearchAdapter extends RecyclerView.Adapter<PlantsSearchAdapter.PlantViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private List<Plants> plantsList, plantsListFull;
    private final int recyclerViewId;
    private boolean showShimmer = true;
    public PlantsSearchAdapter(List<Plants> list, RecyclerViewInterface recyclerViewInterface, Context context, int recyclerViewId) {
        this.plantsList = list;
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.recyclerViewId = recyclerViewId;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plants_search, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        if (showShimmer) {
            holder.shimmerLayout.startShimmer();
            holder.shimmerLayout.setAlpha((float) 1);

        } else {
            holder.shimmerLayout.stopShimmer();
            holder.shimmerLayout.setShimmer(null);
        Plants plants = plantsList.get(position);
            Picasso.get().load(plants.getPlantImage()).into(holder.imgPlants);
            holder.tvName.setBackground(null);
            holder.tvName.setText(""+plants.getCommonName());
            holder.tvFamily.setBackground(null);
            holder.tvFamily.setText("" + plants.getFamily());

        }
    }

    @Override
    public int getItemCount() {
        if(plantsList != null){
            return showShimmer ? 1 : plantsList.size();
        }
        return 0;
    }
    public void setShowShimmer(boolean showShimmer) {
        this.showShimmer = showShimmer;
        notifyDataSetChanged();
    }
    public void setFilteredList(List<Plants> filteredList){
        this.plantsList = filteredList;
        notifyDataSetChanged();
    }
    public void updateData(List<Plants> newPlantList) {
        this.plantsList = newPlantList;
        notifyDataSetChanged();
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvFamily;
        private CircleImageView imgPlants;
        private ShimmerFrameLayout shimmerLayout;
        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_plant_name_search);
            tvFamily = itemView.findViewById(R.id.tv_plant_family_search);
            shimmerLayout = itemView.findViewById(R.id.shimmer_layout_plant_search);
            imgPlants = itemView.findViewById(R.id.img_plant_search);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION && pos < plantsList.size() && !showShimmer){
                            recyclerViewInterface.onItemClick(recyclerViewId,pos);
                            Plants plant = plantsList.get(pos);
                            Bundle bundle = new Bundle();

                                bundle.putInt("plant_id",plant.getPlantID());
                                bundle.putString("plant_scientificName",plant.getScientificName());
                                bundle.putString("plant_commonName",plant.getCommonName());
                                bundle.putString("plant_family", plant.getFamily());
                                bundle.putString("plant_genus", plant.getGenus());
                                bundle.putString("plant_spieces", plant.getSpecies());
                                bundle.putString("plant_description", plant.getDescription());
                                bundle.putString("plant_growth_rate", plant.getGrowthRate());

                            bundle.putString("plant_light_rate", plant.getLightRequirements().get("LightRate"));
                            bundle.putString("plant_light_stage1", plant.getLightRequirements().get("LightStage1"));
                            bundle.putString("plant_light_stage2", plant.getLightRequirements().get("LightStage2"));
                            bundle.putString("plant_light_stage3", plant.getLightRequirements().get("LightStage3"));
                            bundle.putString("plant_light_stage4", plant.getLightRequirements().get("LightStage4"));


//                                bundle.putString("plant_light_rate", plant.getLightRate());
//                                bundle.putString("plant_light_stage1", plant.getLightStage1());
//                                bundle.putString("plant_light_stage2", plant.getLightStage2());
//                                bundle.putString("plant_light_stage3", plant.getLightStage3());
//                                bundle.putString("plant_light_stage4", plant.getLightStage4());


//                            LightRequirements lightRequirements = plant.getLightRequirements();
//                            if (lightRequirements != null) {
//                                bundle.putString("plant_light_rate", lightRequirements.getLightRate());
//                                bundle.putString("plant_light_stage1", lightRequirements.getLightStage1());
//                                bundle.putString("plant_light_stage2", lightRequirements.getLightStage2());
//                                bundle.putString("plant_light_stage3", lightRequirements.getLightStage3());
//                                bundle.putString("plant_light_stage4", lightRequirements.getLightStage4());
//                            }
                                bundle.putString("plant_water", plant.getWaterRequirements());
                                bundle.putString("plant_hard", plant.getCareRequirements());
                                bundle.putString("plant_soil", plant.getSoilType());
                                bundle.putString("plant_ph", plant.getPHRange());
                                bundle.putString("plant_temperature", plant.getTemperatureRange());
                                bundle.putString("plant_bloom", plant.getBloomtime());
                                bundle.putString("plant_propagation", plant.getPropagation());
                                bundle.putString("plant_size", plant.getSize());
                                bundle.putString("plant_img", plant.getPlantImage());

                                Intent intent = new Intent(context, DetailActivity.class);
                                intent.putExtra("plant_infor", bundle);
                                context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }

}
