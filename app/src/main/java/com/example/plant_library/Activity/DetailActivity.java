package com.example.plant_library.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.plant_library.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ShapeableImageView imgPlant;
    private ImageView imgSun, imgWater, imgCare;
    private TextView plantName, plantScientificName, plantSpecies, plantDescript, plantSize, plantPH, plantTempurature, plantBLoom,
            plantPropagation, plantGrowth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initUI();
        getPlantInformation();
        toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        // Thêm nút back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
            Drawable backArrow = getResources().getDrawable(R.drawable.bg_back_button);


            // Thiết lập Drawable cho nút back trên Toolbar
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void getPlantInformation(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("plant_infor");

        if (bundle != null) {
            int plantId = bundle.getInt("plant_id", -1); // Default value -1 if not found
            String scientificName = bundle.getString("plant_scientificName", "");
            String commonName = bundle.getString("plant_commonName", "");
            String family = bundle.getString("plant_family", "");
            String genus = bundle.getString("plant_genus", "");
            String species = bundle.getString("plant_spieces", "");
            String description = bundle.getString("plant_description");
            String growthRate = bundle.getString("plant_growth_rate", "");
            String lightRequirements = bundle.getString("plant_light", "");
            String waterRequirements = bundle.getString("plant_water", "");
            String careRequirements = bundle.getString("plant_hard", "");
            String soilType = bundle.getString("plant_soil", "");
            String phRange = bundle.getString("plant_ph", "");
            String temperatureRange = bundle.getString("plant_temperature", "");
            String bloomTime = bundle.getString("plant_bloom", "");
            String propagation = bundle.getString("plant_propagation", "");
            String size = bundle.getString("plant_size", "");
            String plantImage = bundle.getString("plant_img", "");



            // Set views
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
}
