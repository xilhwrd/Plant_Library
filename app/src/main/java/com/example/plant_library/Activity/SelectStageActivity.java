package com.example.plant_library.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Adapter.StageAdapter;
import com.example.plant_library.Object.Stage;
import com.example.plant_library.R;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SelectStageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StageAdapter stageAdapter;
    private List<Stage> stageList;

    public SelectStageActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stage);

        recyclerView = findViewById(R.id.rcv_stage);
        stageAdapter = new StageAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        stageAdapter.setData(getListStage());
        recyclerView.setAdapter(stageAdapter);
    }
    private List<Stage> getListStage() {
        List<Stage> stageList = new ArrayList<>();
        stageList.add(new Stage("Trong 2-4 tuần",R.drawable.img_stage1,"Nảy mầm"));
        stageList.add(new Stage("Trong 4-6 tháng",R.drawable.img_stage2,"Cây giống"));
        stageList.add(new Stage("Trong 2-3 tháng",R.drawable.img_stage3,"Phát triển"));
        stageList.add(new Stage("Trong 6-8 tuần",R.drawable.img_stage4,"Nở hoa"));
        return stageList;
    }
}