package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class userhome extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        b1=findViewById(R.id.button26);
        b2=findViewById(R.id.button27);
        b3=findViewById(R.id.button28);
        b4=findViewById(R.id.button22);
        b5=findViewById(R.id.button30);
        b6=findViewById(R.id.button31);
        b7=findViewById(R.id.button32);
        b8=findViewById(R.id.button25);
        b9=findViewById(R.id.button40);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewnearshop.class);
                startActivity(ik);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewbuyproduct.class);
                startActivity(ik);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewmybooking.class);
                startActivity(ik);

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewmycart.class);
                startActivity(ik);

            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), feedback.class);
                startActivity(ik);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), complaint.class);
                startActivity(ik);

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewnotification.class);
                startActivity(ik);
            }

        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), chatfarmer.class);
                startActivity(ik);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), login.class);
                startActivity(ik);

            }
        });


    }
}