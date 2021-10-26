package com.dokterkit.LifeCo.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dokterkit.LifeCo.model.History;
import com.dokterkit.lifeco.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class HistoryFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView rvHistory;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth fAuth;
    String userId;
    CollectionReference histRef;


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        rvHistory = view.findViewById(R.id.rv_history);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        histRef = firebaseFirestore.collection("Users");

        Query query = histRef.document(currentuser).collection("History");

        FirestoreRecyclerOptions<History> options = new FirestoreRecyclerOptions.Builder<History>().setQuery(query, History.class).build();

        adapter = new FirestoreRecyclerAdapter<History, HistoryViewHolder>(options) {
            @NonNull
            @Override
            public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardhistory, parent, false);
                return new HistoryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull HistoryViewHolder holder, int position, @NonNull History model) {
                holder.Aktivitas.setText(model.getAktivitas());
                holder.Tanggal.setText(model.getTanggal());
                holder.Waktu.setText(model.getWaktu());
            }
        };

        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHistory.setAdapter(adapter);

    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView Aktivitas, Tanggal, Waktu;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            Aktivitas = itemView.findViewById(R.id.textAktivitas);
            Tanggal = itemView.findViewById(R.id.textTanggal);
            Waktu = itemView.findViewById(R.id.textJam);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
