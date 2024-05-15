package com.example.plant_library.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.plant_library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPass;
    ProgressBar prgSignUp;
    AppCompatButton btnLogin, btnSignUp;
    FirebaseAuth mAuth;
    private static final String TAG = "tagg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivity);
        mAuth = FirebaseAuth.getInstance();
        initUI();

        checkWatcher(edtName);
        checkWatcher(edtEmail);
        checkWatcher(edtPass);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prgSignUp.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();

                if (edtName.length() == 0 || edtEmail.length() == 0 || edtPass.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Please enter information", Toast.LENGTH_SHORT).show();
                    edtName.requestFocus();
                    imm.showSoftInput(edtName, InputMethodManager.SHOW_IMPLICIT);
                }
                if (!isValidEmail(email)) {
                    edtEmail.setError("Email invalid");
                    edtEmail.requestFocus();
                    btnSignUp.setEnabled(false);
                    signUpCheck(btnSignUp);
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        prgSignUp.setVisibility(View.GONE);
                                        Toast.makeText(SignUpActivity.this, "đăng ký ọk", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }

    private void initUI(){
        prgSignUp = findViewById(R.id.prg_SignUp);
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_left_to_right);
            }
        });
    }
    private void checkWatcher(EditText edt){
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(edt.getText())){
                    btnSignUp.setEnabled(false);
                    signUpCheck(btnSignUp);
                }else
                    btnSignUp.setEnabled(true);
                    signUpCheck(btnSignUp);
            }
        });
    }
    private boolean signUpCheck(Button btnSign){
        if(btnSign.isEnabled() == true){
            btnSignUp.setBackground(getDrawable(R.drawable.bg_button_signup));
        }else  btnSignUp.setBackground(getDrawable(R.drawable.bg_button_signup_false));
        return false;
    }
    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}