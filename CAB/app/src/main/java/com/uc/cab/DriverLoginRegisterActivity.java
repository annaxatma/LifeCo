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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginRegisterActivity extends AppCompatActivity {
    Button DriverLoginButton,DriverRegisterButton;
    TextView DriverStatusLink,DriverRegisterLink;
    EditText DriverEmail, DriverPass;
    ProgressDialog loadingbar;
    FirebaseAuth mAuth;
    private DatabaseReference DriverDatabaseRef;
    private String OnlineDriverID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);

        mAuth = FirebaseAuth.getInstance();

        DriverRegisterButton = findViewById(R.id.driver_register_button);
        DriverLoginButton = findViewById(R.id.driver_login_button);
        DriverStatusLink = findViewById(R.id.driver_status);
        DriverRegisterLink = findViewById(R.id.driver_register_link);
        DriverEmail = findViewById(R.id.driver_email_login);
        DriverPass = findViewById(R.id.driver_password_login);
        loadingbar = new ProgressDialog(this);
        DriverRegisterButton.setVisibility(View.INVISIBLE);
        DriverRegisterButton.setEnabled(false);

        DriverRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverLoginButton.setVisibility(View.INVISIBLE);
                DriverRegisterLink.setVisibility(View.INVISIBLE);
                DriverRegisterButton.setVisibility(View.VISIBLE);
                DriverRegisterButton.setEnabled(true);
                DriverStatusLink.setText("DRIVER REGISTER");
            }
        });
        DriverRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = DriverEmail.getText().toString();
                String password = DriverPass.getText().toString();

                RegisterDriver(email,password);
            }
        });
        DriverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = DriverEmail.getText().toString();
                String password = DriverPass.getText().toString();

                SignInDriver(email,password);
            }
        });
    }

    private void SignInDriver(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(DriverLoginRegisterActivity.this, "Missing Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(DriverLoginRegisterActivity.this, "Missing Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar.setTitle("Driver Signing In");
            loadingbar.setMessage("Please wait a moment");
            loadingbar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent driverIntent = new Intent(DriverLoginRegisterActivity.this,DriversMapActivity.class);
                                startActivity(driverIntent);
                                Toast.makeText(DriverLoginRegisterActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }else{
                                Toast.makeText(DriverLoginRegisterActivity.this, "Login Failed, Try Again Pls", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
            });
        }
    }


    private void RegisterDriver(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(DriverLoginRegisterActivity.this, "Missing Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(DriverLoginRegisterActivity.this, "Missing Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar.setTitle("Driver Registration");
            loadingbar.setMessage("Please wait a moment");
            loadingbar.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                OnlineDriverID = mAuth.getCurrentUser().getUid();
                                DriverDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(OnlineDriverID);
                                DriverDatabaseRef.setValue(true);

                                Intent driverIntent = new Intent(DriverLoginRegisterActivity.this,DriversMapActivity.class);
                                startActivity(driverIntent);
                                Toast.makeText(DriverLoginRegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }else{
                                Toast.makeText(DriverLoginRegisterActivity.this, "Registration Failed, Try Again Pls", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });
        }
    }
}
