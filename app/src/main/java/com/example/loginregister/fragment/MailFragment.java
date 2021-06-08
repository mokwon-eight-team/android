package com.example.loginregister.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loginregister.R;

public class MailFragment extends Fragment {

    private View view;

    public static MailFragment newInstance(){
        MailFragment mailFragment = new MailFragment();
        return mailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mail, container, false);
    }
}