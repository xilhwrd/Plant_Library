package com.example.plant_library.Fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.plant_library.Activity.LoginActivity;
import com.example.plant_library.Activity.MainActivity;
import com.example.plant_library.Activity.SignUpActivity;
import com.example.plant_library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment1 extends Fragment {
    EditText edtEmail, edtPass;
    TextView tvForgot;
    AppCompatButton btnLogIn;
    ImageView imgViewGoogle;
    View mView;
    FirebaseAuth mAuth;
    public LoginFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_login1, container, false);

        initUI();
        initListener();
        setEmail();
        return mView;
    }
    private void initUI(){
        edtEmail        = mView.findViewById(R.id.edt_email);
        edtPass         = mView.findViewById(R.id.edt_password);
        tvForgot        = mView.findViewById(R.id.tv_fogot_pass);
        imgViewGoogle   = mView.findViewById(R.id.google_img);
        btnLogIn        = mView.findViewById(R.id.btn_login);
    }
    private void initListener(){
        tvForgot.setPaintFlags(tvForgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmng = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentmng.beginTransaction();
                LoginFragment2 loginFragment2 = new LoginFragment2();
                LoginFragment1 loginFragment1 = new LoginFragment1();
                fragmentTransaction.replace(R.id.frame_login, loginFragment2, "fragLogin2");
                fragmentTransaction.remove(loginFragment1);
                fragmentTransaction.addToBackStack("Frag2");
                fragmentTransaction.commit();
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getActivity(), "đăng nhập ọk", Toast.LENGTH_SHORT).show();
                                    hideProgressBar();
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    hideProgressBar();
                                }
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
    private void setEmail(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String data = bundle.getString("emailUser");
            edtEmail.setText(data.toString().trim());
        }
    }
}