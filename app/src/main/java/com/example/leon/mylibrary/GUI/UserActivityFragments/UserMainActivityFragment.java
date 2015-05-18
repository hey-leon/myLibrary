package com.example.leon.mylibrary.GUI.UserActivityFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leon.mylibrary.R;

/**
 * Created by Leon on 5/05/2015.
 */
public class UserMainActivityFragment extends Fragment {


    //view
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_activity, container, false);


        return view;
    }

}
