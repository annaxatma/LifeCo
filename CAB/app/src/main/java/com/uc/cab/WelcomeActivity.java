package com.uc.cab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    Button WelcomeDriver, WelcomePassenger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        WelcomeDriver = findViewById(R.id.welcome_driver_button);
        WelcomePassenger = findViewById(R.id.welcome_passenger_button);

        WelcomeDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterDriverIntent = new Intent(WelcomeActivity.this,DriverLoginRegisterActivity.class);
                startActivity(LoginRegisterDriverIntent);
            }
        });
        WelcomePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterPassengerIntnet = new Intent(WelcomeActivity.this,PassengerLoginRegisterActivity.class);
                startActivity(LoginRegisterPassengerIntnet);
            }
        });
    }
}
