package com.example.LifeCo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LifeCo.model.OnCardClickListener;
import com.example.LifeCo.model.ppt;
import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PRVAdapter extends RecyclerView.Adapter<PRVAdapter.pptViewHolder> {

    private ArrayList<ppt> pptList;
    private OnCardClickListener cardListener;

    private FirebaseFirestore fStore;

    public PRVAdapter(ArrayList<ppt> pptList, OnCardClickListener cardListener) {
        this.pptList = pptList;
        this.cardListener = cardListener;
    }

    @NonNull
    @Override
    public PRVAdapter.pptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_ppt, parent, false);

        fStore = FirebaseFirestore.getInstance();

        return new pptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PRVAdapter.pptViewHolder holder, int position) {
        ppt ppt = pptList.get(position);
        holder.ppt_textView_title.setText(ppt.getTitle());
    }

    @Override
    public int getItemCount() {
        return pptList.size();
    }

    public class pptViewHolder extends RecyclerView.ViewHolder {
        TextView ppt_textView_title;

        public pptViewHolder(@NonNull View itemView) {
            super(itemView);

            ppt_textView_title = itemView.findViewById(R.id.ppt_textView_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
