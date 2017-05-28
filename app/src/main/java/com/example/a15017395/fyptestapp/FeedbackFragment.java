package com.example.a15017395.fyptestapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.robertlevonyan.views.customfloatingactionbutton.CustomFloatingActionButton;
import com.robertlevonyan.views.customfloatingactionbutton.OnFabClickListener;



/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {
    public FeedbackFragment() {

    }

    EditText etName, etMessage;
    CustomFloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);


        etName = (EditText) view.findViewById(R.id.etName);
        etMessage = (EditText) view.findViewById(R.id.etMessage);
        fab = (CustomFloatingActionButton) view.findViewById(R.id.fabSend);

        fab.setOnFabClickListener(new OnFabClickListener() {
            @Override
            public void onFabClick(View v) {
                if (etName.getText().toString().trim().length() > 0 && etMessage.getText().toString().trim().length() > 0){
                    // The action you want this intent to do;
                    // ACTION_SEND is used to indicate sending text
                    Intent email = new Intent(Intent.ACTION_SEND);
                    // Put essentials like email address, subject & body text
                    email.putExtra(Intent.EXTRA_EMAIL,
                            new String[]{"juhanjufri@gmail.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT,
                            "Cafeteria feedback by " + etName.getText());
                    email.putExtra(Intent.EXTRA_TEXT,
                            etMessage.getText() + "\n\n Sent from Cafeteria App by " + etName.getText());
                    // This MIME type indicates email
                    email.setType("message/rfc822");
                    // createChooser shows user a list of app that can handle
                    // this MIME type, which is, email
                    startActivity(Intent.createChooser(email,
                            "Choose an Email client :"));
                } else {
                    Snackbar.make(fab, "Please fill in all fields", Snackbar.LENGTH_LONG).show();
                }

            }});


        return view;


    }

}