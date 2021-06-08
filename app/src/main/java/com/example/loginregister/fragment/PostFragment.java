package com.example.loginregister.fragment;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loginregister.R;

public class PostFragment extends Fragment {
    private View view;

    public static PostFragment newInstance(){
        PostFragment postFragment = new PostFragment();
        return postFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }
}