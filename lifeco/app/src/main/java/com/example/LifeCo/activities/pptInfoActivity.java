package com.example.LifeCo.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
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

    private Toolbar pptInfo_toolbar;
    private TextView info_title, info_desc;
    private Intent intent;

    private DocumentReference pptReference;

    private FirebaseFirestore fStore;

    private String pptId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt_info);

        fStore = FirebaseFirestore.getInstance();

        initialize();

        pptReference = fStore.collection("Files").document(pptId);

        pptReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String description = documentSnapshot.getString("desc");

                info_title.setText(documentSnapshot.getString("title"));
                info_desc.setText(Html.fromHtml(description));
            }
        });

        setListener();
    }

    private void setListener() {
        pptInfo_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialize() {
        pptInfo_toolbar = findViewById(R.id.pptInfo_toolbar);
        info_title = findViewById(R.id.info_title);
        info_desc = findViewById(R.id.info_desc);

        intent = getIntent();
        pptId = intent.getStringExtra("pptId");
    }
}