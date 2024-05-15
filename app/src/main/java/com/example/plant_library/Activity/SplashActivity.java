package com.example.plant_library.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plant_library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//              nextActivity();
                Intent i = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(i);
            }

        },2000);
    }

    private void nextActivity() {
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       if (user == null){
           Intent i = new Intent(SplashActivity.this, SignUpActivity.class);
           startActivity(i);
       }else{
          Intent i = new Intent(SplashActivity.this, MainActivity.class);
          startActivity(i);
       }
    }

}