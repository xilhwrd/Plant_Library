package com.example.plant_library.Fragment;

import static androidx.core.content.ContextCompat.getDrawable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.example.plant_library.Activity.IndexActivity;
import com.example.plant_library.Activity.LoginActivity;
import com.example.plant_library.Activity.MainActivity;
import com.example.plant_library.Activity.SignUpActivity;
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
import com.google.firebase.database.snapshot.Index;


public class LoginFragment1 extends Fragment {
    EditText edtEmail, edtPass;
    TextView tvForgot;
    AppCompatButton btnLogIn;
    ImageView imgViewGoogle;
    View mView;
    FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
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
        checkWatcher(edtEmail);
        checkWatcher(edtPass);
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
        imgViewGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                signInWithGoogle();
            }
        });
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
                                    Toast.makeText(getActivity(), "Login Succes!", Toast.LENGTH_SHORT).show();
                                    hideProgressBar();
                                    Intent i = new Intent(getActivity(), IndexActivity.class);
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
    public void signInWithGoogle() {
        Intent signInIntent = GoogleSignIn.getClient(getContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
                hideProgressBar();
                Intent i = new Intent(getActivity(), Index.class);
                startActivity(i);
                getActivity().finishAffinity();
            } catch (ApiException e) {
                hideProgressBar();
            }
        }
    }

    // Xác thực tài khoản Google bằng Firebase Authentication
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
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
            edtEmail.setText(data.trim());
        }
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
                    btnLogIn.setEnabled(false);
                    signUpCheck(btnLogIn);
                }else
                    btnLogIn.setEnabled(true);
                signUpCheck(btnLogIn);
            }
        });
    }
    private boolean signUpCheck(Button btnSign){
        if(btnSign.isEnabled() == true){
            btnLogIn.setBackground(getDrawable(getContext(),R.drawable.bg_button_signup));
        }else  btnLogIn.setBackground(getDrawable(getContext(),R.drawable.bg_button_signup_false));
        return false;
    }
}