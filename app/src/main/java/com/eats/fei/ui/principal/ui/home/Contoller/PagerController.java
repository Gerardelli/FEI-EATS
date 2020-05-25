package com.eats.fei.ui.principal.ui.home.Contoller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eats.fei.ui.principal.ui.home.Fragments.Comida;
import com.eats.fei.ui.principal.ui.home.Fragments.Dulces;
import com.eats.fei.ui.principal.ui.home.Fragments.Frituras;
import com.eats.fei.ui.principal.ui.home.Fragments.Todos;

public class PagerController extends FragmentPagerAdapter {
    int numOfTabs;

    public PagerController(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Todos();
            case 1:
                return new Dulces();
            case 2:
                return new Comida();
            case 3:
                return new Frituras();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
