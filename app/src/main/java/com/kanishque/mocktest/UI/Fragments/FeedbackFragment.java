package com.kanishque.mocktest.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kanishque.mocktest.R;

public class FeedbackFragment extends Fragment {


    Button b;
    EditText e1,e2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback,container,false);
        b= view.findViewById(R.id.button);
        e1=view.findViewById(R.id.editText1);
        e2=view.findViewById(R.id.editText2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"customercare@iniestawebtech.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback from App");
                i.putExtra(Intent.EXTRA_TEXT, "Name : "+e1.getText()+"\nMessage : "+e2.getText());
                try {
                    startActivity(Intent.createChooser(i, "Send feedback..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;



    }
}
