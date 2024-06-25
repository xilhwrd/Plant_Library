package com.example.plant_library.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.plant_library.Object.Genre;
import com.example.plant_library.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MeFragment extends Fragment {
    private TextView userEmailTextView, userNameTextView;
    private FirebaseAuth mAuth;
    View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_me, container, false);
        initUI();
        setUserInformation();
        // Lấy email của người dùng và đưa vào TextView

        return mView;
    }
    private void initUI(){
        mAuth = FirebaseAuth.getInstance();
        userEmailTextView = mView.findViewById(R.id.tv_email_user);
        userNameTextView = mView.findViewById(R.id.tv_name_user);

    }
    private void setUserInformation(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            userEmailTextView.setText(""+email);
            String username = currentUser.getDisplayName();
            userNameTextView.setText(""+ username);
        }
    }
    private void loadDataUser(){
        DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference("UserInformation");

    }
}
