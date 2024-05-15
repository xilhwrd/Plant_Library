package com.example.plant_library.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.plant_library.Activity.LoginActivity;
import com.example.plant_library.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment2 extends Fragment {

    TextView edtEmail;
    View mView;
    AppCompatButton btnSubmit;
    FirebaseAuth mAuth;
    public LoginFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        mView = inflater.inflate(R.layout.fragment_login2, container, false);
        initUI();
        initListener();
        return mView;
    }
    private void initUI(){
        edtEmail = mView.findViewById(R.id.edt_email);
        btnSubmit = mView.findViewById(R.id.btn_submit);
    }
    private void initListener(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                String email = edtEmail.getText().toString().trim();
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        FragmentManager fragmentmng = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentmng.beginTransaction();
                        LoginFragment1 loginFragment1 = new LoginFragment1();

                        Bundle bundle = new Bundle();
                        bundle.putString("emailUser",edtEmail.getText().toString().trim());
                        loginFragment1.setArguments(bundle);

                        fragmentTransaction.replace(R.id.frame_login, loginFragment1, "fragLogin1");
                        fragmentTransaction.commit();
                        hideProgressBar();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        hideProgressBar();
                    }
                });
            }
        });
    }
    public void showProgressBar() {
        if (getActivity() != null) {
            ((LoginActivity) getActivity()).showProgressBar();
        }
    }
    public void hideProgressBar() {
        if (getActivity() != null) {
            ((LoginActivity) getActivity()).hideProgressBar();
        }
    }
}