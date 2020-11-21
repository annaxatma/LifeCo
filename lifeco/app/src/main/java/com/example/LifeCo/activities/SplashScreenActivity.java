package com.example.LifeCo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SplashScreenActivity extends AppCompatActivity {
    private int SLEEP_TIMER = 1;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);

        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();

    }
    private class LogoLauncher extends Thread{
        public void run(){
            try{
                sleep(1000 * SLEEP_TIMER);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
//            Intent intent = new Intent(SplashScreenActivity.this, welcome.class);
//            startActivity(intent);
            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            userId = fAuth.getCurrentUser().getUid();
            if (fAuth.getCurrentUser() != null) {
                // User is logged in
                DocumentReference documentReference = fStore.collection("Users").document(userId);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot.getString("tipeakun").equalsIgnoreCase("driver")){
                            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                            String account = "ambulans";
                            intent.putExtra("account",account);
                            startActivity(intent);
                            finish();
                        } else if(documentSnapshot.getString("tipeakun").equalsIgnoreCase("pasien")){
                            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                            String account = "pasien";
                            intent.putExtra("account",account);
                            startActivity(intent);
                            finish();
                        }

                    }
                });

            }
        }
    }
}
