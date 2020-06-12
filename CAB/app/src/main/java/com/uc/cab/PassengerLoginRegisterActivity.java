package com.uc.cab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PassengerLoginRegisterActivity extends AppCompatActivity {
    Button PassengerLoginButton,PassengerRegisterButton;
    TextView PassengerStatusLink,PassengerRegisterLink;
    EditText PassengerEmail, PassengerPass;
    ProgressDialog loadingbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_login_register);
        PassengerRegisterButton = findViewById(R.id.passenger_register_button);
        PassengerLoginButton = findViewById(R.id.passenger_login_button);
        PassengerStatusLink = findViewById(R.id.passenger_status);
        PassengerRegisterLink = findViewById(R.id.passenger_register_link);
        PassengerEmail = findViewById(R.id.passenger_email_login);
        PassengerPass = findViewById(R.id.passenger_password_login);
        loadingbar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        PassengerRegisterButton.setVisibility(View.INVISIBLE);
        PassengerRegisterButton.setEnabled(false);

        PassengerRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassengerLoginButton.setVisibility(View.INVISIBLE);
                PassengerRegisterLink.setVisibility(View.INVISIBLE);
                PassengerRegisterButton.setVisibility(View.VISIBLE);
                PassengerRegisterButton.setEnabled(true);
                PassengerStatusLink.setText("PASSENGER REGISTER");
            }
        });

        PassengerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = PassengerEmail.getText().toString();
                String password = PassengerPass.getText().toString();

                RegisterPassenger(email,password);
            }
        });

        PassengerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = PassengerEmail.getText().toString();
                String password = PassengerPass.getText().toString();

                SignInPassenger(email,password);
            }
        });
    }

    private void SignInPassenger(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(PassengerLoginRegisterActivity.this, "Missing Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(PassengerLoginRegisterActivity.this, "Missing Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar.setTitle("Passenger Signing In");
            loadingbar.setMessage("Please wait a moment");
            loadingbar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PassengerLoginRegisterActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent PassengerIntent = new Intent(PassengerLoginRegisterActivity.this,PassengerMapsActivity.class);
                                startActivity(PassengerIntent);
                                loadingbar.dismiss();
                            }else{
                                Toast.makeText(PassengerLoginRegisterActivity.this, "Login Failed, Try Again Pls", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });
        }
    }

    private void RegisterPassenger(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(PassengerLoginRegisterActivity.this, "Missing Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(PassengerLoginRegisterActivity.this, "Missing Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar.setTitle("Passenger Registration");
            loadingbar.setMessage("Please wait a moment");
            loadingbar.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PassengerLoginRegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent PassengerIntent = new Intent(PassengerLoginRegisterActivity.this,PassengerMapsActivity.class);
                                startActivity(PassengerIntent);
                                loadingbar.dismiss();
                            }else{
                                Toast.makeText(PassengerLoginRegisterActivity.this, "Registration Failed, Try Again Pls", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });
        }
    }
}
