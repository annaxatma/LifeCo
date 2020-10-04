package com.example.LifeCo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifeco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginDriverActivity extends AppCompatActivity {

    Button btnLogin, btnDaftarAkun;
    String email, password;
    TextInputLayout emailLogin, passwordLogin;
    TextView loginStatus;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logindriver);

        btnLogin = findViewById(R.id.btnLogin);
        btnDaftarAkun = findViewById(R.id.btnDaftarAkun);
        emailLogin = findViewById(R.id.inputEmailLogin);
        passwordLogin = findViewById(R.id.inputPasswordLogin);
        loginStatus = findViewById(R.id.textLoginStatus);
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            // User is logged in
            Intent intent = new Intent(LoginDriverActivity.this, MainActivity.class);
            String account = "ambulans";
            intent.putExtra("account",account);
            startActivity(intent);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailLogin.getEditText().getText().toString().trim();
                password = passwordLogin.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailLogin.setError("Silahkan isi email Anda.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwordLogin.setError("Silahkan isi password Anda.");
                    return;
                }
                if(password.length() <= 6){
                    passwordLogin.setError("Password harus melebihi 6 karakter.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginDriverActivity.this, "User Logged In.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginDriverActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginDriverActivity.this, "Tidak dapat log in akun.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        btnDaftarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginDriverActivity.this, RegistrationDriverActivity.class);

                startActivity(intent);
            }
        });
    }

//    private void LoginChecking(String emailChecking, String passwordChecking){
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("email", emailChecking);
//        params.put("password", passwordChecking);
//        String url = "http://iamtinara.com/lifeco/loginuser.php";
//        client.post(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                try {
//                    String result = new String(responseBody);
//                    JSONObject responseObject = new JSONObject(result);
//                    String login_status = responseObject.getString("login_status");
//                    if (login_status.equalsIgnoreCase("logged_in")) {
//                        Log.d("loginstat", login_status);
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Log.d("loginstat", login_status);
//                        loginStatus.setText("Wrong email or password! Try again!!!");
//
//                    }
//                } catch (Exception e) {
//                    Log.d("ExceptionHistory", "onSuccess: " + e.getMessage());
//
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.d("onFailureHistory", "onFailure: " + error.getMessage());
//            }
//        });
//    }
}
