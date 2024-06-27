package com.example.plant_library.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.plant_library.FragmentHelper;
import com.example.plant_library.Interface.FragmentHandler;
import com.example.plant_library.Interface.OnGenreSelectedListener;
import com.example.plant_library.Interface.RecyclerViewInterface;
import com.example.plant_library.R;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment implements RecyclerViewInterface, OnGenreSelectedListener {
    private View mView;
    private TextView tab1, tab2, tab3, tab4;
    private TabLayout mTabLayout;
    private Button btn;
    private FragmentHandler fragmentHandler;
    private ProgressBar progressBar;
    private HomeFragmentAll homeFragmentAll = new HomeFragmentAll();
    private HomeFragmentArticle homeFragmentArticle = new HomeFragmentArticle();
    private HomeFragmentFlowering homeFragmentFlowering = new HomeFragmentFlowering();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        initUI();

        return mView;
    }

    private void initUI() {
        fragmentHandler = new FragmentHelper(getActivity().getSupportFragmentManager(), R.id.frame_index);
        progressBar = mView.findViewById(R.id.prg_home);

        tab1 = mView.findViewById(R.id.tab_all);
        tab2 = mView.findViewById(R.id.tab_article);
        tab3 = mView.findViewById(R.id.tab_flowering);
        tab4 = mView.findViewById(R.id.tab_foliage);


        Fragment selectedFragment = new HomeFragmentAll();
        loadFragment(selectedFragment);
        tab1.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
        tab1.setTextColor(getResources().getColor(R.color.white));
        setBG(tab2, tab3, tab4);

        tab1.setOnClickListener(tabClickListener);
        tab2.setOnClickListener(tabClickListener);
        tab3.setOnClickListener(tabClickListener);
        tab4.setOnClickListener(tabClickListener);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int tabIndex = bundle.getInt("tab_index");
            selectTab(tabIndex);
        }

    }
    private View.OnClickListener tabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment selectedFragment = homeFragmentAll;
            switch (view.getId()) {
                case R.id.tab_all:
                    selectedFragment = homeFragmentAll;
                    tab1.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                    tab1.setTextColor(getResources().getColor(R.color.white));
                    setBG(tab2, tab3, tab4);
                    break;
                case R.id.tab_article:
                    selectedFragment = homeFragmentArticle;
                    tab2.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                    tab2.setTextColor(getResources().getColor(R.color.white));
                    setBG(tab1, tab3, tab4);
                    break;
                case R.id.tab_flowering:
                    selectedFragment = homeFragmentFlowering;
                    tab3.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                    tab3.setTextColor(getResources().getColor(R.color.white));
                    setBG(tab1, tab2, tab4);
                    break;
                case R.id.tab_foliage:
                    selectedFragment = homeFragmentFlowering;
                    tab4.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                    tab4.setTextColor(getResources().getColor(R.color.white));
                    setBG(tab1, tab2, tab3);
                    break;
            }
            loadFragment(selectedFragment);
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Hide all fragments
        if (homeFragmentAll.isAdded()) transaction.hide(homeFragmentAll);
        if (homeFragmentArticle.isAdded()) transaction.hide(homeFragmentArticle);
        if (homeFragmentFlowering.isAdded()) transaction.hide(homeFragmentFlowering);
//        if (homeFragmentFoliage.isAdded()) transaction.hide(homeFragmentFoliage);

        // Show the selected fragment
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.frame_home, fragment);
        }

        transaction.commit();
    }

    private void setBG(TextView tab1, TextView tab2, TextView tab3){
        tab1.setBackground(getResources().getDrawable(R.drawable.bg_tab));
        tab1.setTextColor(getResources().getColor(R.color.black));
        tab2.setBackground(getResources().getDrawable(R.drawable.bg_tab));
        tab2.setTextColor(getResources().getColor(R.color.black));
        tab3.setBackground(getResources().getDrawable(R.drawable.bg_tab));
        tab3.setTextColor(getResources().getColor(R.color.black));
    }
    public void selectTab(int tabIndex) {
        Fragment selectedFragment;
        switch (tabIndex) {
            case 1:
                selectedFragment = homeFragmentArticle;
                tab2.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                setBG(tab1, tab3, tab4);
                break;
            case 2:
                selectedFragment = homeFragmentFlowering;
                tab3.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                tab3.setTextColor(getResources().getColor(R.color.white));
                setBG(tab1, tab2, tab4);
                break;
            case 3:
                selectedFragment = homeFragmentFlowering;
                tab3.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                tab3.setTextColor(getResources().getColor(R.color.white));
                setBG(tab1, tab2, tab4);
                break;
            default:
                selectedFragment = new HomeFragmentArticle();
                tab1.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                setBG(tab2, tab3, tab4);
                break;
        }
        if (selectedFragment != null) {
            loadFragment(selectedFragment);
        }
    }

    @Override
    public void onItemClick(int recyclerViewId, int position) {
    }

    @Override
    public void onGenreSelected(int position) {
        selectTab(position + 1);
    }
}