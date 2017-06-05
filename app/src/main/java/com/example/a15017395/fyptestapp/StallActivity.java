package com.example.a15017395.fyptestapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class StallActivity extends AppCompatActivity {

    public String outletId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.background)));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_stall);


    }

    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        outletId = intent.getStringExtra("com.example.MAIN_MESSAGE");
        HttpRequest request= new HttpRequest("https://night-vibes.000webhostapp.com/getStalls.php?outlet_id=" + outletId);
        request.setMethod("GET");
        request.execute();
        try{
            String jsonString = request.getResponse();
            JSONObject jsonObj = new JSONObject(jsonString);
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>" + jsonObj.getString("stall_name") + "</font>"));
            TextView firstNameET = (TextView) findViewById(R.id.review);
            firstNameET.setText(jsonObj.getString("stall_review"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

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
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.no_change,R.anim.slide_down_animation);
        } else if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_animation,R.anim.slide_left_animation);
        }
        return super.onOptionsItemSelected(item);
    }

}
