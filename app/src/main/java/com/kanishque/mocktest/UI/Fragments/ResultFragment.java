package com.kanishque.mocktest.UI.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.kanishque.mocktest.R;



public class ResultFragment extends Fragment {
    private TextView score_result,mques;
    private Button result_restart;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_result, container, false);
        mques = view.findViewById(R.id.total_ques);
        score_result = view.findViewById(R.id.score_result);
        String score =  getArguments().getString("score");
        String totalques = getArguments().getString("question");
        mques.setText(totalques);
        score_result.setText(score);
        result_restart = view.findViewById(R.id.result_restart);
        result_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new DashboardFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).commit();
            }
        });

        score_result = view.findViewById(R.id.score_result);

        return view;
    }



}

