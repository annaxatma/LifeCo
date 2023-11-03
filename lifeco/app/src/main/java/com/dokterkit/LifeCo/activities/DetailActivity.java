package com.dokterkit.LifeCo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dokterkit.lifeco.R;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT = "extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
