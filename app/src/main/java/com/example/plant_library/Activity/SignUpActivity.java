package com.example.plant_library.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.plant_library.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtName, edtEmail, edtPass;
    private ProgressBar prgSignUp;
    private ImageView imgGoogle;
    private AppCompatButton btnLogin, btnSignUp;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivity);
        mAuth = FirebaseAuth.getInstance();
        initUI();

        checkWatcher(edtName);
        checkWatcher(edtEmail);
        checkWatcher(edtPass);
        initListener();

    }

    private void initUI(){
        prgSignUp = findViewById(R.id.prg_SignUp);
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        imgGoogle = findViewById(R.id.google_SignIn);
    }
    private void initListener(){
        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prgSignUp.setVisibility(View.VISIBLE);
            signInWithGoogle();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_left_to_right);
            }
        });
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
    public void signInWithGoogle() {
        Intent signInIntent = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Xử lý kết quả của việc đăng nhập bằng Google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Đăng nhập thành công, lấy thông tin tài khoản Google
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                prgSignUp.setVisibility(View.GONE);
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
            } catch (ApiException e) {
                // Đăng nhập thất bại
            }
        }
    }

    // Xác thực tài khoản Google bằng Firebase Authentication
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // Đăng nhập thất bại
                        }
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