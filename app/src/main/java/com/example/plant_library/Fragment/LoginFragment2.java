package com.example.plant_library.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.plant_library.R;

public class LoginFragment2 extends Fragment {
    View mView;
    AppCompatButton btnSubmit;
    public LoginFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_login2, container, false);
        btnSubmit = mView.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmng = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentmng.beginTransaction();
                LoginFragment3 loginFragment3 = new LoginFragment3();
                fragmentTransaction.replace(R.id.frame_login, loginFragment3, "fragLogin2");
                fragmentTransaction.addToBackStack("Frag2");
                fragmentTransaction.commit();
            }
        });


        return mView;
    }
}