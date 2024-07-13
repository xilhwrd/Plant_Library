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
import com.example.plant_library.Object.CareRequirements;
import com.example.plant_library.Object.LightRequirements;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.Object.Stage;
import com.example.plant_library.Object.TemperatureRange;
import com.example.plant_library.Object.WaterRequirements;
import com.example.plant_library.Object.WaterStage;
import com.example.plant_library.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private List<Plants> plantsList;
    private final int recyclerViewId;
    private boolean showShimmer = true;
    public PlantsAdapter(List<Plants> list,RecyclerViewInterface recyclerViewInterface, Context context, int recyclerViewId) {
        this.plantsList = list;
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.recyclerViewId = recyclerViewId;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plants, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        if (showShimmer) {
            holder.shimmerLayout.startShimmer();
            holder.shimmerLayout.setAlpha((float) 1);
            holder.imgHard.setVisibility(View.GONE);
            holder.imgSun.setVisibility(View.GONE);
            holder.imgWater.setVisibility(View.GONE);

        } else {
            holder.shimmerLayout.stopShimmer();
            holder.shimmerLayout.setShimmer(null);
        Plants plants = plantsList.get(position);
            Picasso.get().load(plants.getPlantImage()).into(holder.imgPlants);
            holder.tvName.setText(""+plants.getCommonName());
            holder.imgHard.setVisibility(View.VISIBLE);
            holder.imgSun.setVisibility(View.VISIBLE);
            holder.imgWater.setVisibility(View.VISIBLE);

            LightRequirements lightRequirements = plants.getLightRequirements();
            if (lightRequirements != null) {
                switch (lightRequirements.getLightRate()) {
//                switch (plants.getLightRequirements().get("LightRate")) {
                    case "1":
                        holder.imgSun.setImageResource(R.drawable.img_sun_level1);
                        break;
                    case "2":
                        holder.imgSun.setImageResource(R.drawable.img_sun_level2);
                        break;
                    case "3":
                        holder.imgSun.setImageResource(R.drawable.img_sun_level3);
                        break;
                    default:
                        holder.imgSun.setImageResource(R.drawable.img_sun_level1);
                        break;
                }
            }
            WaterRequirements waterRequirements = plants.getWaterRequirements();
            if (waterRequirements != null) {
            switch (waterRequirements.getWaterRate()) {
//            switch (plants.getWaterRequirements()){
                case "1" : holder.imgWater.setImageResource(R.drawable.img_water_level1);
                    break;
                case "2" : holder.imgWater.setImageResource(R.drawable.img_water_level2);
                    break;
                case "3" : holder.imgWater.setImageResource(R.drawable.img_water_level3);
                    break;
                default:holder.imgWater.setImageResource(R.drawable.img_water_level1);
                    break;
            }
            }
            CareRequirements careRequirements = plants.getCareRequirements();
            if (careRequirements != null) {
            switch (careRequirements.getCareRate()) {
//            switch (plants.getCareRequirements()) {
                case "1":
                    holder.imgHard.setImageResource(R.drawable.img_hard_level1);
                    break;
                case "2":
                    holder.imgHard.setImageResource(R.drawable.img_hard_level2);
                    break;
                case "3":
                    holder.imgHard.setImageResource(R.drawable.img_hard_level3);
                    break;
                default:
                    holder.imgHard.setImageResource(R.drawable.img_hard_level1);
                    break;
            }
            }
        }
    }

    @Override
    public int getItemCount() {
        if(plantsList != null){
            return showShimmer ? 6 : plantsList.size();
        }
        return 0;
    }
    public void setShowShimmer(boolean showShimmer) {
        this.showShimmer = showShimmer;
        notifyDataSetChanged();
    }
    public void updateData(List<Plants> newPlantList) {
        this.plantsList = newPlantList;
        notifyDataSetChanged();
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPlants, imgSun, imgHard, imgWater;
        private TextView tvName;
        private ConstraintLayout plantsLayout;
        private ShimmerFrameLayout shimmerLayout;
        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            plantsLayout = itemView.findViewById(R.id.plants_layout);
            imgSun = itemView.findViewById(R.id.img_sun);
            imgHard = itemView.findViewById(R.id.img_hard);
            imgWater = itemView.findViewById(R.id.img_water);
            imgPlants = itemView.findViewById(R.id.img_plant);
            tvName = itemView.findViewById(R.id.tv_plant_name);
            shimmerLayout = itemView.findViewById(R.id.shimmer_layout_plant);

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

//                            bundle.putString("plant_light_rate", plant.getLightRequirements().get("LightRate"));
//                            bundle.putString("plant_light_stage1", plant.getLightRequirements().get("LightStage1"));
//                            bundle.putString("plant_light_stage2", plant.getLightRequirements().get("LightStage2"));
//                            bundle.putString("plant_light_stage3", plant.getLightRequirements().get("LightStage3"));
//                            bundle.putString("plant_light_stage4", plant.getLightRequirements().get("LightStage4"));


//                            bundle.putString("plant_light_rate", plant.getLightRate());
//                            bundle.putString("plant_light_stage1", plant.getLightStage1());
//                            bundle.putString("plant_light_stage2", plant.getLightStage2());
//                            bundle.putString("plant_light_stage3", plant.getLightStage3());
//                            bundle.putString("plant_light_stage4", plant.getLightStage4());

                            LightRequirements lightRequirements = plant.getLightRequirements();
                            if (lightRequirements != null) {
                                bundle.putString("plant_light_rate", lightRequirements.getLightRate());
                                bundle.putString("plant_light_stage1", lightRequirements.getLightStage1());
                                bundle.putString("plant_light_stage2", lightRequirements.getLightStage2());
                                bundle.putString("plant_light_stage3", lightRequirements.getLightStage3());
                                bundle.putString("plant_light_stage4", lightRequirements.getLightStage4());
                            }
                            WaterRequirements waterRequirements = plant.getWaterRequirements();

                            if (waterRequirements != null) {
                                bundle.putString("plant_water_rate", waterRequirements.getWaterRate());

                                WaterStage waterStage1 = waterRequirements.getWaterStage1();
                                if (waterStage1 != null) {
                                    bundle.putString("plant_water_stage1_description", waterStage1.getDescription());
                                    bundle.putInt("plant_water_stage1_interval", waterStage1.getWateringInterval());
                                }

                                WaterStage waterStage2 = waterRequirements.getWaterStage2();
                                if (waterStage2 != null) {
                                    bundle.putString("plant_water_stage2_description", waterStage2.getDescription());
                                    bundle.putInt("plant_water_stage2_interval", waterStage2.getWateringInterval());
                                }

                                WaterStage waterStage3 = waterRequirements.getWaterStage3();
                                if (waterStage3 != null) {
                                    bundle.putString("plant_water_stage3_description", waterStage3.getDescription());
                                    bundle.putInt("plant_water_stage3_interval", waterStage3.getWateringInterval());
                                }

                                WaterStage waterStage4 = waterRequirements.getWaterStage4();
                                if (waterStage4 != null) {
                                    bundle.putString("plant_water_stage4_description", waterStage4.getDescription());
                                    bundle.putInt("plant_water_stage4_interval", waterStage4.getWateringInterval());
                                }
                            }

//                            if (waterRequirements != null) {
//                                bundle.putString("plant_water_rate", waterRequirements.getWaterRate());
//                                bundle.putString("plant_water_stage1", waterRequirements.getWaterStage1());
//                                bundle.putString("plant_water_stage2", waterRequirements.getWaterStage2());
//                                bundle.putString("plant_water_stage3", waterRequirements.getWaterStage3());
//                                bundle.putString("plant_water_stage4", waterRequirements.getWaterStage4());
//                            }

//                            bundle.putString("plant_water", plant.getWaterRequirements());
//                                bundle.putString("plant_hard", plant.getCareRequirements());

                            CareRequirements careRequirements = plant.getCareRequirements();
                            if (careRequirements != null) {
                                bundle.putString("plant_hard_rate", careRequirements.getCareRate());
                                bundle.putString("plant_hard_stage", careRequirements.getCareStage());
                            }
                                bundle.putString("plant_soil", plant.getSoilType());
                                bundle.putString("plant_ph", plant.getPHRange());
//                                bundle.putString("plant_temperature", plant.getTemperatureRange());
                            TemperatureRange temperatureRange = plant.getTemperatureRange();
                            if (temperatureRange != null) {
                                bundle.putString("plant_temperature", temperatureRange.getTemperatureRate());
                                bundle.putString("plant_tempurature_stage", temperatureRange.getTemperatureStage());
                            }
                            Stage stageRequirements = plant.getStage();
                            if (stageRequirements != null) {
                                bundle.putString("plant_stage_name", stageRequirements.getStageName());
                                bundle.putInt("plant_stage_day1", stageRequirements.getStageDay1());
                                bundle.putInt("plant_stage_day2", stageRequirements.getStageDay2());
                                bundle.putInt("plant_stage_day3", stageRequirements.getStageDay3());
                                bundle.putInt("plant_stage_day4", stageRequirements.getStageDay4());
                            }
                            bundle.putString("plant_fert", plant.getFertilizing());
                            bundle.putString("plant_pests", plant.getPests());

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
//            plantsLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("plant_id",plants.getPlantID);
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    context.startActivity(intent);
//                }
//            });
        }
    }

}
