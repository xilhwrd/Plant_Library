package com.example.plant_library.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Object.Genre;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OtherPlantCategoryAdapter extends RecyclerView.Adapter<OtherPlantCategoryAdapter.UserViewHolder> {
    private Context context;
    private List<Genre> genreList;

    public OtherPlantCategoryAdapter(Context context) {
        this.context = context;
    }
     public void setData(List<Genre> list){
        this.genreList = list;
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
        Genre genre = genreList.get(position);
        if(genre == null){
            return;
        }else {
            Picasso.get().load(genre.getGenreImage()).into(holder.imgPlantCategory);
            holder.tvCategoryName.setText(genre.getGenreName());
        }
    }

    @Override
    public int getItemCount() {
        if(genreList != null){
            return genreList.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPlantCategory;
        private TextView tvCategoryName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
//
//            imgPlantCategory = itemView.findViewById(R.id.cir_img_other_plant);
//            tvCategoryName = itemView.findViewById(R.id.tv_other_plants);

        }
    }
}
