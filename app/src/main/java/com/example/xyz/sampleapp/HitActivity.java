package com.example.xyz.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Util.NetworkUtil;

public class HitActivity extends AppCompatActivity implements NetworkUtil.AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hit);

        new NetworkUtil.APIutil(this).execute();
    }

    @Override
    public void processFinish(String output) {

    }
}
