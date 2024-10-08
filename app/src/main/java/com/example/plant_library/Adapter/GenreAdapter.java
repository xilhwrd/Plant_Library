package com.example.plant_library.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Genre;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private Context context;
    private List<Genre> genreList;
    private final RecyclerViewInterface recyclerViewInterface;
    private final int recyclerViewId;
    private boolean showShimmer = true;
    private int itemLayout;
    public GenreAdapter(List<Genre> list, int itemLayout, Context context, int recyclerViewId, RecyclerViewInterface recyclerViewInterface) {
        this.genreList = list;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
        this.recyclerViewId = recyclerViewId;
        this.itemLayout = itemLayout;
    }


    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new GenreViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        if (showShimmer) {
            holder.shimmerLayout.startShimmer();
            holder.shimmerLayout.setAlpha((float) 1);

        } else {
            holder.shimmerLayout.stopShimmer();
            holder.shimmerLayout.setShimmer(null);
        Genre genre = genreList.get(position);

            Picasso.get().load(genre.getGenreImage()).into(holder.imgGenre);
            holder.tvGenre.setText(genre.getGenreName());
        }
    }
    public void updateData(List<Genre> newGenreList) {
        this.genreList = newGenreList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(genreList != null){
            return showShimmer ? 3 : genreList.size();
        }
        return 0;
    }
    public void setShowShimmer(boolean showShimmer) {
        this.showShimmer = showShimmer;
        notifyDataSetChanged();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgGenre;
        private TextView tvGenre;
        private ShimmerFrameLayout shimmerLayout;
        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGenre = itemView.findViewById(R.id.img_genre);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            shimmerLayout = itemView.findViewById(R.id.shimmer_layout_genre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION && pos < genreList.size() && !showShimmer){
                            recyclerViewInterface.onItemClick(recyclerViewId, pos);

                        }
                    }
                }
            });
        }
    }
}
