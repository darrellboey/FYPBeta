package com.example.a15017395.fyptestapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    ArrayAdapter aa;
    ArrayList<Contact> contactList = new ArrayList<Contact>();
    ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }





    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

        //Keyboard Stuff
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        lv = (ListView) view.findViewById(R.id.lvContacts);

        return view;
    }

    public void onResume() {
        super.onResume();
        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Connect to database
            String url = "https://night-vibes.000webhostapp.com/getContacts.php";
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
                    Contact person = new Contact();
                    person.setRole(jsonObj.getString("role"));
                    person.setName(jsonObj.getString("name"));
                    person.setEmail(jsonObj.getString("email"));
                    person.setInsta(jsonObj.getString("insta"));
                    person.setFb(jsonObj.getString("fb"));
                    contactList.add(person);
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            aa = new ContactArrayAdapter(getActivity(), R.layout.row_contact, contactList);
            lv.setAdapter(aa);
        } else {
            showAlert();
        }
    }

    private void showAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("No network connection!")
                .setPositiveButton("Exit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                       getActivity().finish();
                    }
                })
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        onResume();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }
}
