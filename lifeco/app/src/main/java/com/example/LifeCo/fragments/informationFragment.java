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

import com.example.LifeCo.Adapter.IRVAdapter;
import com.example.LifeCo.activities.informationDetailActivity;
import com.example.LifeCo.model.OnCardClickListener;
import com.example.LifeCo.model.Information;
import com.example.lifeco.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class informationFragment extends Fragment implements OnCardClickListener {

    private View view;
    private RecyclerView information_recyclerView;
    private ArrayList<Information> informationList;
    private IRVAdapter adapter;

    private Query informationReference;

    private FirebaseFirestore fStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_information, container, false);

        fStore = FirebaseFirestore.getInstance();

        initialize();
        loadInformation();

        return view;
    }

    private void loadInformation() {
        informationReference = fStore.collection("Files")
        .orderBy("created", Query.Direction.DESCENDING);

        informationReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                informationList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        String id = doc.getId();
                        Information Information = doc.toObject(Information.class).withId(id);
                        informationList.add(Information);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(int position) {
        Information Information = informationList.get(position);
        String informationId = Information.itemId;

        Intent intent = new Intent(getContext(), informationDetailActivity.class);
        intent.putExtra("informationId", informationId);
        startActivity(intent);
    }

    private void initialize() {
        information_recyclerView = view.findViewById(R.id.information_recyclerView);
        informationList = new ArrayList<Information>();
        adapter = new IRVAdapter(informationList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        information_recyclerView.setLayoutManager(manager);
        information_recyclerView.setAdapter(adapter);
    }

}