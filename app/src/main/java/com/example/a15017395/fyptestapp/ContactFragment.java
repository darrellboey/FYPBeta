package com.example.a15017395.fyptestapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }



    ArrayAdapter aa;
    ArrayList<Contact> contactList = new ArrayList<Contact>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Keyboard Stuff
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        ListView lv = (ListView) view.findViewById(R.id.lvContacts);


        //Connect to database

        String url = "https://night-vibes.000webhostapp.com/resources.php";
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
        aa = new ContactArrayAdapter(getActivity(), R.layout.row, contactList);
        lv.setAdapter(aa);

        return view;
    }
    

}
