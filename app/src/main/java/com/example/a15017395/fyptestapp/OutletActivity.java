package com.example.a15017395.fyptestapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OutletActivity extends AppCompatActivity {
    ArrayList<Outlet> outletList = new ArrayList<Outlet>();
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.background)));

        }
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Nearby Outlets </font>"));
        setContentView(R.layout.activity_outlet);

        ListView lv = (ListView) findViewById(R.id.lvOutlet);


        //Connect to database
        String url = "https://night-vibes.000webhostapp.com/getOutlets.php";
        HttpRequest request = new HttpRequest(url);
        request.setMethod("GET");
        request.execute();

        try {
            //TODO 4
            String jsonString = request.getResponse();
            Log.i("message",jsonString);
            System.out.println(">>" + jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Outlet outlet = new Outlet();
                outlet.setId(jsonObj.getString("outlet_id"));
                outlet.setName(jsonObj.getString("outlet_name"));
                outlet.setLocation(jsonObj.getString("outlet_location"));
                outlet.setPostalCode(jsonObj.getString("postalCode"));
                outletList.add(outlet);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        aa = new OutletArrayAdapter(this, R.layout.row_outlet, outletList);
        lv.setAdapter(aa);
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
