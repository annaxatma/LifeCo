package com.example.LifeCo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lifeco.R;

public class welcome extends AppCompatActivity {

    Button pasienButton, ambulansButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        pasienButton = findViewById(R.id.pasienButton);
        ambulansButton = findViewById(R.id.ambulansButton);

        ambulansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterDriverIntent = new Intent(welcome.this,RegistrationActivity.class);
                startActivity(LoginRegisterDriverIntent);
            }
        });
        pasienButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterPassengerIntnet = new Intent(welcome.this,RegistrationActivity.class);
                startActivity(LoginRegisterPassengerIntnet);
            }
        });
    }
}