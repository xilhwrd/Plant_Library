package com.example.plant_library.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Adapter.GardenInformationAdapter;
import com.example.plant_library.EventDecorator;
import com.example.plant_library.Object.GardenInformation;
import com.example.plant_library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GardenDetailActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;
    private Toolbar toolbarGarden;
    private RecyclerView recyclerView;
    private GardenInformationAdapter adapter;
    private AppCompatButton btnSeeInfor;
    private AppCompatImageButton btnDelete;
    private TextView tvPlantName;
    int plantId;
    String stageName = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_detail);


        btnDelete = findViewById(R.id.btn_delete_garden);
        btnSeeInfor = findViewById(R.id.btn_see_infor);
        toolbarGarden = findViewById(R.id.toolbar_garden);
        tvPlantName = findViewById(R.id.tv_garden_plant_name);
        setSupportActionBar(toolbarGarden);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
            Drawable backArrow = getResources().getDrawable(R.drawable.bg_back_button);
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }
         calendarView = findViewById(R.id.calendar_view);
        // Áp dụng EventDecorator cho sự kiện A (V) và B (X)
        CalendarDay eventDayA = CalendarDay.from(2024, 6, 30);
        CalendarDay eventDayB = CalendarDay.from(2024, 6, 29);

        EventDecorator eventDecoratorA = new EventDecorator(eventDayA, Color.WHITE, true);
        EventDecorator eventDecoratorB = new EventDecorator(eventDayB, Color.BLACK, false);

        calendarView.addDecorator(eventDecoratorA);
        calendarView.addDecorator(eventDecoratorB);
        calendarView.invalidate();


        recyclerView = findViewById(R.id.rcv_garden_information);
        adapter = new GardenInformationAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        getInfor();
        setClick();
    }
    public void setClick(){
        btnSeeInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GardenDetailActivity.this, DetailActivity.class);
                intent.putExtra("plantID", plantId);
                startActivity(intent);

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(GardenDetailActivity.this)
                        .setTitle("Xóa cây")
                        .setMessage("Bạn có chắc chắn muốn xóa cây này không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Xóa cây từ Firebase
                                deletePlantFromGarden(plantId);
                            }
                        })
                        .setNegativeButton("Không", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }
    private void deletePlantFromGarden(int plantID) {
        DatabaseReference gardenRef = FirebaseDatabase.getInstance().getReference().child("Garden");
        Query query = gardenRef.orderByChild("plantID").equalTo(plantID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(GardenDetailActivity.this, "Đã xóa cây thành công!", Toast.LENGTH_SHORT).show();
                                        // Gửi broadcast để cập nhật GardenFragment
                                        Intent intent = new Intent("com.example.plant_library.UPDATE_GARDEN");
                                        LocalBroadcastManager.getInstance(GardenDetailActivity.this).sendBroadcast(intent);

                                        finish();
                                    } else {
                                        Toast.makeText(GardenDetailActivity.this, "Xóa cây thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Toast.makeText(GardenDetailActivity.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getInfor() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("plant_infor_garden");

        if (bundle != null) {
            plantId = bundle.getInt("plant_id", -1); // Default value -1 if not found
            String plantName = bundle.getString("plant_commonName");
            stageName = bundle.getString("plant_stage_name");
            int stageDay1 = bundle.getInt("plant_stage_day1");
            int stageDay2 = bundle.getInt("plant_stage_day2");
            int stageDay3 = bundle.getInt("plant_stage_day3");
            int stageDay4 = bundle.getInt("plant_stage_day4");

            String lightRate = bundle.getString("plant_light_rate");
            String lightStage1 = bundle.getString("plant_light_stage1");
            String lightStage2 = bundle.getString("plant_light_stage2");
            String lightStage3 = bundle.getString("plant_light_stage3");
            String lightStage4 = bundle.getString("plant_light_stage4");

            String waterRate = bundle.getString("plant_water_rate");
            String waterStage1 = bundle.getString("plant_water_stage1");
            String waterStage2 = bundle.getString("plant_water_stage2");
            String waterStage3 = bundle.getString("plant_water_stage3");
            String waterStage4 = bundle.getString("plant_water_stage4");

            String careRate = bundle.getString("plant_hard_rate");
            String careStage = bundle.getString("plant_hard_stage");

            String temperatureRate = bundle.getString("plant_temperature");
            String temperatureStage = bundle.getString("plant_temperature_stage");

            String feztStage = bundle.getString("plant_fert", "");
            String pestsStage = bundle.getString("plant_pests", "");
            tvPlantName.setText(plantName);
            List<GardenInformation> gardenInformationList = new ArrayList<>();
            switch (stageName) {
                case "Nảy mầm":
                    gardenInformationList =getListInfor (stageName, careStage, waterStage1, lightStage1, temperatureStage, feztStage, pestsStage);
                    break;
                case "Cây giống":
                    gardenInformationList =getListInfor(stageName, careStage, waterStage2, lightStage2, temperatureStage, feztStage, pestsStage);
                    break;
                case "Phát triển":
                    gardenInformationList =getListInfor(stageName, careStage, waterStage3, lightStage3, temperatureStage, feztStage, pestsStage);
                    break;
                case "Nở hoa":
                    gardenInformationList =getListInfor(stageName, careStage, waterStage4, lightStage4, temperatureStage, feztStage, pestsStage);
                    break;
                default:
                    Log.e("getInfor", "Unknown stage name: " + stageName);
                    break;
            }
            adapter.setData(gardenInformationList);
            recyclerView.setAdapter(adapter);
        }
    }
    private List<GardenInformation> getListInfor(String overView, String careDescript, String water, String light, String tempurature, String fert, String pests) {
        List<GardenInformation> gardenInformationList = new ArrayList<>();

        gardenInformationList.add(new GardenInformation("Tổng quan","Cây của bạn đang ở giai đoạn " +overView,R.drawable.img_eye));
        gardenInformationList.add(new GardenInformation("Độ chăm sóc",careDescript,R.drawable.img_care_garden));
        gardenInformationList.add(new GardenInformation("Nước",water,R.drawable.img_water_garden));
        gardenInformationList.add(new GardenInformation("Ánh sáng",light,R.drawable.img_light_garden));
        gardenInformationList.add(new GardenInformation("Nhiệt độ",tempurature,R.drawable.img_tempu_garden));
        gardenInformationList.add(new GardenInformation("Bón phân",fert,R.drawable.img_fert_garden));
        gardenInformationList.add(new GardenInformation("Sâu bọ",pests,R.drawable.img_pests_garden));
        return gardenInformationList;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}