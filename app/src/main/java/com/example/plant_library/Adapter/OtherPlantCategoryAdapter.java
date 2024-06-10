package com.example.plant_library.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.R;

import java.util.List;

public class OtherPlantCategoryAdapter extends RecyclerView.Adapter<OtherPlantCategoryAdapter.UserViewHolder> {
    private Context context;
    private List<PlantCategory> plantCategoryList;

    public OtherPlantCategoryAdapter(Context context) {
        this.context = context;
    }
     public void setData(List<PlantCategory> list){
        this.plantCategoryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_plants, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        PlantCategory plantCategory = plantCategoryList.get(position);
        if(plantCategory == null){
            return;
        }else {
            holder.imgPlantCategory.setImageResource(plantCategory.getResourceID());
            holder.tvCategoryName.setText(plantCategory.getCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        if(plantCategoryList != null){
            return plantCategoryList.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPlantCategory;
        private TextView tvCategoryName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPlantCategory = itemView.findViewById(R.id.cir_img_other_plant);
            tvCategoryName = itemView.findViewById(R.id.tv_other_plants);

        }
    }
}
