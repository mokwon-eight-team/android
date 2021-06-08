package com.example.loginregister.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loginregister.R;


public class CourierFragment extends Fragment {

    private View view;

    public static CourierFragment newInstance(){
        CourierFragment courierFragment = new CourierFragment();
        return courierFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_courier, container, false);
    }
}