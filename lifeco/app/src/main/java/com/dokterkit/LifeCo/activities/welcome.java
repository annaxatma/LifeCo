package com.dokterkit.LifeCo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dokterkit.lifeco.R;

public class welcome extends AppCompatActivity {

    Button pasienButton, ambulansButton, socsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        pasienButton = findViewById(R.id.pasienButton);
        ambulansButton = findViewById(R.id.ambulansButton);
        socsButton = findViewById(R.id.socsButton);

        ambulansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterDriverIntent = new Intent(welcome.this, MainActivity.class);
                LoginRegisterDriverIntent.putExtra("account", "driver");
                startActivity(LoginRegisterDriverIntent);
            }
        });
        pasienButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterPassengerIntent = new Intent(welcome.this, MainActivity.class);
                LoginRegisterPassengerIntent.putExtra("account", "pasien");
                startActivity(LoginRegisterPassengerIntent);
            }
        });
        socsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterSocsIntent = new Intent(welcome.this, SOCSMainActivity.class);
                startActivity(LoginRegisterSocsIntent);
            }
        });
    }
}