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

import java.text.SimpleDateFormat;
import java.util.Date;

public class pptInfoActivity extends AppCompatActivity {

    private Toolbar pptInfo_toolbar;
    private TextView info_title, info_desc, info_created;
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

                Date createdTimestamp = documentSnapshot.getTimestamp("created").toDate();
                SimpleDateFormat sfd = new SimpleDateFormat("MMMM dd, yyyy");
                String createdDate = sfd.format(createdTimestamp);

                info_title.setText(documentSnapshot.getString("title"));
                info_desc.setText(Html.fromHtml(description));
                info_created.setText(createdDate);
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
        info_created = findViewById(R.id.info_created);

        intent = getIntent();
        pptId = intent.getStringExtra("pptId");
    }
}