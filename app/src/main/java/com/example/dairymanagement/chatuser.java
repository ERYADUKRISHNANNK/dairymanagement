package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chatuser extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String url,uuid;
    ArrayList<String> name,userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatuser);
        l1=findViewById(R.id.listview41);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/view_c_user";
        RequestQueue queue = Volley.newRequestQueue(chatuser.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
                    Toast.makeText(chatuser.this, response+"", Toast.LENGTH_SHORT).show();


                    JSONArray ar=new JSONArray(response);
                    name= new ArrayList<>();
                    userid= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)

                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("name"));
                        userid.add(jo.getString("lid"));
                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<>(chatuser.this,android.R.layout.simple_list_item_1,name);
                    l1.setAdapter(ad);
//
                    //                   l1.setAdapter(new custom_reply(chatfarmer.this,name,fid));
                    l1.setOnItemClickListener(chatuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(chatuser.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid",sh.getString("lid",""));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        uuid=userid.get(position);
        SharedPreferences.Editor edp = sh.edit();
        edp.putString("uid", uuid);
        edp.commit();
        Intent ii=new Intent(getApplicationContext(),Test.class);
        startActivity(ii);
    }
}




