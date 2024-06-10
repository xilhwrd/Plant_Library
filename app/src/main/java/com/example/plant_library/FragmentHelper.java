package com.example.plant_library;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.plant_library.Interface.FragmentHandler;

public class FragmentHelper implements FragmentHandler {
    private FragmentManager fragmentManager;
    private int containerId;

    public FragmentHelper(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    @Override
    public void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Hide all fragments
        for (Fragment frag : fragmentManager.getFragments()) {
            fragmentTransaction.hide(frag);
        }

        // Show the selected fragment
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(containerId, fragment);
        }
        fragmentTransaction.commit();
    }
}