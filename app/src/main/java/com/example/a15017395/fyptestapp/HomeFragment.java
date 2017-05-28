package com.example.a15017395.fyptestapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;

import com.robertlevonyan.views.customfloatingactionbutton.CustomFloatingActionButton;
import com.robertlevonyan.views.customfloatingactionbutton.OnFabClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    CustomFloatingActionButton fab;
    ImageSwitcher is;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fab = (CustomFloatingActionButton) view.findViewById(R.id.fabNO);

        fab.setOnFabClickListener(new OnFabClickListener() {
            @Override
            public void onFabClick(View v) {
                Intent i = new Intent(getActivity(), OutletActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_up_animation,R.anim.no_change);
            }
        });

        is = (ImageSwitcher) view.findViewById(R.id.imageSwitcher) ;

        is.postDelayed(new Runnable() {
            int i = 0;
            public void run() {
                is.setImageResource(
                        i++ % 2 == 0 ?
                                R.drawable.promo1 :
                                R.drawable.promo2);
                is.postDelayed(this, 2000);
            }
        }, 2000);






        return view;
    }

}