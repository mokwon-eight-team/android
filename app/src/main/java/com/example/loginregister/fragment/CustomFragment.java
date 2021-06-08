package com.example.loginregister.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.loginregister.R;


public class CustomFragment extends Fragment {

    private View view;

    public CustomFragment(){

    }


    public static CustomFragment newInstance(){
        CustomFragment customFragment = new CustomFragment();
        return customFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_custom, container, false);
        String[] custom = {"물량증가로 인한 배송지연", "코로나로 인한 배송지연", "산간지역 배송지연", "무인택배우편함 사용방법"};

        ListView listView = (ListView) view.findViewById(R.id.listview2);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, custom);
        listView.setAdapter(listViewAdapter);
        return view;
    }
}