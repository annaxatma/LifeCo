package com.example.LifeCo.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class pptInfoActivity extends AppCompatActivity {

    private TextView info_title, info_desc;
    private String pptId;

    private DocumentReference pptReference;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt_info);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        pptReference = fStore.collection("Files").document(pptId);

        pptReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                info_title.setText(documentSnapshot.getString("title"));
                info_desc.setText(documentSnapshot.getString("desc"));
            }
        });

    }

    private void initialize(){
        info_title=findViewById(R.id.info_title);
        info_desc=findViewById(R.id.info_desc);
    }
}