package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

public class delviewnotifi extends AppCompatActivity {
    ListView l1;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delviewnotifi);
        l1=findViewById(R.id.listview11);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
}