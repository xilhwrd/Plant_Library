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
import com.example.plant_library.Object.PlantInstance;
import com.example.plant_library.R;
import com.example.plant_library.WateringReminderReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GardenDetailActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;
    private Toolbar toolbarGarden;
    private RecyclerView recyclerView;
    private GardenInformationAdapter adapter;
    private AppCompatButton btnSeeInfor;
    private AppCompatImageButton btnDelete;
    private TextView tvPlantName, tvInfor, tvInfor2;
    private MaterialCardView materialCardView;
    private List<CalendarDay> wateringDays = new ArrayList<>();
    int plantId;
    String plantKey = null;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String stageNameView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_detail);


        btnDelete = findViewById(R.id.btn_delete_garden);
        btnSeeInfor = findViewById(R.id.btn_see_infor);
        toolbarGarden = findViewById(R.id.toolbar_garden);
        tvPlantName = findViewById(R.id.tv_garden_plant_name);
        tvInfor = findViewById(R.id.tv_infor);
        tvInfor2 = findViewById(R.id.tv_infor2);
        materialCardView = findViewById(R.id.cardView_garden_detail);
        setSupportActionBar(toolbarGarden);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
            Drawable backArrow = getResources().getDrawable(R.drawable.bg_back_button);
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }
         calendarView = findViewById(R.id.calendar_view);
        calendarView.setClickable(false);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        // Áp dụng EventDecorator cho sự kiện A (V) và B (X)
        CalendarDay eventDayA = CalendarDay.from(2024, 6, 30);
        CalendarDay eventDayB = CalendarDay.from(2024, 6, 29);
