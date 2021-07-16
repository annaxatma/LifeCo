package com.example.LifeCo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.lifeco.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class AddGroupActivity extends AppCompatActivity {

//    private static final int
    Toolbar groupChatToolbar;

    //Firebase
    FirebaseAuth firbaseAuth;

    ImageView groupChatIcon;
    TextInputLayout groupNameLayout, groupDescLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }
}