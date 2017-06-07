package com.example.a15017395.fyptestapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.provider.Contacts.SettingsColumns.KEY;

public class OutletActivity extends AppCompatActivity {
    ArrayList<Outlet> outletList = new ArrayList<Outlet>();
    Intent intent;
    ArrayAdapter aa;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outletList.clear();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.background)));

        }
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Nearby Outlets </font>"));
        setContentView(R.layout.activity_outlet);

        lv = (ListView) findViewById(R.id.lvOutlet);

        outletList = (ArrayList<Outlet>) getIntent().getSerializableExtra("outlets");

        aa = new OutletArrayAdapter(this, R.layout.row_outlet, outletList);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                Outlet outlet = (Outlet) parent.getItemAtPosition(arg2);
                intent = new Intent(getApplicationContext(), StallActivity.class);
                intent.putExtra("com.example.MAIN_MESSAGE", Integer.toString(outlet.getId()));
                startActivity(intent);
            }
        });
    }







    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            finish();
            overridePendingTransition(R.anim.no_change,R.anim.slide_down_animation);
        }
        return super.onOptionsItemSelected(item);
    }
}
