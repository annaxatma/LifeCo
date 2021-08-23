package com.example.LifeCo.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LifeCo.model.Socs;
import com.example.lifeco.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterParticipantAdd extends RecyclerView.Adapter<AdapterParticipantAdd.HolderParticipantAdd>{

    private Context context;
    private ArrayList<Socs> userList;
    private String groupId, myGroupRole; //creator/admin/participant

    public AdapterParticipantAdd(Context context, ArrayList<Socs> userList, String groupId, String myGroupRole) {
        this.context = context;
        this.userList = userList;
        this.groupId = groupId;
        this.myGroupRole = myGroupRole;
    }

    @NonNull
    @Override
    public HolderParticipantAdd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_participant_add, parent, false);

        return new HolderParticipantAdd(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterParticipantAdd.HolderParticipantAdd holder, int position) {
        //get data
        Socs modelUser = userList.get(position);
        String name = modelUser.getName();
        String email = modelUser.getEmail();
        String image = modelUser.getImage();
        String uid = modelUser.getUid();

        //set data
        holder.nameTv.setText(name);
        holder.emailTv.setText(email);
        try{
            Picasso.get().load(image).placeholder(R.drawable.ic_account_circle_black_24dp).into(holder.avatarIv);
        }catch(Exception e){
            holder.avatarIv.setImageResource(R.drawable.ic_account_circle_black_24dp);
        }

        checkIfAlreadyExists(modelUser, holder);

        //handle click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
                ref.child(groupId).child("Participants").child(uid)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    //user exists/not participant
                                    String hisPreviousRole = "" + dataSnapshot.child("role").getValue();

                                    //option to display in dialog
                                    String[] options;

//                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                                    builder.setTitle("Choose Option");
//                                    if (myGroupRole.equals("creator")){
//                                        if (hisPreviousRole.equals("admin")){
//                                            //im creator, he's admin
//                                            options = new String[]{"Remove Admin", "Remove User"};
//                                            builder.setItems(options, new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int which) {
//                                                    //handle item clicks
//                                                    if (which == 0){
//                                                        //remove admin clicked
//                                                        removeAdmin(modelUser);
//                                                    }else{
//                                                        //remove user clicked
//                                                        removeParticipant(modelUser);
//                                                    }
//                                                }
//                                            }). show();
//
//                                        }
//                                        else if (hisPreviousRole.equals("participants")){
//                                            //im creator, he's participants
//                                            options = new String[]{"Make Admin", "Remove User"};
//                                            builder.setItems(options, new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int which) {
//                                                    //handle item clicks
//                                                    if (which == 0){
//                                                        //"Make Admin clicked
//                                                        makeAdmin(modelUser);
//                                                    }else{
//                                                        //remove user clicked
//                                                        removeParticipant(modelUser);
//                                                    }
//                                                }
//                                            }). show();
//                                        }
//                                    }
//                                    else if (myGroupRole.equals("admin")){
//                                        if (hisPreviousRole.equals("creator")){
//                                            //im admin, he is creator
//                                            Toast.makeText(context, "Creator of Group...", Toast.LENGTH_SHORT).show();
//                                        }
//                                        else if (hisPreviousRole.equals("admin")){
//                                            //im admin, he is admin too
//                                            options = new String[]{"Remove Admin", "Remove User"};
//                                            builder.setItems(options, new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int which) {
//                                                    //handle item clicks
//                                                    if (which == 0){
//                                                        //remove admin clicked
//                                                        removeAdmin(modelUser);
//                                                    }else{
//                                                        //remove user clicked
//                                                        removeParticipant(modelUser);
//                                                    }
//                                                }
//                                            }). show();
//                                        }
//                                        else if (hisPreviousRole.equals("participants")){
//                                            //im admin, he is participants
//                                            options = new String[]{"Make Admin", "Remove User"};
//                                            builder.setItems(options, new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int which) {
//                                                    //handle item clicks
//                                                    if (which == 0){
//                                                        //"Make Admin clicked
//                                                        makeAdmin(modelUser);
//                                                    }else{
//                                                        //remove user clicked
//                                                        removeParticipant(modelUser);
//                                                    }
//                                                }
//                                            }). show();
//                                        }
//                                    }

                                    androidx.appcompat.app.AlertDialog.Builder participantsDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(view.getRootView().getContext());
                                    participantsDialogBuilder.setTitle("Choose Option");

                                    participantsDialogBuilder.setCancelable(true);

                                    Log.d("check groupRole", myGroupRole);
                                    if (myGroupRole.equalsIgnoreCase("creator")){
                                        Log.d("check groupRole", "creator checked");
                                        Log.d("check previousRolebfr", hisPreviousRole);
                                        if (hisPreviousRole.equalsIgnoreCase("admin")){
                                            Log.d("check previousRole", "admin");
                                            participantsDialogBuilder.setItems(new CharSequence[]
                                                            {"Remove Admin", "Remove User"},
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            switch (which) {
                                                                case 0:
                                                                    removeAdmin(modelUser);
                                                                    break;
                                                                case 1:
                                                                    removeParticipant(modelUser);
                                                                    break;

                                                            }
                                                        }
                                                    });
                                        }
                                        else if (hisPreviousRole.equalsIgnoreCase("participant")){
                                            Log.d("check previousRole", "participant");
                                            participantsDialogBuilder.setItems(new CharSequence[]
                                                            {"Make Admin", "Remove User"},
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            switch (which) {
                                                                case 0:
                                                                    makeAdmin(modelUser);
                                                                    break;
                                                                case 1:
                                                                    removeParticipant(modelUser);
                                                                    break;

                                                            }
                                                        }
                                                    });
                                        }

                                    } else if (myGroupRole.equalsIgnoreCase("admin")){
                                        Log.d("check groupRole", "admin");
                                        if (hisPreviousRole.equalsIgnoreCase("creator")){
                                            Log.d("check previousRole", "creator");
//                                            //im admin, he is creator
                                            Toast.makeText(context, "Creator of Group...", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (hisPreviousRole.equalsIgnoreCase("admin")){
                                            Log.d("check previousRole", "admin");
                                            participantsDialogBuilder.setItems(new CharSequence[]
                                                            {"Remove Admin", "Remove User"},
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            switch (which) {
                                                                case 0:
                                                                    removeAdmin(modelUser);
                                                                    break;
                                                                case 1:
                                                                    removeParticipant(modelUser);
                                                                    break;

                                                            }
                                                        }
                                                    });
                                        }
                                        else if (hisPreviousRole.equalsIgnoreCase("participant")){
                                            Log.d("check previousRole", "participant");
                                            participantsDialogBuilder.setItems(new CharSequence[]
                                                            {"Make Admin", "Remove User"},
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            switch (which) {
                                                                case 0:
                                                                    makeAdmin(modelUser);
                                                                    break;
                                                                case 1:
                                                                    removeParticipant(modelUser);
                                                                    break;

                                                            }
                                                        }
                                                    });
                                        }
                                    }

                                    androidx.appcompat.app.AlertDialog participantAlert = participantsDialogBuilder.create();
                                    participantAlert.show();
                                }
                                else{
                                    //user doesn't exists/not participant: add
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                                    builder.setTitle("Add Participant")
//                                            .setMessage("Add this user in this group?")
//                                            .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int i) {
//                                                    //add user
//                                                    addParticipant(modelUser);
//                                                }
//                                            })
//                                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int i) {
//                                                    dialogInterface.dismiss();
//                                                }
//                                            }).show();

                                    androidx.appcompat.app.AlertDialog.Builder addParticipantDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(view.getRootView().getContext());
                                    addParticipantDialogBuilder.setTitle("Add Participant");
                                    addParticipantDialogBuilder.setMessage("Add this user in this group?");
                                    addParticipantDialogBuilder.setCancelable(true);

                                    addParticipantDialogBuilder.setPositiveButton(
                                            "ADD",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    if (myGroupRole.equals("creator")){
                                                        addParticipant(modelUser);
                                                    }
                                                    else {
                                                        dialog.cancel();
                                                    }
                                                }
                                            });

                                    addParticipantDialogBuilder.setNegativeButton(
                                            "CANCEL",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog alertAddDialog = addParticipantDialogBuilder.create();
                                    alertAddDialog.show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        });
    }

    private void addParticipant(Socs modelUser) {
        //setup user data - add user in group
        String timestamp = "" + System.currentTimeMillis();
        HashMap<String, String>hashMap = new HashMap<>();
        hashMap.put("uid", modelUser.getUid());
        hashMap.put("role", "participant");
        hashMap.put("timestamp", "" + timestamp);

        //add that user in group > groupId > Participants
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.child(groupId).child("Participants").child(modelUser.getUid()).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //added successfully
                        Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void makeAdmin(Socs modelUser) {
        //setup data - change role
        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("role", "admin"); //Roles: participants/admin/creator
        //update role in db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.child(groupId).child("Participants").child(modelUser.getUid()).updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //made admin
                        Toast.makeText(context, "The user is now an admin", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //dailed making admin
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void removeParticipant(Socs modelUser) {
        //remove participants from group
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.child(groupId).child("Participants").child(modelUser.getUid()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //removed successfully

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed to remove

                    }
                });
    }

    private void removeAdmin(Socs modelUser) {
        //setup data: remove admin - just change role
        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("role", "participant"); //Roles: participant/admin/creator
        //update role in db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.child(groupId).child("Participants").child(modelUser.getUid()).updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //made admin
                        Toast.makeText(context, "The user is no longer an admin", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //dailed making admin
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkIfAlreadyExists(Socs modelUser, HolderParticipantAdd holder) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.child(groupId).child("Participants").child(modelUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            //already exists
                            String hisRole = "" + dataSnapshot.child("role").getValue();
                            holder.statusTv.setText(hisRole);
                        }else{
                            //doesn't exists
                            holder.statusTv.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class HolderParticipantAdd extends RecyclerView.ViewHolder{

        private ImageView avatarIv;
        private TextView nameTv, emailTv, statusTv;

        public HolderParticipantAdd(@NonNull View itemView) {
            super(itemView);

            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            emailTv = itemView.findViewById(R.id.emailTv);
            statusTv = itemView.findViewById(R.id.statusTv);

        }
    }
}
