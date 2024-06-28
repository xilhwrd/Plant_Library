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
import com.example.plant_library.Object.Stage;
import com.example.plant_library.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder> {
    private Context context;
    private List<Stage> stageList;

    public StageAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Stage> list){
        this.stageList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StageAdapter.StageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stage, parent, false);
        return new StageAdapter.StageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StageAdapter.StageViewHolder holder, int position) {
        Stage stage = stageList.get(position);
        if(stage == null){
            return;
        }else {
            holder.tvStageTime.setText(stage.getStageTime());
            holder.tvStageName.setText(stage.getStageName());
            holder.imgStage.setImageResource(stage.getIdResource());
        }
    }

    @Override
    public int getItemCount() {
        if(stageList != null){
            return stageList.size();
        }
        return 0;
    }


    public class StageViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgStage;
        private TextView tvStageTime, tvStageName;
        public StageViewHolder(@NonNull View itemView) {
            super(itemView);

            imgStage = itemView.findViewById(R.id.img_stage);
            tvStageTime = itemView.findViewById(R.id.tv_stage_time);
            tvStageName = itemView.findViewById(R.id.tv_stage_name);

        }
    }
}

