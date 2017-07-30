package com.servodroid.servoclock;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimeFragment extends Fragment {

    private HoloCircularProgressBar mHoloCircularProgressBar;
    private ObjectAnimator mProgressBarAnimator;

    public TimeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_holo_circular, container, false);
        //mHoloCircularProgressBar = (HoloCircularProgressBar) view.findViewById(R.id.holoCircularProgressBar);
        //mHoloCircularProgressBar.setProgress(90);

        return inflater.inflate(R.layout.fragment_time, container, false);
    }
}
