package com.example.plant_library.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Adapter.GardenInformationAdapter;
import com.example.plant_library.Adapter.StageAdapter;
import com.example.plant_library.CircleDecorator;
import com.example.plant_library.Decorator.VDotDecorator;
import com.example.plant_library.Decorator.XDotDecorator;
import com.example.plant_library.EventDecorator;
import com.example.plant_library.Object.GardenInformation;
import com.example.plant_library.Object.Stage;
import com.example.plant_library.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class GardenDetailActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;
    private Toolbar toolbarGarden;
    private RecyclerView recyclerView;
    private GardenInformationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_detail);


        toolbarGarden = findViewById(R.id.toolbar_garden);
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
        adapter.setData(getListInfor());
        recyclerView.setAdapter(adapter);

    }
    private List<GardenInformation> getListInfor() {
        List<GardenInformation> gardenInformationList = new ArrayList<>();
        gardenInformationList.add(new GardenInformation("OverView","o12irjfainfjwefbqiuwgfai",R.drawable.img_eye));
        gardenInformationList.add(new GardenInformation("OverView","o12irjfainfjwefbqiuwgfai",R.drawable.img_eye));
        gardenInformationList.add(new GardenInformation("OverView","o12irjfainfjwefbqiuwgfai",R.drawable.img_eye));
        gardenInformationList.add(new GardenInformation("OverView","o12irjfainfjwefbqiuwgfai",R.drawable.img_eye));
        gardenInformationList.add(new GardenInformation("OverView","o12irjfainfjwefbqiuwgfai",R.drawable.img_eye));
        return gardenInformationList;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}