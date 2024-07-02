package com.example.plant_library.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Adapter.PlantsAdapter;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Plants;
import com.example.plant_library.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements RecyclerViewInterface {
    private Toolbar toolbar;
    private ShapeableImageView imgPlant;
    private ImageView imgSun, imgWater, imgCare, imgBack, imgFavorite;
    private RecyclerView recyclerView;
    private AppCompatButton addToGarden;
    private TextView plantName, plantScientificName, plantSpecies, plantDescript, plantSize, plantPH, plantTempurature, plantBLoom,
            plantPropagation, plantGrowth;
    private List<Plants> plantsList;
    private PlantsAdapter plantsAdapter;
    private boolean isCheck = false;
    String family = null;
    int plantId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initUI();
        getPlantInformation();
        toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        setToolBarClick();
        setSuggestPlantsAdapter();
//        getFromGarden();
        setButtonClick();
        // Thêm nút back
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setTitle(null);
//            Drawable backArrow = getResources().getDrawable(R.drawable.bg_back_button);
//
//
//            // Thiết lập Drawable cho nút back trên Toolbar
//            getSupportActionBar().setHomeAsUpIndicator(backArrow);
//        }

    }

    private void setButtonClick(){
        addToGarden = findViewById(R.id.btn_add_garden);
        addToGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putInt("plant_id",plantId);
                Intent intent = new Intent(DetailActivity.this, SelectStageActivity.class);
                intent.putExtra("plant_infor", bundle);
                startActivity(intent);
            }
        });
    }

    private void setToolBarClick(){
        imgBack = findViewById(R.id.back_img);
        imgFavorite = findViewById(R.id.favorite_img);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck == false) {
                    imgFavorite.setImageResource(R.drawable.bg_favorite_button_selected);
                    isCheck = true;
                }else {
                    imgFavorite.setImageResource(R.drawable.bg_favorite_button);
                    isCheck = false;
                }
            }
        });
    }

    private void initUI(){
        imgPlant = findViewById(R.id.img_plant_detail);
        imgSun = findViewById(R.id.img_sun_detail);
        imgWater = findViewById(R.id.img_water_detail);
        imgCare = findViewById(R.id.img_hard_detail);

        plantName = findViewById(R.id.tv_plants_name_detail);
        plantSize =findViewById(R.id.tv_mature_size);
        plantSpecies = findViewById(R.id.tv_plants_spieces_detail);
        plantDescript = findViewById(R.id.tv_plants_descipt_detail);
        plantPH = findViewById(R.id.tv_ph_range);
        plantTempurature = findViewById(R.id.tv_temperature);
        plantBLoom = findViewById(R.id.tv_bloom_time);
        plantPropagation = findViewById(R.id.tv_propagation);
        plantGrowth = findViewById(R.id.tv_growth_rate);

    }
