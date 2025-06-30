package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class dhome extends AppCompatActivity {
    Button b1,b2,b3,b5;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhome);
        b1=findViewById(R.id.button33);
        b2=findViewById(R.id.button34);
        b3=findViewById(R.id.button35);
        b5=findViewById(R.id.button37);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),viewallocatedwork.class);
                startActivity(ii);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),delviewnotification.class);
                startActivity(ii);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),Test2.class);
                startActivity(ii);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),login.class);
                startActivity(ii);
                Intent ik = new Intent(getApplicationContext(),LocationServiceno.class);
                stopService(ik);
            }
        });



    }
}