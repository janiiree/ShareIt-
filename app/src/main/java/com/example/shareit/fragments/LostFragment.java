package com.example.shareit.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shareit.R;

public class LostFragment extends Fragment {

    public LostFragment() {
        // Required empty public constructor
    }

    public static LostFragment newInstance() {
        LostFragment fragment = new LostFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lost, container, false);

        return view;
    }
}