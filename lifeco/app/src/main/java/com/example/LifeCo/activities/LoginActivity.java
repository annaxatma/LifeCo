package com.example.LifeCo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeco.R;
import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnDaftarAkun;
    String email, password;
    TextInputLayout emailLogin, passwordLogin;
    TextView loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnDaftarAkun = findViewById(R.id.btnDaftarAkun);
        emailLogin = findViewById(R.id.inputEmailLogin);
        passwordLogin = findViewById(R.id.inputPasswordLogin);
        loginStatus = findViewById(R.id.textLoginStatus);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailLogin.getEditText().getText().toString().trim();
                password = passwordLogin.getEditText().getText().toString().trim();

                LoginChecking(email, password);

            }
        });
        btnDaftarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LoginChecking(String emailChecking, String passwordChecking){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("email", emailChecking);
        params.put("password", passwordChecking);
        String url = "http://iamtinara.com/lifeco/loginuser.php";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    String login_status = responseObject.getString("login_status");
                    if (login_status.equalsIgnoreCase("logged_in")) {
                        Log.d("loginstat", login_status);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.d("loginstat", login_status);
                        loginStatus.setText("Wrong email or password! Try again!!!");

                    }
                } catch (Exception e) {
                    Log.d("ExceptionHistory", "onSuccess: " + e.getMessage());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureHistory", "onFailure: " + error.getMessage());
            }
        });
    }
}
