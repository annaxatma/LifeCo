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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class informationDetailActivity extends AppCompatActivity {

    private Toolbar information_toolbar;
    private TextView information_title, information_desc, information_created;
    private Intent intent;

    private DocumentReference informationReference;

    private FirebaseFirestore fStore;

    private String informationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);

        fStore = FirebaseFirestore.getInstance();

        initialize();

        informationReference = fStore.collection("Files").document(informationId);

        informationReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String description = documentSnapshot.getString("desc");

                Date createdTimestamp = documentSnapshot.getTimestamp("created").toDate();
                SimpleDateFormat sfd = new SimpleDateFormat("MMMM dd, yyyy");
                String createdDate = sfd.format(createdTimestamp);

                information_title.setText(documentSnapshot.getString("title"));
                information_desc.setText(Html.fromHtml(description));
                information_created.setText(createdDate);
            }
        });

        setListener();
    }

    private void setListener() {
        information_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialize() {
        information_toolbar = findViewById(R.id.information_toolbar);
        information_title = findViewById(R.id.information_title);
        information_desc = findViewById(R.id.information_desc);
        information_created = findViewById(R.id.information_created);

        intent = getIntent();
        informationId = intent.getStringExtra("informationId");
    }
}