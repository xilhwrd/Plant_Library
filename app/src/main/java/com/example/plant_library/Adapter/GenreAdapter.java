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
import com.example.plant_library.Object.Genre;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.R;
import com.squareup.picasso.Picasso;

import java.util.List;
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private Context context;
    private List<Genre> genreList;
    private final RecyclerViewInterface recyclerViewInterface;
    private final int recyclerViewId;

    public GenreAdapter(List<Genre> list, Context context, int recyclerViewId, RecyclerViewInterface recyclerViewInterface) {
        this.genreList = list;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
        this.recyclerViewId = recyclerViewId;
    }


    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        if(genre == null){
            return;
        } else {
            Picasso.get().load(genre.getGenreImage()).into(holder.imgGenre);
            holder.tvGenre.setText(genre.getGenreName());
        }
    }

    @Override
    public int getItemCount() {
        if(genreList != null){
            return genreList.size();
        }
        return 0;
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgGenre;
        private TextView tvGenre;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGenre = itemView.findViewById(R.id.img_genre);
            tvGenre = itemView.findViewById(R.id.tv_genre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(recyclerViewId, pos);
                        }
                    }
                }
            });
        }
    }
}
