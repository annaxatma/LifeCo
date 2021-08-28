package com.example.LifeCo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.LifeCo.Adapter.PRVAdapter;
import com.example.LifeCo.activities.pptInfoActivity;
import com.example.LifeCo.model.OnCardClickListener;
import com.example.LifeCo.model.ppt;
import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class pptFragment extends Fragment implements OnCardClickListener {

    private View view;
    private RecyclerView ppt_recyclerView;
    private ArrayList<ppt> pptList;
    private PRVAdapter adapter;

    private Query pptReference;

    private FirebaseFirestore fStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ppt, container, false);

        fStore = FirebaseFirestore.getInstance();

        initialize();
        loadPpt();

        return view;
    }

    private void loadPpt() {
        pptReference = fStore.collection("Files");

        pptReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                pptList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        String id = doc.getId();
                        ppt ppt = doc.toObject(ppt.class).withId(id);
                        pptList.add(ppt);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(int position) {
        ppt ppt = pptList.get(position);
        String pptId = ppt.pptId;

        Intent intent = new Intent(getContext(), pptInfoActivity.class);
        intent.putExtra("pptId", pptId);
        startActivity(intent);
    }

    private void initialize() {
        ppt_recyclerView = view.findViewById(R.id.ppt_recyclerView);
        pptList = new ArrayList<ppt>();
        adapter = new PRVAdapter(pptList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        ppt_recyclerView.setLayoutManager(manager);
        ppt_recyclerView.setAdapter(adapter);
    }

}