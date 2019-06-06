package com.kanishque.mocktest.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kanishque.mocktest.R;

public class InstructionFragment extends Fragment {
    private Button btn_instruction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_instruction, container, false);

        btn_instruction = view.findViewById(R.id.btn_instructions);
        btn_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new DashboardFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).commit();

            }
        });

        return view;
    }
}