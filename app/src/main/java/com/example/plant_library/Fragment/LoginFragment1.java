package com.example.plant_library.Fragment;

import android.graphics.Paint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.plant_library.Activity.LoginActivity;
import com.example.plant_library.R;

public class LoginFragment1 extends Fragment {
    EditText edtEmail, edtPass;
    TextView tvForgot;
    AppCompatButton btnLogIn;
    ImageView imgViewGoogle;
    View mView;

    public LoginFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_login1, container, false);

        initUI();
        return mView;
    }
    private void initUI(){
        edtEmail        = mView.findViewById(R.id.edt_email);
        edtPass         = mView.findViewById(R.id.edt_password);
        tvForgot        = mView.findViewById(R.id.tv_fogot_pass);
        imgViewGoogle   = mView.findViewById(R.id.google_img);
        btnLogIn        = mView.findViewById(R.id.btn_login);

        tvForgot.setPaintFlags(tvForgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmng = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentmng.beginTransaction();
                LoginFragment2 loginFragment2 = new LoginFragment2();
                fragmentTransaction.replace(R.id.frame_login, loginFragment2, "fragLogin2");
                fragmentTransaction.addToBackStack("Frag2");
                fragmentTransaction.commit();

            }
        });

    }

}