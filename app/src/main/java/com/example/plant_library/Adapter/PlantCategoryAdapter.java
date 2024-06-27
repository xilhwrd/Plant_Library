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
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Activity.GenreActivity;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Genre;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlantCategoryAdapter extends RecyclerView.Adapter<PlantCategoryAdapter.PlantsViewHolder> {
    private Context context;
    private List<PlantCategory> plantCategoryList;
    private int layout;
    private final RecyclerViewInterface recyclerViewInterface;
    private final int recyclerViewId;
    private boolean showShimmer = true;
    private int itemLayout;
    public PlantCategoryAdapter(List<PlantCategory> list, int itemLayout, Context context, int layout, RecyclerViewInterface recyclerViewInterface, int recyclerViewId) {
        this.plantCategoryList = list;
        this.context = context;
        this.layout = layout;
        this.recyclerViewInterface = recyclerViewInterface;
        this.recyclerViewId = recyclerViewId;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new PlantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder holder, int position) {
        if (showShimmer) {
            holder.shimmerLayout.startShimmer();
            holder.shimmerLayout.setAlpha((float) 1);

        } else {
            holder.shimmerLayout.stopShimmer();
            holder.shimmerLayout.setShimmer(null);
        PlantCategory plantCategory = plantCategoryList.get(position);
        holder.tvCategoryName.setText(""+plantCategory.getCategoryName());
        Picasso.get().load(plantCategory.getCategoryImage()).into(holder.imgPlantCategory);

        }
    }

    @Override
    public int getItemCount() {
        if(plantCategoryList != null) {
            return showShimmer ? 8 : plantCategoryList.size();
        }
        return 0;
    }
    public void setShowShimmer(boolean showShimmer) {
        this.showShimmer = showShimmer;
        notifyDataSetChanged();
    }
    public void updateData(List<PlantCategory>  newCateList) {
        this.plantCategoryList = newCateList;
        notifyDataSetChanged();
    }
    public class PlantsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPlantCategory;
        private TextView tvCategoryName;
        private ShimmerFrameLayout shimmerLayout;

        public PlantsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlantCategory = itemView.findViewById(R.id.img_category);
            tvCategoryName = itemView.findViewById(R.id.tv_plant_category);
            shimmerLayout = itemView.findViewById(R.id.shimmer_layout_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION && pos < plantCategoryList.size() && !showShimmer) {
                            recyclerViewInterface.onItemClick(recyclerViewId, pos);
                            PlantCategory  category = plantCategoryList.get(pos);
                            Bundle bundle = new Bundle();
                            bundle.putString("category_name", category.getCategoryName());
                            bundle.putString("category_img", category.getCategoryImage());
                            bundle.putInt("category_id", category.getCategoryID());

                            Intent intent = new Intent(context, GenreActivity.class);
                            intent.putExtra("category_infor", bundle);
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}
