package com.example.LifeCo.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.LifeCo.Adapter.UserAdapter;
import com.example.LifeCo.activities.MainActivity;
import com.example.LifeCo.activities.SplashScreenActivity;
import com.example.LifeCo.model.Chat;
import com.example.LifeCo.model.Users;
import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<Users> mUsers;
    private DatabaseReference ChatRef, targetRef;
    FirebaseUser fuser;
    DatabaseReference reference;
    private String targetID,partnerID;
    private List<String> usersList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //EDITING DIS
        ChatRef = FirebaseDatabase.getInstance().getReference().child("Conversations");
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        targetID = fuser.getUid();
        DocumentReference documentReference = fStore.collection("Users").document(targetID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.getString("account").equalsIgnoreCase("ambulance")){
                    targetRef = FirebaseDatabase.getInstance().getReference().child("Paired Request").child(targetID).child("Patient");
                    targetRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                partnerID = dataSnapshot.getValue().toString();
                            } else {
                                Log.d("ERROR", "Empty snapshot");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("ERROR", databaseError.toString());
                        }
                    });
                } else if(documentSnapshot.getString("account").equalsIgnoreCase("patient")){
                    targetRef = FirebaseDatabase.getInstance().getReference().child("Paired Request 2").child(targetID).child("Ambulance");
                    targetRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                partnerID = dataSnapshot.getValue().toString();
                            } else {
                                Log.d("ERROR", "Empty snapshot");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("ERROR", databaseError.toString());
                        }
                    });

                }

            }});
        //EDITING STOPS HERE
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("plz work", "why why");
        Log.d("trial user: ", fuser.getUid());
        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if(chat.getSender().equals(fuser.getUid())){
                        String uid = chat.getSender();
                        Log.d("trial sender: ", uid);
                        usersList.add(chat.getReceiver());
                    }
                    if (chat.getReceiver().equals(fuser.getUid())){
                        String uid = chat.getReceiver();
                        Log.d("trial receiver: ", uid);
                        usersList.add(chat.getSender());
                    }
                }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void readChats() {
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Users users = snapshot.getValue(Users.class);

                    //display 1 user from chats
                    for (String id : usersList){
                        if (users.getId().equals(id)){
                            if (mUsers.size() != 0){
                                for (Users users1 : mUsers){
                                    if (!users.getId().equals(users1.getId())){
                                        mUsers.add(users);
                                    }
                                }
                            }else{
                                mUsers.add(users);
                            }
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