//
//        EventDecorator eventDecoratorA = new EventDecorator(eventDayA, R.color.main_color, true);
//        EventDecorator eventDecoratorB = new EventDecorator(eventDayB, Color.BLACK, false);
//
//        calendarView.addDecorator(eventDecoratorA);
//        calendarView.addDecorator(eventDecoratorB);
//        calendarView.invalidate();

        recyclerView = findViewById(R.id.rcv_garden_information);
        adapter = new GardenInformationAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        getInfor();
        setClick();
    }
    public void setClick(){

        tvInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInfor2.setText("Nếu ngày được đánh dấu màu xanh, điều đó nói rằng bạn sẽ cần tưới nước cho cây");
                materialCardView.setVisibility(View.VISIBLE);
            }
        });
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
                                deletePlantFromGarden(plantKey);
                            }
                        })
                        .setNegativeButton("Không", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }
    private void deletePlantFromGarden(String plantKey) {
        DatabaseReference gardenRef = FirebaseDatabase.getInstance().getReference().child("Garden").child(currentUser.getUid()).child("Plants").child(plantKey);

        gardenRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Xử lý lỗi nếu có
                Toast.makeText(GardenDetailActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getInfor() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("plant_infor_garden");

        if (bundle != null) {
            plantKey = bundle.getString("plant_key");
            plantId = bundle.getInt("plant_id", -1); // Default value -1 if not found
            String plantName = bundle.getString("plant_commonName");
            stageNameView = bundle.getString("plant_stage_name");
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
            String waterStage1Description = bundle.getString("plant_water_stage1_description");
            int waterStage1Interval = bundle.getInt("plant_water_stage1_interval");
            String waterStage2Description = bundle.getString("plant_water_stage2_description");
            int waterStage2Interval = bundle.getInt("plant_water_stage2_interval");
            String waterStage3Description = bundle.getString("plant_water_stage3_description");
            int waterStage3Interval = bundle.getInt("plant_water_stage3_interval");
            String waterStage4Description = bundle.getString("plant_water_stage4_description");
            int waterStage4Interval = bundle.getInt("plant_water_stage4_interval");


//            String waterStage1 = bundle.getString("plant_water_stage1");
//            String waterStage2 = bundle.getString("plant_water_stage2");
//            String waterStage3 = bundle.getString("plant_water_stage3");
//            String waterStage4 = bundle.getString("plant_water_stage4");

            String careRate = bundle.getString("plant_hard_rate");
            String careStage = bundle.getString("plant_hard_stage");

            String temperatureRate = bundle.getString("plant_temperature");
            String temperatureStage = bundle.getString("plant_temperature_stage");

            String feztStage = bundle.getString("plant_fert", "");
            String pestsStage = bundle.getString("plant_pests", "");
            tvPlantName.setText(plantName);

            DatabaseReference gardenRef = FirebaseDatabase.getInstance().getReference("Garden").child(currentUser.getUid()).child("Plants").child(plantKey);
            gardenRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot gardenSnapshot) {
                    List<GardenInformation> gardenInformationList = new ArrayList<>();
//                    for (DataSnapshot snapshot : gardenSnapshot.getChildren()) {
//                        PlantInstance plantInstance = snapshot.getValue(PlantInstance.class);
                    if (gardenSnapshot.exists()) {
                        PlantInstance plantInstance = gardenSnapshot.getValue(PlantInstance.class);
                        Log.e("getInfor", "Database error: " + plantKey);
                        if (plantInstance != null && plantInstance.getPlantID() == plantId ) {
                            String stageName = plantInstance.getStageName();
                            String datePlanted = plantInstance.getDatePlanted();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            try {
                                Date datePlantedDate = dateFormat.parse(datePlanted);
                                Date currentDate = new Date(); // Current date
                                long diff = currentDate.getTime() - datePlantedDate.getTime() ;
                                long daysSincePlanted = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                                //chuyển giai đoạn
                                boolean stageChanged = false;
                                switch (stageName) {
                                    case "Nảy mầm":
                                        if (daysSincePlanted >= stageDay1) {
                                            stageName = "Cây giống";
                                            gardenRef.child("StageName").setValue(stageName);
                                            stageChanged = true;
                                        }
                                        break;
                                    case "Cây giống":
                                        if (daysSincePlanted >= stageDay1 + stageDay2) {
                                            stageName = "Phát triển";
                                            gardenRef.child("StageName").setValue(stageName);
                                            stageChanged = true;
                                        }
                                        break;
                                    case "Phát triển":
                                        if (daysSincePlanted >= stageDay1 + stageDay2 + stageDay3) {
                                            stageName = "Nở hoa";
                                            gardenRef.child("StageName").setValue(stageName);
                                            stageChanged = true;
                                        }
                                        break;
                                    case "Nở hoa":
                                        // Đây là giai đoạn cuối, không cần chuyển
                                        break;
                                    default:
                                        Log.e("getInfor", "Unknown stage name: " + stageName);
                                        break;
                                }

                                if (stageChanged) {
                                    // Cập nhật lại dữ liệu giai đoạn sau khi chuyển giai đoạn
                                    gardenRef.child("StageName").setValue(stageName);
                                }

                                //set thông tin
                            switch (stageName) {
                                case "Nảy mầm":
                                    wateringDays.clear();
                                    addWateringDays(wateringDays, datePlantedDate, waterStage1Interval, stageDay1);
                                    gardenInformationList = getListInfor(daysSincePlanted +1,stageName,stageNameView, careStage, waterStage1Description, lightStage1, temperatureStage, feztStage, pestsStage);
                                    break;
                                case "Cây giống":
                                    wateringDays.clear();
                                    addWateringDays(wateringDays, datePlantedDate, waterStage2Interval, stageDay2);
                                    gardenInformationList = getListInfor(daysSincePlanted +1,stageName,stageNameView, careStage, waterStage2Description, lightStage2, temperatureStage, feztStage, pestsStage);
                                    break;
                                case "Phát triển":
                                    wateringDays.clear();
                                    addWateringDays(wateringDays, datePlantedDate, waterStage3Interval, stageDay3);
                                    gardenInformationList = getListInfor(daysSincePlanted+1,stageName,stageNameView, careStage, waterStage3Description, lightStage3, temperatureStage, feztStage, pestsStage);
                                    break;
                                case "Nở hoa":
                                    wateringDays.clear();
                                    addWateringDays(wateringDays, datePlantedDate, waterStage4Interval, stageDay4);
                                    gardenInformationList = getListInfor(daysSincePlanted+1,stageName,stageNameView, careStage, waterStage4Description, lightStage4, temperatureStage, feztStage, pestsStage);
                                    break;
                                default:
                                    Log.e("getInfor", "Unknown stage name: " + stageName);
                                    break;
                            }
                            EventDecorator eventDecorator = new EventDecorator(wateringDays,getColor(R.color.main_color), false);
                            calendarView.addDecorator(eventDecorator);
                                calendarView.invalidate();
                                calendarView.invalidateDecorators();
                                setWateringReminders(wateringDays, plantName);
                            }catch (ParseException e) {
                                e.printStackTrace();
                            }
                    }
                    }
            adapter.setData(gardenInformationList);
            recyclerView.setAdapter(adapter);
        }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Log.e("getInfor", "Database error: " + error.getMessage());
                }
            });
            }
    }
    private List<GardenInformation> getListInfor(Long date,String stage, String overView, String careDescript, String water, String light, String tempurature, String fert, String pests) {
        List<GardenInformation> gardenInformationList = new ArrayList<>();

        gardenInformationList.add(new GardenInformation("Tổng quan","Cây của bạn đã thêm vào vườn được "+date+" ngày và đang ở giai đoạn: " +stage+ "\n"+overView,R.drawable.img_eye));
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
    private void addWateringDays(List<CalendarDay> wateringDays, Date startDate, int interval, int stageDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        wateringDays.add(CalendarDay.from(calendar));
        // Thêm các ngày tiếp theo với khoảng cách là interval
        for (int i = 0; i < stageDays; i += interval) {
            if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
                wateringDays.add(CalendarDay.from(calendar));
                Log.d("addWateringDays", "Added watering day: " + calendar.getTime().toString());
            }
            calendar.add(Calendar.DAY_OF_MONTH, interval);
        }
    }
    private void scheduleWateringReminder(Context context, CalendarDay wateringDay, String plantName, int notificationId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, wateringDay.getYear());
        calendar.set(Calendar.MONTH, wateringDay.getMonth() - 1); // Tháng trong Calendar bắt đầu từ 0
        calendar.set(Calendar.DAY_OF_MONTH, wateringDay.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, 8); // Thời gian trong ngày khi thông báo (8 giờ sáng)
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            return; // Bỏ qua ngày đã qua
        }
        Intent intent = new Intent(context, WateringReminderReceiver.class);
        intent.putExtra("plant_name", plantName);
        intent.putExtra("notification_id", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private void setWateringReminders(List<CalendarDay> wateringDays, String plantName) {
        int notificationId = 0;
        for (CalendarDay day : wateringDays) {
            scheduleWateringReminder(this, day, plantName, notificationId++);
        }
    }
}