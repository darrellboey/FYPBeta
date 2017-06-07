package com.example.a15017395.fyptestapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageSwitcher;
import android.widget.ListView;

import com.robertlevonyan.views.customfloatingactionbutton.CustomFloatingActionButton;
import com.robertlevonyan.views.customfloatingactionbutton.OnFabClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CustomFloatingActionButton fab;
    ImageSwitcher is;
    ListView lv;

    ArrayList<String> alNews = new ArrayList<String>();
    ArrayAdapter aa;
    ArrayList<Outlet> outletList = new ArrayList<Outlet>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        loadOutlets();
        fab = (CustomFloatingActionButton) view.findViewById(R.id.fabNO);
        fab.setOnFabClickListener(new OnFabClickListener() {
            @Override
            public void onFabClick(View v) {
                Intent i = new Intent(getActivity(), OutletActivity.class);
                i.putExtra("outlets", outletList);
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

        lv = (ListView) view.findViewById(R.id.lvNewsFeed);

        alNews.add("Firebake");
        alNews.add("Firders");
        alNews.add("Lewin Terrace");
        alNews.add("The black swan");
        alNews.add("SPRMRKT");
        alNews.add("The Pillar");
        alNews.add("Kotobuki");
        alNews.add("Schmear");//no 8
        aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,alNews);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String[] links = getResources().getStringArray(R.array.link);
                Uri uri = Uri.parse(links[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });


        return view;
    }

    private void loadOutlets(){
        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("No network connection!")
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }

}