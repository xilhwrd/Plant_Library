package com.example.plant_library.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.plant_library.R;
import com.google.android.material.textfield.TextInputLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpAcitivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPass;
    AppCompatButton btnLogin, btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivity);

        initUI();

        checkWatcher(edtName);
        checkWatcher(edtEmail);
        checkWatcher(edtPass);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(edtName.length()==0 || edtEmail.length()==0 || edtPass.length()==0 ){
                    Toast.makeText(SignUpAcitivity.this, "Please enter information", Toast.LENGTH_SHORT).show();
                    edtName.requestFocus();
                    imm.showSoftInput(edtName, InputMethodManager.SHOW_IMPLICIT);
                }
                String email = edtEmail.getText().toString().trim();
                if (!isValidEmail(email)) {
                    // Nếu không phải, hiển thị thông báo
                    edtEmail.setError("Email invalid");
                    edtEmail.requestFocus();
                    btnSignUp.setEnabled(false);
                    signUpCheck(btnSignUp);
                }
            }
        });
    }

    private void initUI(){
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpAcitivity.this, LoginActivity.class));
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