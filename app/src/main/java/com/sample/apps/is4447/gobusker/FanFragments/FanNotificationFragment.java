package com.sample.apps.is4447.gobusker.FanFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.apps.is4447.gobusker.R;


public class FanNotificationFragment extends Fragment {
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fan_notification, container, false);
    }
}