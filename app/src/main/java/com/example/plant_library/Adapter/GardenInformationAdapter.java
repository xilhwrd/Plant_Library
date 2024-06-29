package com.example.plant_library.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Object.GardenInformation;
import com.example.plant_library.Object.Stage;
import com.example.plant_library.R;

import java.util.List;

    public class GardenInformationAdapter extends RecyclerView.Adapter<GardenInformationAdapter.GardenInforViewHolder> {
        private Context context;
        private List<GardenInformation> gardenInformationList;

        public GardenInformationAdapter(Context context) {
            this.context = context;
        }
        public void setData(List<GardenInformation> list){
            this.gardenInformationList = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public GardenInformationAdapter.GardenInforViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_garden_info, parent, false);
            return new GardenInformationAdapter.GardenInforViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GardenInformationAdapter.GardenInforViewHolder holder, int position) {
            GardenInformation gardenInformation = gardenInformationList.get(position);
            if(gardenInformation == null){
                return;
            }else {
                holder.tvContent.setText(gardenInformation.getContent());
                holder.tvTitle.setText(gardenInformation.getHeading());
                holder.imgGarden.setImageResource(gardenInformation.getTitleImage());

                boolean isVisible = gardenInformation.isVisibility();
                holder.constraintLayoutEx.setVisibility(isVisible ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            if(gardenInformationList != null){
                return gardenInformationList.size();
            }
            return 0;
        }


        public class GardenInforViewHolder extends RecyclerView.ViewHolder{
            private ImageView imgGarden;
            private TextView tvTitle, tvContent;
            private ConstraintLayout constraintLayout, constraintLayoutEx;
            public GardenInforViewHolder(@NonNull View itemView) {
                super(itemView);

                imgGarden = itemView.findViewById(R.id.img_garden);
                tvTitle = itemView.findViewById(R.id.tv_title_garden);
                tvContent = itemView.findViewById(R.id.tv_content_garden);
                constraintLayout = itemView.findViewById(R.id.layout_garden);
                constraintLayoutEx = itemView.findViewById(R.id.constraint_layout_extend);

                constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GardenInformation gardenInformation = gardenInformationList.get(getAdapterPosition());
                        gardenInformation.setVisibility(!gardenInformation.isVisibility());
                        notifyItemChanged(getAdapterPosition());
//                        if(constraintLayoutEx.getVisibility() == View.GONE){
//                            constraintLayoutEx.setVisibility(View.VISIBLE);
//                        }
//                        else { constraintLayoutEx.setVisibility(View.GONE);}
                    }
                });
            }
        }
}
