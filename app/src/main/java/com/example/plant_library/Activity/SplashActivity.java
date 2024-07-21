package com.example.plant_library.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plant_library.BootReceiver;
import com.example.plant_library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {
    View view;
    ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgLogo = findViewById(R.id.img_logo);
        createNotificationChannel();
        setDailyCheckAlarm(this);
        setAnim();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
                finish();
            }
        },1800);
    }

    private void nextActivity() {
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       if (user != null){
           Intent i = new Intent(SplashActivity.this, IndexActivity.class);
           startActivity(i);
       }else{
           Intent i = new Intent(SplashActivity.this, OnboardingActivity.class);
           startActivity(i);
       }
    }
    private void setAnim(){
        AnimationSet animationSet = new AnimationSet(true);
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_splash);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_scale_splash);
        Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_splash);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        imgLogo.startAnimation(animationSet);

        AnimationSet animationSet2 = new AnimationSet(true);
        Animation rotateAnimation2 = AnimationUtils.loadAnimation(this, R.anim.anim_splash2);
        Animation scaleAnimation2 = AnimationUtils.loadAnimation(this, R.anim.anim_scale_splash2);
        animationSet2.addAnimation(scaleAnimation2);
        animationSet2.addAnimation(rotateAnimation2);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgLogo.startAnimation(animationSet2);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnimationSet animationSet3 = new AnimationSet(true);
                        Animation rotateAnimation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_splash3);
                        Animation scaleAnimation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale_splash3);
                        animationSet3.addAnimation(scaleAnimation3);
                        animationSet3.addAnimation(rotateAnimation3);
                        imgLogo.startAnimation(animationSet3);
                        imgLogo.setVisibility(View.GONE);
                    }
                },200);
            }
        },1000);

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Watering Reminder";
            String description = "Channel for watering reminders";
            int importance = NotificationManager.IMPORTANCE_HIGH; // Đảm bảo thông báo hiển thị nổi bật
            NotificationChannel channel = new NotificationChannel("WateringReminderChannel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void setDailyCheckAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BootReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}