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
            switch (plants.getLightRequirements()){
                case "1" : holder.imgSun.setImageResource(R.drawable.img_sun_level1);
                    break;
                case "2" : holder.imgSun.setImageResource(R.drawable.img_sun_level2);
                    break;
                case "3" : holder.imgSun.setImageResource(R.drawable.img_sun_level3);
                    break;
                default: holder.imgSun.setImageResource(R.drawable.img_sun_level1);
                    break;
            }
            switch (plants.getWaterRequirements()){
                case "1" : holder.imgWater.setImageResource(R.drawable.img_water_level1);
                    break;
                case "2" : holder.imgWater.setImageResource(R.drawable.img_water_level2);
                    break;
                case "3" : holder.imgWater.setImageResource(R.drawable.img_water_level3);
                    break;
                default:holder.imgWater.setImageResource(R.drawable.img_water_level1);
                    break;
            }
            switch (plants.getCareRequirements()) {
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

                                bundle.putString("plant_light", plant.getLightRequirements());
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
