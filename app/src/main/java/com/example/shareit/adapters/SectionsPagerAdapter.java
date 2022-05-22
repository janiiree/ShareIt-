package com.example.shareit.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shareit.fragments.FoundFragment;
import com.example.shareit.fragments.LostFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return LostFragment.newInstance();
            case 1: return FoundFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;//tenemos dos fragments
    }

    @Override
    public String getPageTitle(int position) {
        switch(position) {
            case 0:
                return "Perdidos";
            case 1:
                return "Encontrados";
        }
        return "";
    }

}
