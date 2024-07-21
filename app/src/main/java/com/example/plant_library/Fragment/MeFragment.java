package com.example.plant_library.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.plant_library.Activity.AccountSettings;
import com.example.plant_library.Activity.FavoriteActivity;
import com.example.plant_library.Activity.HistoryActivity;
import com.example.plant_library.Activity.MainActivity;
import com.example.plant_library.Activity.SignUpActivity;
import com.example.plant_library.Object.Genre;
import com.example.plant_library.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeFragment extends Fragment {
    private TextView userEmailTextView, userNameTextView, accountSettingTextview, logOut, favorite, history;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    CircleImageView imgUser;
    private FirebaseUser currentUser;
    View mView;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_me, container, false);
        initUI();
        setUserInformation();
        setOnClick();


        imgUser.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AccountSettings.class);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
        return mView;
    }
    private void initUI(){

        mStorageRef = FirebaseStorage.getInstance().getReference("Profile");
        mAuth = FirebaseAuth.getInstance();
        userEmailTextView = mView.findViewById(R.id.tv_email_user);
        userNameTextView = mView.findViewById(R.id.tv_name_user);
        logOut = mView.findViewById(R.id.tv_logout);
        accountSettingTextview = mView.findViewById(R.id.tv_account_setting);
        imgUser = mView.findViewById(R.id.cir_img_user);
        currentUser = mAuth.getCurrentUser();
        favorite = mView.findViewById(R.id.tv_plant_favorite);
        history = mView.findViewById(R.id.tv_plant_history);
    }
    private void setOnClick(){
        accountSettingTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AccountSettings.class);
                startActivity(intent);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDialog() {
        Dialog dialog =new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        AppCompatButton btnLogOut = dialog.findViewById(R.id.btn_logout);
        TextView btnCancel = dialog.findViewById(R.id.tv_cancel);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), SignUpActivity.class);
                startActivity(i);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void setUserInformation(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            userEmailTextView.setText(""+email);
            String username = currentUser.getDisplayName();
            if (username == null || username.isEmpty()){
                loadDataUser();
            }else {
                userNameTextView.setText(username);
            }
        }
    }
    private void loadDataUser(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("UserInformation");

        if (currentUser != null) {
            String userId = currentUser.getUid();
            userEmailTextView.setText(currentUser.getEmail());

            mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.child("UserName").getValue(String.class);
                        userNameTextView.setText(userName);
                        String userImage = dataSnapshot.child("ProfileImage").getValue(String.class);
                        Picasso.get().load(userImage).into(imgUser);
                        if (userImage != null && !userImage.isEmpty()) {
                            Picasso.get().load(userImage).into(imgUser);
                        } else {
                            Log.e("MeFragment", "UserImage is null or empty");
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    userNameTextView.setText("Error loading username");
                }
            });
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            String newImageUrl = data.getStringExtra("imageUrl");
            if (newImageUrl != null && !newImageUrl.isEmpty()) {
                Picasso.get().load(newImageUrl).into(imgUser);
            }
            // Reload user data to ensure all details are up to date
            loadDataUser();
        }
    }
}
