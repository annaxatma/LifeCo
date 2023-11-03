package com.dokterkit.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dokterkit.LifeCo.Adapter.UserAdapter;
import com.dokterkit.LifeCo.model.Chat;
import com.dokterkit.LifeCo.model.Users;
import com.dokterkit.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<Users> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<String> usersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

//            View view = inflater.inflate(R.layout.activity_group_chat, container, false);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GroupChatActivity.this));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if(chat.getSender().equals(fuser.getUid())){
                        usersList.add(chat.getReceiver());
                    }
                    if (chat.getReceiver().equals(fuser.getUid())){
                        usersList.add(chat.getSender());
                    }
                }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

                userAdapter = new UserAdapter(GroupChatActivity.this, mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}