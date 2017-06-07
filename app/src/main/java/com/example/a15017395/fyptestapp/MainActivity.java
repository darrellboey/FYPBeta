package com.example.a15017395.fyptestapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_RETAINED_FRAGMENT = "ContactFragment";
    ArrayList<Outlet> outletList = new ArrayList<Outlet>();

    private ContactFragment mRetainedFragment;
//    Button btnLogin, btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //listen for navigation events
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
// select the correct nav menu item

        //Drawer stuff
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        getSupportActionBar().setTitle("Home");
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.linearlayout_for_fragment,
                homeFragment,
                homeFragment.getTag())
                .commit();

        loadOutlets();
        Intent i = new Intent(this, OutletActivity.class);
        i.putExtra("outlets", outletList);
        startActivityForResult(i, 1);

//        btnLogin = (Button) findViewById(R.id.btnLogin);
//        btnRegister = (Button) findViewById(R.id.btnRegister);
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivity(i);
//            }
//        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            item.setChecked(true);
            getSupportActionBar().setTitle("Home");
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.linearlayout_for_fragment,
                    homeFragment,
                    homeFragment.getTag())
                    .commit();


        } else if (id == R.id.nav_about) {
            getSupportActionBar().setTitle("About");
            item.setChecked(true);
            AboutFragment aboutFragment = new AboutFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.linearlayout_for_fragment,
                    aboutFragment,
                    aboutFragment.getTag())
                    .commit();


        } else if (id == R.id.nav_contact) {
            MyProgressFragment progressFragment = new MyProgressFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.linearlayout_for_fragment,
                    progressFragment,
                    progressFragment.getTag())
                    .commit();


            item.setChecked(true);
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    getSupportActionBar().setTitle("Contact");
                    ContactFragment homeFragment = new ContactFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.linearlayout_for_fragment,
                            homeFragment,
                            homeFragment.getTag())
                            .commitAllowingStateLoss();

                }
            };

            handler.postDelayed(runnable, 350);




        } else if (id == R.id.nav_feedback) {
            item.setChecked(true);
            getSupportActionBar().setTitle("Send Feedback");
            FeedbackFragment feedbackFragment = new FeedbackFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.linearlayout_for_fragment,
                    feedbackFragment,
                    feedbackFragment.getTag())
                    .commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void loadOutlets(){
        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //Connect to database
            String url = "https://night-vibes.000webhostapp.com/getOutlets.php";
            HttpRequest request = new HttpRequest(url);
            request.setMethod("GET");
            request.execute();


            try {
                String jsonString = request.getResponse();
                Log.i("message", jsonString);
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

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // AlertBox
            showAlert();
        }
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No network connection!")
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loadOutlets();
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }

}