package com.example.plant_library.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plant_library.Fragment.LoginFragment1;
import com.example.plant_library.Fragment.LoginFragment2;
import com.example.plant_library.R;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView tvBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvBack = findViewById(R.id.back);
        tvBack.setPaintFlags(tvBack.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

//        FragmentManager fragmentmng = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentmng.beginTransaction();
//        LoginFragment1 loginFragment1 = new LoginFragment1();
//        fragmentTransaction.add(R.id.frame_login, loginFragment1,"fragLogin1");
//        fragmentTransaction.addToBackStack("Frag1");
//        fragmentTransaction.commit();
//
//        tvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            onBackPressed();
//            }
//        });

        addInitialFragment();

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi nhấn vào nút "Back"
                onBackPressed();
            }
        });
    }

    private void addInitialFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment1 loginFragment1 = new LoginFragment1();
        fragmentTransaction.add(R.id.frame_login, loginFragment1, "fragLogin1");
        fragmentTransaction.addToBackStack("Frag1"); // Thêm vào ngăn xếp trở lại
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 1) {
            // Nếu có nhiều hơn một fragment trong ngăn xếp trở lại
            super.onBackPressed(); // Thực hiện hành động back bình thường
        } else {
            // Nếu chỉ còn một fragment trong ngăn xếp trở lại
            // Kết thúc hoạt động (thoát ứng dụng hoặc quay lại màn hình trước đó)
            finish();
        }
    }


}