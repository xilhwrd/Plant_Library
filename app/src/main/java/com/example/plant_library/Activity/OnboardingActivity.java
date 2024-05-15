package com.example.plant_library.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.plant_library.Adapter.ViewPagerAdapter;
import com.example.plant_library.R;

import me.relex.circleindicator.CircleIndicator;

public class OnboardingActivity extends AppCompatActivity {

    private Button btnNext;
    private TextView tvSkip;
    private ViewPager viewPager;
    private RelativeLayout relativeLayout;
    private CircleIndicator circleIndicator;
    private LinearLayout linearLayout;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboardingmain);

        initUi();
        buttonNextClick();
        Lifecycle lifecycle = getLifecycle();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        circleIndicator.setViewPager(viewPager);
        viewPagerWatcher();


    }

    private void viewPagerWatcher(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 || position == 1){
                    btnNext.setText("Next");
                    tvSkip.setVisibility(View.VISIBLE);
                    buttonNextClick();
                }
                if(position == 2){
                    tvSkip.setVisibility(View.INVISIBLE);
                    btnNext.setText("Start");
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(OnboardingActivity.this, SplashActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_left_to_right);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void buttonNextClick(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem()<2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });
    }
    private void initUi(){
        tvSkip = findViewById(R.id.skip);
        viewPager = findViewById(R.id.view_pager);
        relativeLayout = findViewById(R.id.relative_layout_main);
        circleIndicator = findViewById(R.id.circleIndi);
        linearLayout = findViewById(R.id.linear_layout_main);
        btnNext = findViewById(R.id.btn_next);

        tvSkip.setPaintFlags(tvSkip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnboardingActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_left_to_right);
            }
        });
    }
}