package com.example.plant_library.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Adapter.StageAdapter;
import com.example.plant_library.Interface.OnStageClickListener;
import com.example.plant_library.Object.Garden;
import com.example.plant_library.Object.StageObj;
import com.example.plant_library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectStageActivity extends AppCompatActivity implements OnStageClickListener {
    private RecyclerView recyclerView;
    private StageAdapter stageAdapter;
    private List<StageObj> stageList;
    int plantID;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public SelectStageActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stage);

        getBundleData();
        setStageAdapter();

    }

    private void getBundleData(){
        plantID = getIntent().getIntExtra("plant_id", -1);
//        Log.d(TAG, "plant idd" + plantID);
    }
    private void setStageAdapter(){
        recyclerView = findViewById(R.id.rcv_stage);
        stageAdapter = new StageAdapter(this, this, plantID);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        stageAdapter.setData(getListStage());
        recyclerView.setAdapter(stageAdapter);
    }
    private List<StageObj> getListStage() {
        List<StageObj> stageList = new ArrayList<>();
        stageList.add(new StageObj("Trong 2-4 tuần",R.drawable.img_stage1,"Nảy mầm"));
        stageList.add(new StageObj("Trong 4-6 tháng",R.drawable.img_stage2,"Cây giống"));
        stageList.add(new StageObj("Trong 2-3 tháng",R.drawable.img_stage3,"Phát triển"));
        stageList.add(new StageObj("Trong 6-8 tuần",R.drawable.img_stage4,"Nở hoa"));
        return stageList;
    }

    @Override
    public void onStageClick(String stageName) {
        updateStageInDatabase(stageName, plantID);
        upDateHistory(stageName,plantID);
        onBackPressed();
    }
    private void updateStageInDatabase(String stageName, int plantId) {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference().child("Plants");
        DatabaseReference plantRef = plantsRef.child(String.valueOf(plantId));
//        plantRef.child("Stage").child("StageName").setValue(stageName);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        DatabaseReference gardenRef = FirebaseDatabase.getInstance().getReference().child("Garden").child(currentUser.getUid()).child("Plants");
        Map<String, Object> newPlantInstance = new HashMap<>();
        newPlantInstance.put("PlantID", plantId);
        newPlantInstance.put("DatePlanted", currentDate);
        newPlantInstance.put("StageName", stageName);

        gardenRef.push().setValue(newPlantInstance).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SelectStageActivity.this, "Đã được thêm vào vườn của bạn", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("com.example.plant_library.UPDATE_GARDEN");
                    LocalBroadcastManager.getInstance(SelectStageActivity.this).sendBroadcast(intent);
                } else {
                    Toast.makeText(SelectStageActivity.this, "Không thể thêm cây vào vườn. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void upDateHistory(String stageName, int plantId){
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        DatabaseReference gardenRef = FirebaseDatabase.getInstance().getReference().child("History").child(currentUser.getUid()).child("Plants");
        Map<String, Object> newPlantInstance = new HashMap<>();
        newPlantInstance.put("PlantID", plantId);
        newPlantInstance.put("DatePlanted", currentDate);
        newPlantInstance.put("StageName", stageName);

        gardenRef.push().setValue(newPlantInstance).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent("com.example.plant_library.UPDATE_HISTORY");
                    LocalBroadcastManager.getInstance(SelectStageActivity.this).sendBroadcast(intent);
                } else {
                    Toast.makeText(SelectStageActivity.this, "Không thể thêm cây vào vườn. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
