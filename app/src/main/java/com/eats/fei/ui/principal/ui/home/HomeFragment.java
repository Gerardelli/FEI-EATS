package com.eats.fei.ui.principal.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eats.fei.R;
import com.eats.fei.ui.principal.ui.home.Contoller.PagerController;
import com.eats.fei.ui.principal.ui.home.Fragments.Todos;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TabLayout mTablayout;
    private TabItem mTodos, mDulces, mComida, mFritura;
    private ViewPager mPager;
    PagerController pagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mTablayout = (TabLayout) root.findViewById(R.id.tablayoutR);
        mPager = (ViewPager) root.findViewById(R.id.viewpager);

        mTodos = (TabItem) root.findViewById(R.id.todos);
        mDulces = (TabItem) root.findViewById(R.id.dulces);
        mComida = (TabItem) root.findViewById(R.id.comida);
        mFritura = (TabItem) root.findViewById(R.id.fritura);

        pagerAdapter = new PagerController(getFragmentManager(), mTablayout.getTabCount());
        mPager.setAdapter(pagerAdapter);

        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0){
                    pagerAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Todos", Toast.LENGTH_LONG).show();
                }
                if(tab.getPosition()==1){
                    //pagerAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Dulces", Toast.LENGTH_LONG).show();
                }
                if(tab.getPosition()==2){
                    //pagerAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Comida", Toast.LENGTH_LONG).show();
                }
                if(tab.getPosition()==3){
                    Toast.makeText(getContext(), "Frituras", Toast.LENGTH_LONG).show();
                    //pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));


        return root;
    }
}
