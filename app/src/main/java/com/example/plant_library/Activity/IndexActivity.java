package com.example.plant_library.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.plant_library.Fragment.GardenFragment;
import com.example.plant_library.Fragment.HomeFragment;
import com.example.plant_library.Fragment.MeFragment;
import com.example.plant_library.Fragment.SearchFragment;
import com.example.plant_library.FragmentHelper;
import com.example.plant_library.Interface.FragmentHandler;
import com.example.plant_library.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.os.Bundle;
import android.view.MenuItem;

public class IndexActivity extends AppCompatActivity {
   public BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    GardenFragment gardenFragment = new GardenFragment();
    MeFragment meFragment = new MeFragment();
    private FragmentHandler fragmentHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        bottomNavigationView = findViewById(R.id.nav_menu);
        fragmentHandler = new FragmentHelper(getSupportFragmentManager(), R.id.frame_index);
        gardenFragment.setFragmentHandler(fragmentHandler);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_menu:
                        fragmentHandler.showFragment(homeFragment);
                        return true;
                    case R.id.search_menu:
                        fragmentHandler.showFragment(searchFragment);
                        return true;
                    case R.id.garden_menu:
                        fragmentHandler.showFragment(gardenFragment);
                        return true;
                    case R.id.me_menu:
                        fragmentHandler.showFragment(meFragment);
                        return true;
                }
                return false;
            }
        });

        // Add initial fragment
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_index, homeFragment).commit();
        }

    }
    public void navigateToHome(int tabIndex) {
        bottomNavigationView.setSelectedItemId(R.id.home_menu);

        // Sử dụng fragmentHandler để hiển thị HomeFragment
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tab_index", tabIndex);
        homeFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_index, homeFragment);
        fragmentTransaction.commit();
    }
}