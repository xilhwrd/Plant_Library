package com.example.plant_library.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.plant_library.R;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {
    View mView;
    TextView tab1, tab2, tab3, tab4;
    private TabLayout mTabLayout;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        initUI();
        return mView;
    }

    private void initUI() {
        tab1 = mView.findViewById(R.id.tab_all);
        tab2 = mView.findViewById(R.id.tab_article);
        tab3 = mView.findViewById(R.id.tab_flowering);
        tab4 = mView.findViewById(R.id.tab_foliage);

        Fragment selectedFragment = new HomeFragmentAll();
        loadFragment(selectedFragment);
        tab1.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
        setBG(tab2, tab3, tab4);
        tab1.setOnClickListener(tabClickListener);
        tab2.setOnClickListener(tabClickListener);
        tab3.setOnClickListener(tabClickListener);
        tab4.setOnClickListener(tabClickListener);
//        mTabLayout = mView.findViewById(R.id.tab_layout);
//        mTabLayout.setBackgroundColor(getResources().getColor(R.color.bg));
//        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
//        mTabLayout.addTab(mTabLayout.newTab().setText("All"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("Article"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("Flowering"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("Foliage"));
//        loadFragment(new HomeFragmentAll());
//
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(@NonNull TabLayout.Tab tab) {
//                Fragment selectedFragment = null;
//                switch (tab.getPosition()) {
//                    case 0:
//                        selectedFragment = new HomeFragmentAll();
//                        break;
//                    case 1:
//                        selectedFragment = new HomeFragmentAll();
//                        break;
//                    case 2:
//                        selectedFragment = new HomeFragmentAll();
//                        break;
//                    case 3:
//                        selectedFragment = new HomeFragmentAll();
//                        break;
//                }
//                if (selectedFragment != null) {
//                    loadFragment(selectedFragment);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(@NonNull TabLayout.Tab tab) {
//                // Do nothing
//            }
//
//            @Override
//            public void onTabReselected(@NonNull TabLayout.Tab tab) {
//                // Do nothing
//            }
//        });
    }
    private View.OnClickListener tabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment selectedFragment = new HomeFragmentAll();
            switch (view.getId()) {
                case R.id.tab_all:
                    selectedFragment = new HomeFragmentAll();
                    tab1.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                    setBG(tab2, tab3, tab4);
                    break;
                case R.id.tab_article:
                    selectedFragment = new HomeFragmentAll();
                    tab2.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                    setBG(tab1, tab3, tab4);
                    break;
                case R.id.tab_flowering:
                    selectedFragment = new HomeFragmentAll();
                    tab3.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                    setBG(tab1, tab2, tab4);
                    break;
                case R.id.tab_foliage:
                    selectedFragment = new HomeFragmentAll();
                    tab4.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                    setBG(tab1, tab2, tab3);
                    break;
            }
            if (selectedFragment != null) {
                loadFragment(selectedFragment);

            }
        }
    };
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_home, fragment);
        transaction.commit();
    }
    private void setBG(TextView tab1, TextView tab2, TextView tab3){
        tab1.setBackground(getResources().getDrawable(R.drawable.bg_tab));
        tab2.setBackground(getResources().getDrawable(R.drawable.bg_tab));
        tab3.setBackground(getResources().getDrawable(R.drawable.bg_tab));
    }
    public void selectTab(int tabIndex) {
        Fragment selectedFragment;
        switch (tabIndex) {
            case 1:
                selectedFragment = new HomeFragmentAll();
                tab2.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                setBG(tab1, tab3, tab4);
                break;
            default:
                selectedFragment = new HomeFragmentAll();
                tab1.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                setBG(tab2, tab3, tab4);
                break;
        }
        if (selectedFragment != null) {
            loadFragment(selectedFragment);
        }
    }
}