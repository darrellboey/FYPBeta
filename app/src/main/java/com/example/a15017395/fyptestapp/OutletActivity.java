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

import java.util.ArrayList;

public class OutletActivity extends AppCompatActivity {
    ArrayList<Outlet> outletList = new ArrayList<Outlet>();
    Intent intent;
    ArrayAdapter aa;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.background)));

        }
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Nearby Outlets </font>"));
        setContentView(R.layout.activity_outlet);

        lv = (ListView) findViewById(R.id.lvOutlet);


    }

    public void onResume(){
        super.onResume();
        outletList.clear();
        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //Connect to database
            String url = "https://night-vibes.000webhostapp.com/getOutlets.php";
            HttpRequest request = new HttpRequest(url);
            request.setMethod("GET");
            request.execute();


            try {
                String jsonString = request.getResponse();
                Log.i("message",jsonString);
                System.out.println(">>" + jsonString);
                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Outlet outlet = new Outlet();
                    outlet.setId(jsonObj.getInt("outlet_id"));
                    outlet.setName(jsonObj.getString("outlet_name"));
                    outlet.setLocation(jsonObj.getString("outlet_location"));
                    outlet.setPostalCode(jsonObj.getString("postalCode"));
                    outletList.add(outlet);
                }

            }catch(Exception e) {
                e.printStackTrace();
            }
            aa = new OutletArrayAdapter(this, R.layout.row_outlet, outletList);

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    lv.setAdapter(aa);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View arg1, int arg2, long arg3) {

                            Outlet outlet = (Outlet)parent.getItemAtPosition(arg2);

                            intent = new Intent(getApplicationContext(), StallActivity.class);
                            intent.putExtra("com.example.MAIN_MESSAGE", Integer.toString(outlet.getId()));
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_left_animation,R.anim.slide_right_animation);
                        }
                    });

                }
            };

            handler.postDelayed(runnable, 250);


        } else {
            // AlertBox
            showAlert();
        }
    }

    private void showAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No network connection!")
                .setPositiveButton("Exit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        OutletActivity.this.finish();
                    }
                })
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onResume();
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
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
