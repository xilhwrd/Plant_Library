package com.example.plant_library.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.plant_library.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpFinishActivity extends AppCompatActivity {
    AppCompatButton btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_finish);

        btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpFinishActivity.this, IndexActivity.class));
                finish();
            }
        });
    }
}