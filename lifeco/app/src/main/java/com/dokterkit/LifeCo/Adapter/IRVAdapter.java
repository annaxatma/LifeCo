package com.dokterkit.LifeCo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dokterkit.LifeCo.model.OnCardClickListener;
import com.dokterkit.LifeCo.model.Information;
import com.dokterkit.lifeco.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IRVAdapter extends RecyclerView.Adapter<IRVAdapter.informationViewHolder> {

    private ArrayList<Information> informationList;
    private OnCardClickListener cardListener;

    private FirebaseFirestore fStore;

    public IRVAdapter(ArrayList<Information> informationList, OnCardClickListener cardListener) {
        this.informationList = informationList;
        this.cardListener = cardListener;
    }

    @NonNull
    @Override
    public IRVAdapter.informationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_information, parent, false);

        fStore = FirebaseFirestore.getInstance();

        return new informationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IRVAdapter.informationViewHolder holder, int position) {
        Information Information = informationList.get(position);

        Date createdTimestamp = Information.getCreated().toDate();
        SimpleDateFormat sfd = new SimpleDateFormat("MMMM dd, yyyy");
        String createdDate = sfd.format(createdTimestamp);

        holder.information_textView_title.setText(Information.getTitle());
        holder.information_textView_created.setText(createdDate);
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public class informationViewHolder extends RecyclerView.ViewHolder {
        TextView information_textView_title, information_textView_created;

        public informationViewHolder(@NonNull View itemView) {
            super(itemView);

            information_textView_title = itemView.findViewById(R.id.information_textView_title);
            information_textView_created = itemView.findViewById(R.id.information_textView_created);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