//    private void getFromGarden(){
//        Intent intent = getIntent();
//        plantId = intent.getIntExtra("plantID", -1); // Default value -1 if not found
//
//        if (plantId != -1) {
//            DatabaseReference plantRef = FirebaseDatabase.getInstance().getReference().child("Plants").child(String.valueOf(plantId));
//            plantRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        String scientificName = dataSnapshot.child("ScientificName").getValue(String.class);
//                        String commonName = dataSnapshot.child("CommonName").getValue(String.class);
//                        String family = dataSnapshot.child("Family").getValue(String.class);
//                        String genus = dataSnapshot.child("Genus").getValue(String.class);
//                        String species = dataSnapshot.child("Species").getValue(String.class);
//                        String description = dataSnapshot.child("Description").getValue(String.class);
//                        String growthRate = dataSnapshot.child("GrowthRate").getValue(String.class);
//                        String lightRequirements = dataSnapshot.child("LightRequirements").child("LightRate").getValue(String.class);
//                        String waterRequirements = dataSnapshot.child("WaterRequirements").child("WaterRate").getValue(String.class);
//                        String careRequirements = dataSnapshot.child("CareRequirements").child("CareRate").getValue(String.class);
//                        String soilType = dataSnapshot.child("SoilType").getValue(String.class);
//                        String phRange = dataSnapshot.child("PHRange").getValue(String.class);
//                        String temperatureRange = dataSnapshot.child("TemperatureRange").child("TemperatureRate").getValue(String.class);
//                        String bloomTime = dataSnapshot.child("Bloomtime").getValue(String.class);
//                        String propagation = dataSnapshot.child("Propagation").getValue(String.class);
//                        String size = dataSnapshot.child("Size").getValue(String.class);
//                        String plantImage = dataSnapshot.child("PlantImage").getValue(String.class);
//                        Picasso.get().load(plantImage).into(imgPlant);
//                        plantName.setText(commonName);
//                        plantSize.setText(size);
//                        plantSpecies.setText(species);
//                        plantDescript.setText(description);
//                        plantPH.setText(phRange);
//                        plantTempurature.setText(temperatureRange);
//                        plantBLoom.setText(bloomTime);
//                        plantPropagation.setText(propagation);
//                        plantGrowth.setText(growthRate);
//                        switch (lightRequirements) {
//                            case "1":
//                                imgSun.setImageResource(R.drawable.img_sun_level1);
//                                break;
//                            case "2":
//                                imgSun.setImageResource(R.drawable.img_sun_level2);
//                                break;
//                            case "3":
//                                imgSun.setImageResource(R.drawable.img_sun_level3);
//                                break;
//                            default:
//                                imgSun.setImageResource(R.drawable.img_sun_level1);
//                                break;
//                        }
//
//                        switch (waterRequirements) {
//                            case "1":
//                                imgWater.setImageResource(R.drawable.img_water_level1);
//                                break;
//                            case "2":
//                                imgWater.setImageResource(R.drawable.img_water_level2);
//                                break;
//                            case "3":
//                                imgWater.setImageResource(R.drawable.img_water_level3);
//                                break;
//                            default:
//                                imgWater.setImageResource(R.drawable.img_water_level1);
//                                break;
//                        }
//
//                        switch (careRequirements) {
//                            case "1":
//                                imgCare.setImageResource(R.drawable.img_hard_level1);
//                                break;
//                            case "2":
//                                imgCare.setImageResource(R.drawable.img_hard_level2);
//                                break;
//                            case "3":
//                                imgCare.setImageResource(R.drawable.img_hard_level3);
//                                break;
//                            default:
//                                imgCare.setImageResource(R.drawable.img_hard_level1);
//                                break;
//                        }
//                } else {
//                    Log.e("DetailActivity", "Plant does not exist");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("DetailActivity", "Database error: " + databaseError.getMessage());
//            }
//        });
//    } else {
//        Log.e("DetailActivity", "Invalid plant ID");
//    }
//
//    }
    private void getPlantInformation(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("plant_infor");

        if (bundle != null) {
             plantId = bundle.getInt("plant_id", -1); // Default value -1 if not found
            String scientificName = bundle.getString("plant_scientificName", "");
            String commonName = bundle.getString("plant_commonName", "");
            family = bundle.getString("plant_family", "");
            String genus = bundle.getString("plant_genus", "");
            String species = bundle.getString("plant_spieces", "");
            String description = bundle.getString("plant_description");
            String growthRate = bundle.getString("plant_growth_rate", "");
            String lightRequirements = bundle.getString("plant_light_rate", "");
            String waterRequirements = bundle.getString("plant_water_rate", "");
            String careRequirements = bundle.getString("plant_hard_rate", "");
            String soilType = bundle.getString("plant_soil", "");
            String phRange = bundle.getString("plant_ph", "");
            String temperatureRange = bundle.getString("plant_temperature", "");
            String bloomTime = bundle.getString("plant_bloom", "");
            String propagation = bundle.getString("plant_propagation", "");
            String size = bundle.getString("plant_size", "");
            String plantImage = bundle.getString("plant_img", "");


            Picasso.get().load(plantImage).into(imgPlant);
            plantName.setText(commonName);
            plantSize.setText(size);
            plantSpecies.setText(species);
            plantDescript.setText(description);
            plantPH.setText(phRange);
            plantTempurature.setText(temperatureRange);
            plantBLoom.setText(bloomTime);
            plantPropagation.setText(propagation);
            plantGrowth.setText(growthRate);
            switch (lightRequirements) {
                case "1":
                    imgSun.setImageResource(R.drawable.img_sun_level1);
                    break;
                case "2":
                    imgSun.setImageResource(R.drawable.img_sun_level2);
                    break;
                case "3":
                    imgSun.setImageResource(R.drawable.img_sun_level3);
                    break;
                default:
                    imgSun.setImageResource(R.drawable.img_sun_level1);
                    break;
            }

            switch (waterRequirements) {
                case "1":
                    imgWater.setImageResource(R.drawable.img_water_level1);
                    break;
                case "2":
                    imgWater.setImageResource(R.drawable.img_water_level2);
                    break;
                case "3":
                    imgWater.setImageResource(R.drawable.img_water_level3);
                    break;
                default:
                    imgWater.setImageResource(R.drawable.img_water_level1);
                    break;
            }

            switch (careRequirements) {
                case "1":
                    imgCare.setImageResource(R.drawable.img_hard_level1);
                    break;
                case "2":
                    imgCare.setImageResource(R.drawable.img_hard_level2);
                    break;
                case "3":
                    imgCare.setImageResource(R.drawable.img_hard_level3);
                    break;
                default:
                    imgCare.setImageResource(R.drawable.img_hard_level1);
                    break;
            }
        } else {
            Log.e("DetailActivity", "Bundle is null");
        }
    }

    private void setSuggestPlantsAdapter(){
        recyclerView = findViewById(R.id.rcv_suggest);
        plantsList = new ArrayList<>();
        plantsAdapter = new PlantsAdapter(plantsList, this, this, R.id.rcv_suggest);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(plantsAdapter);
        getListSuggestPlants();


    }
    private void getListSuggestPlants() {
        DatabaseReference plantsRef = FirebaseDatabase.getInstance().getReference("Plants");
        plantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plants plant = snapshot.getValue(Plants.class);
//                    if (plant != null && plant.getFamily().equals(family) && plant.getPlantID() != plantId ) {
//                        plantsList.add(plant);
//                    }
                    if (plant != null && family != null) {
                        if (plant.getFamily() != null && plant.getFamily().equals(family) && plant.getPlantID() != plantId) {
                            plantsList.add(plant);
                        }
                        else Log.d(TAG, "lxỗiĩoixỗi");
                    }
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plantsAdapter.setShowShimmer(false);
                    }
                }, 3000);
                Log.d(TAG, "Number of plants loaded: " + plantsList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int recyclerViewId, int position) {

    }
}
