package com.example.shareit.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shareit.R;

public class FoundFragment extends Fragment {

    public FoundFragment() {
        // Required empty public constructor
    }

    public static FoundFragment newInstance() {
        FoundFragment fragment = new FoundFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_found, container, false);

        return view;
    }
}