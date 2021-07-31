package com.example.LifeCo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.LifeCo.Adapter.AdapterGroupChatList;
import com.example.LifeCo.activities.AddGroupActivity;
import com.example.LifeCo.model.ModelGroupChatList;
import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SOCSChatFragment extends Fragment {

    Button goToGroupChat;
    private RecyclerView groupsRv;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelGroupChatList> groupChatLists;
    private AdapterGroupChatList adapterGroupChatList;

    public SOCSChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_socs_chat, container, false);

        groupsRv = view.findViewById(R.id.groupsRv);
        firebaseAuth = FirebaseAuth.getInstance();
        loadGroupChatList();
        return view;
    }

    private void loadGroupChatList() {
        groupChatLists = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Groups");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupChatLists.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("Participants").child(firebaseAuth.getUid()).exists()){
                        ModelGroupChatList model = ds.getValue(ModelGroupChatList.class);
                        groupChatLists.add(model);
                    }

                }
                adapterGroupChatList = new AdapterGroupChatList(getActivity(), groupChatLists);
                groupsRv.setAdapter(adapterGroupChatList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchGroupChatList(String query) {
        groupChatLists = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Groups");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupChatLists.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(!ds.child("Participants").child(firebaseAuth.getUid()).exists()){
                        if(ds.child("groupTitle").toString().toLowerCase().contains(query.toLowerCase())){
                            ModelGroupChatList model = ds.getValue(ModelGroupChatList.class);
                            groupChatLists.add(model);
                        }

                    }

                }
                adapterGroupChatList = new AdapterGroupChatList(getActivity(), groupChatLists);
                groupsRv.setAdapter(adapterGroupChatList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull  Menu menu, @NonNull  MenuInflater inflater) {

        inflater.inflate(R.menu.groupchat_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search_group);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!TextUtils.isEmpty(s.trim())){
                    searchGroupChatList(s);
                } else {
                    loadGroupChatList();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!TextUtils.isEmpty(s.trim())){
                    searchGroupChatList(s);
                } else {
                    loadGroupChatList();
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_create_group){
            startActivity(new Intent(getActivity(), AddGroupActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}