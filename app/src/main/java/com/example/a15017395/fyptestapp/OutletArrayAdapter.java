package com.example.a15017395.fyptestapp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class OutletArrayAdapter extends ArrayAdapter<Outlet> {
    private ArrayList<Outlet> objects;
    private Context context;
    private int layoutResourceId;
    private ArrayList<Outlet> outletList = null;
    public String role_type;


    public OutletArrayAdapter(Context context, int layoutResourceId, ArrayList<Outlet> outletList) {
        super(context, layoutResourceId, outletList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.outletList = outletList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        OutletArrayAdapter.OutletHolder holder = null;

        if (role_type == "user"){
            if(row == null){
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent,false);

                holder = new OutletArrayAdapter.OutletHolder();
                holder.tvName = (TextView) row.findViewById(R.id.name);

                holder.tvLocation = (TextView) row.findViewById(R.id.location);

                row.setTag(holder);
            } else {
                holder = (OutletArrayAdapter.OutletHolder)row.getTag();
            }

            final Outlet outlet = outletList.get(position);
            //remember to use holder
            holder.tvName.setText(outlet.getName());
            holder.tvLocation.setText(outlet.getLocation());


        } else if (role_type == "admin"){
            
        }




        return row;

    }

    static class OutletHolder
    {
        private TextView tvName, tvLocation;
        //Put your variables here
        private Button dropdown;
    }
}


