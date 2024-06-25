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
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtName, edtEmail, edtPass;
    private ProgressBar prgSignUp;
    private ImageView imgGoogle;
    private AppCompatButton btnLogin, btnSignUp;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    private DatabaseReference mDatabase;
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
        mDatabase = FirebaseDatabase.getInstance().getReference("UserInformation");
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

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();
                String username = edtName.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Please enter information", Toast.LENGTH_SHORT).show();
                    edtName.requestFocus();
                    imm.showSoftInput(edtName, InputMethodManager.SHOW_IMPLICIT);
                }
                if (!isValidEmail(email)) {
                    edtEmail.setError("Email invalid");
                    edtEmail.requestFocus();
                    btnSignUp.setEnabled(false);
                    signUpCheck(btnSignUp);
                }
                mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        prgSignUp.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                            if (isNewUser) {
                                prgSignUp.setVisibility(View.VISIBLE);
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    prgSignUp.setVisibility(View.GONE);
                                                    saveUserToDatabase(user, username);
                                                }
                                            }
                                        });
                            } else {
                                // Email đã tồn tại
                                Toast.makeText(SignUpActivity.this, "Email đã được đăng ký.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void saveUserToDatabase(FirebaseUser user, String username) {
        if (user != null) {
            String userId = user.getUid();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("UserID", userId);
            userInfo.put("UserName", username);
            userInfo.put("ProfileImage", "https://firebasestorage.googleapis.com/v0/b/plant-box-b4958.appspot.com/o/Profile%2FImage%20Profile%20Default.png?alt=media&token=600a4541-85a3-477b-bae9-8062291c5871");

            mDatabase.child(userId).setValue(userInfo)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                finishAffinity();
                                Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, SignUpFinishActivity.class));
                                prgSignUp.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Failed to save user information", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public void signInWithGoogle() {
        Intent signInIntent = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // xử lý kết quả của việc đăng nhập bằng Google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // đăng nhập thành công, lấy thông tin tài khoản Google
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                prgSignUp.setVisibility(View.GONE);
                Intent i = new Intent(SignUpActivity.this, SignUpFinishActivity.class);
                startActivity(i);
                finish();
            } catch (ApiException e) {
                Toast.makeText(this, "Some thing wrong, check your connection.", Toast.LENGTH_SHORT).show();
                prgSignUp.setVisibility(View.GONE);
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
                            Toast.makeText(SignUpActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            prgSignUp.setVisibility(View.GONE);
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
    public boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}