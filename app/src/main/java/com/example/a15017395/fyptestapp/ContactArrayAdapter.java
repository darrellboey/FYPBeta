package com.example.a15017395.fyptestapp;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.a15017395.fyptestapp.R.layout.row;

public class ContactArrayAdapter extends ArrayAdapter<Contact> {
    private ArrayList<Contact> objects;
    private Context context;
    int layoutResourceId;
    ArrayList<Contact> contactList = null;


    public ContactArrayAdapter(Context context, int layoutResourceId, ArrayList<Contact> contactList) {
        super(context, layoutResourceId, contactList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {   View row = convertView;
        ContactHolder holder = null;


        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent,false);

            holder = new ContactHolder();
            holder.tvRole = (TextView) row.findViewById(R.id.role);
            holder.tvName = (TextView) row.findViewById(R.id.name);
            holder.ibMail = (ImageButton) row.findViewById(R.id.ibMail);
            holder.ibInsta = (ImageButton) row.findViewById(R.id.ibInsta);
            holder.ibFacebook = (ImageButton) row.findViewById(R.id.ibFacebook);

            row.setTag(holder);
        } else {
            holder = (ContactHolder)row.getTag();
        }

        final Contact person = contactList.get(position);
        holder.tvRole.setText(person.getRole());
        holder.tvName.setText(person.getName());



        holder.ibMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                // Put essentials like email address, subject & body text
                email.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{person.getEmail()});
                // This MIME type indicates email
                email.setType("message/rfc822");
                // createChooser shows user a list of app that can handle
                // this MIME type, which is, email
                context.startActivity(Intent.createChooser(email,
                        "Choose an Email client :"));
            }
        });


        if(person.getInsta().equals("")){
            holder.ibInsta.setVisibility(View.GONE);
        } else {
            holder.ibInsta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://instagram.com/_u/" + person.getInsta()));
                        intent.setPackage("com.instagram.android");
                        context.startActivity(intent);
                    }
                    catch (android.content.ActivityNotFoundException anfe)
                    {
                        context.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.instagram.com/" + person.getInsta())));
                    }
                }
            });
        }

        holder.ibFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(person.getFb());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        return row;
    }

    static class ContactHolder
    {
        private TextView tvRole, tvName;
        private ImageButton ibMail, ibInsta, ibFacebook;
    }
}


