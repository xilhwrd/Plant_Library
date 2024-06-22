package com.example.plant_library.Fragment;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Activity.GenreActivity;
import com.example.plant_library.Activity.IndexActivity;
import com.example.plant_library.Adapter.GenreAdapter;
import com.example.plant_library.Adapter.PlantCategoryAdapter;
import com.example.plant_library.FragmentHelper;
import com.example.plant_library.Interface.FragmentHandler;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.Genre;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.R;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchFragment extends Fragment implements RecyclerViewInterface {
    private static final String TAG = "SearchFragment";
    GenreAdapter genreAdapter;
    RecyclerView recyclerView;
    PlantCategoryAdapter plantCategoryAdapter;
    View mView;
    private FragmentHandler fragmentHandler;
    private List<PlantCategory> plantCategoryList;
    private List<Genre> genreList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        fragmentHandler = new FragmentHelper(getActivity().getSupportFragmentManager(), R.id.frame_search);
        setGenreAdapter();
        setPlantCategoryAdapter();
        return mView;
    }

    private void setGenreAdapter() {
        recyclerView = mView.findViewById(R.id.rcv_genre);
        genreList = new ArrayList<>();
        genreAdapter = new GenreAdapter(genreList,getContext(), R.id.rcv_genre,this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(genreAdapter);
        getListGenre();
    }

    private void getListGenre() {
        DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference("Genre");
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                genreList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "DataSnapshot: " + snapshot.toString());
                    Genre genre = snapshot.getValue(Genre.class);
                    if (genre != null) {
                        Log.d(TAG, "Parsed category: " + genre.getGenreName() + ", " + genre.getGenreImage());
                        genreList.add(genre);
                    }
                }
                genreAdapter.notifyDataSetChanged();
                Log.d(TAG, "Updated plantCategoryList: " + genreList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

    private void setPlantCategoryAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_cate);
        plantCategoryList = new ArrayList<>();
        plantCategoryAdapter = new PlantCategoryAdapter(plantCategoryList,getContext(),R.layout.item_category_search, this, R.id.rcv_cate);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);


        recyclerView.setAdapter(plantCategoryAdapter);
        getListCateGory();
    }
    private void getListCateGory() {
        DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference("Category");
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantCategoryList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "DataSnapshot: " + snapshot.toString());
                    PlantCategory category = snapshot.getValue(PlantCategory.class);
                    if (category != null) {
                        Log.d(TAG, "Parsed category: " + category.getCategoryName() + ", " + category.getCategoryImage());
                        plantCategoryList.add(category);
                    }
                }
                plantCategoryAdapter.notifyDataSetChanged();
                Log.d(TAG, "Updated plantCategoryList: " + plantCategoryList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
    }

    private List<Genre> getGenre() {
        List<Genre> genreList = new ArrayList<>();
//        genreList.add(new PlantCategory(R.drawable.img_plant_genre,"foliage"));
//        genreList.add(new PlantCategory(R.drawable.img_plant_genre,"flowering"));
//        genreList.add(new PlantCategory(R.drawable.img_plant_genre,"article"));
        return genreList;
    }


    @Override
    public void onItemClick(int recyclerViewId, int position) {
        switch (recyclerViewId) {
            case R.id.rcv_genre:
                handleGenreClick(position);
                break;
            case R.id.rcv_cate:
                handleCategoryClick(position);
                break;
            default:
                Log.e("SearchFragment", "Unknown RecyclerView ID");
                break;
        }
    }

    private void handleCategoryClick(int position) {

       GenreFragment genreFragment = new GenreFragment();
        Bundle bundle = new Bundle();
        PlantCategory selectedCategory = plantCategoryList.get(position);
        bundle.putString("category_name", selectedCategory.getCategoryName());
        bundle.putString("category_img", selectedCategory.getCategoryImage());
        bundle.putInt("category_id", selectedCategory.getCategoryID());
        genreFragment.setArguments(bundle);

        Intent intent = new Intent(getActivity(), GenreActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_index);
//
//        // Kiểm tra nếu fragment hiện tại là SearchFragment
//
//        fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_index));
//        clearBackStack();
//        fragmentTransaction.add(R.id.frame_index, genreFragment);
//        clearBackStack();
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

    private void handleGenreClick(int position) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tab_index", position + 1);
        homeFragment.setArguments(bundle);
        clearBackStack();
        replaceFragment(homeFragment, "HomeFragment");

        if (getActivity() instanceof IndexActivity) {
            IndexActivity indexActivity = (IndexActivity) getActivity();
            indexActivity.navigateToHome(position +1 );
        }
        clearBackStack();
    }
    private void clearBackStack() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            while (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
            }
        }
    }
    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_index, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}