package com.example.plant_library.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_library.Activity.IndexActivity;
import com.example.plant_library.Adapter.GenreAdapter;
import com.example.plant_library.Adapter.PlantCategoryAdapter;
import com.example.plant_library.FragmentHelper;
import com.example.plant_library.Interface.FragmentHandler;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.Object.PlantCategory;
import com.example.plant_library.R;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements RecyclerViewInterface {
    GenreAdapter genreAdapter;
    RecyclerView recyclerView;
    PlantCategoryAdapter plantCategoryAdapter;
    View mView;
    private FragmentHandler fragmentHandler;
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
        genreAdapter = new GenreAdapter(getContext(), R.id.rcv_genre,this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        genreAdapter.setData(getGenre());
        recyclerView.setAdapter(genreAdapter);
    }

    private void setPlantCategoryAdapter(){
        recyclerView = mView.findViewById(R.id.rcv_cate);
        plantCategoryAdapter = new PlantCategoryAdapter(getContext(),R.layout.item_category_search, this, R.id.rcv_cate);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        plantCategoryAdapter.setData(getListPlantCategory());
        recyclerView.setAdapter(plantCategoryAdapter);
    }

    private List<PlantCategory> getGenre() {
        List<PlantCategory> genreList = new ArrayList<>();
        genreList.add(new PlantCategory(R.drawable.img_plant_genre,"foliage"));
        genreList.add(new PlantCategory(R.drawable.img_plant_genre,"flowering"));
        genreList.add(new PlantCategory(R.drawable.img_plant_genre,"article"));
        return genreList;
    }
    private List<PlantCategory> getListPlantCategory() {
        List<PlantCategory> plantCategoryListList = new ArrayList<>();
        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage"));
        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage2"));
        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage3"));
        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage4"));
        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage5"));
        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage6"));
        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage"));
        plantCategoryListList.add(new PlantCategory(R.drawable.img_plant_cate,"Foliage"));
        return plantCategoryListList;
    }

    @Override
    public void onItemClick(int recyclerViewId, int position) {
//        GenreFragment genreFragment = new GenreFragment();
//        Bundle bundle = new Bundle();
//        // Giả sử bạn muốn truyền tên của thể loại từ danh sách thể loại (getListPlants)
//        PlantCategory selectedCategory = getListPlantCategory().get(position);
//        PlantCategory selectedCategory2 = getGenre().get(position);
//
//
//        bundle.putString("category_name", selectedCategory.getCategoryName());
//        bundle.putString("genre_name", selectedCategory2.getCategoryName());
//        genreFragment.setArguments(bundle);
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_index));
//
//        fragmentTransaction.add(R.id.frame_index, genreFragment);
//        fragmentTransaction.addToBackStack(null);
//       fragmentTransaction.commit();

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
        clearBackStack();
       GenreFragment genreFragment = new GenreFragment();
        Bundle bundle = new Bundle();
        // Giả sử bạn muốn truyền tên của thể loại từ danh sách thể loại (getListPlants)
        PlantCategory selectedCategory = getListPlantCategory().get(position);
        bundle.putString("category_name", selectedCategory.getCategoryName());
        genreFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_index));
        fragmentTransaction.add(R.id.frame_index, genreFragment);
        clearBackStack();
        fragmentTransaction.addToBackStack(null);
       fragmentTransaction.commit();
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
        transaction.commit();
    }
}