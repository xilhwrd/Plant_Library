package com.example.plant_library.Activity;

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
import java.util.Locale;

import java.util.ArrayList;
import java.util.List;

public class SelectStageActivity extends AppCompatActivity implements OnStageClickListener {
    private RecyclerView recyclerView;
    private StageAdapter stageAdapter;
    private List<StageObj> stageList;
    int plantID;
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
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("plant_infor");
        if (bundle != null) {
             plantID = bundle.getInt("plant_id", -1);
        }
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
        updateStageInDatabase(stageName);
        onBackPressed();
    }
    private void updateStageInDatabase(String stageName) {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference().child("Plants");
        DatabaseReference plantRef = plantsRef.child(String.valueOf(plantID));
        plantRef.child("Stage").child("StageName").setValue(stageName);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Thêm vào bảng Garden nếu chưa tồn tại
        DatabaseReference gardenRef = FirebaseDatabase.getInstance().getReference().child("Garden");
        gardenRef.orderByChild("plantID").equalTo(plantID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Plant ID tồn tại trong bảng Garden
                    Toast.makeText(SelectStageActivity.this, "Plant already exists in the garden", Toast.LENGTH_SHORT).show();
                } else {
                    // Plant ID không tồn tại trong bảng Garden, thêm vào
                    gardenRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int maxId = 0;
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                int currentId = Integer.parseInt(ds.getKey());
                                if (currentId > maxId) {
                                    maxId = currentId;
                                }
                            }
                            int gardenID = maxId + 1; // Tạo ID duy nhất cho Garden

                            Garden newGardenEntry = new Garden(gardenID, plantID, currentDate);
                            gardenRef.child(String.valueOf(gardenID)).setValue(newGardenEntry);

                            Toast.makeText(SelectStageActivity.this, "Đã được thêm vào vườn của bạn", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("com.example.plant_library.UPDATE_GARDEN");
                            LocalBroadcastManager.getInstance(SelectStageActivity.this).sendBroadcast(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Xử lý lỗi nếu cần
                            Log.e("updateStageInDatabase", "Database error: " + databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
                Log.e("updateStageInDatabase", "Database error: " + error.getMessage());
            }
        });
    }
}