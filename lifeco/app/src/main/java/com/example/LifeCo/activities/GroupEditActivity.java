package com.example.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lifeco.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class GroupEditActivity extends AppCompatActivity {

    //permission constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;

    //image pick constants
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    //permission arrays
    private String[] cameraPermissions;
    private String[] storagePermissions;

    //picked image uri
    private Uri image_uri = null;

    //Firebase
    FirebaseAuth firebaseAuth;

    //Views
    private Toolbar toolbar;
    ImageView groupChatIcon;
    TextInputEditText groupNameTextLayout, groupDescTextLayout;
    Button updategroupbtn;

    //dialog
    ProgressDialog progressDialog;

    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_edit);

        toolbar = findViewById(R.id.editgroup_toolbar);

        //init views
        groupChatIcon = findViewById(R.id.groupchaticon);
        groupNameTextLayout = findViewById(R.id.groupTitleEdit);
        groupDescTextLayout = findViewById(R.id.groupDescriptionEdit);
        updategroupbtn = findViewById(R.id.updategroupbtn);

        groupId = getIntent().getStringExtra("groupId");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        //init permission arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        loadGroupInfo();

        //group chat icon
        groupChatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialog();
            }
        });

        //create group btn
        updategroupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUpdatingGroup();
            }
        });
    }

    private void startUpdatingGroup() {
        //input data
        String groupTitle = groupNameTextLayout.getText().toString().trim();
        String groupDescription = groupDescTextLayout.getText().toString().trim();

        //validate data
        if (TextUtils.isEmpty(groupTitle)){
            Toast.makeText(this, "Group Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Updating Group Info...");
        progressDialog.show();

        if (image_uri == null){
            //update without icon

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("groupTitle", groupTitle);
            hashMap.put("groupDescription", groupDescription);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
            ref.child(groupId).updateChildren(hashMap)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    Toast.makeText(GroupEditActivity.this, "Group Info Updated", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(GroupEditActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            String timestamp = ""+System.currentTimeMillis();

            String filePathAndName = "Group_Imgs/" + "image" + "_" + timestamp;

            //upload img to firebase storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //image uploaded
                            //get url
                            Task<Uri> p_uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!p_uriTask.isSuccessful());
                            Uri p_downloadUri = p_uriTask.getResult();
                            if (p_uriTask.isSuccessful()){

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("groupTitle", groupTitle);
                                hashMap.put("groupDescription", groupDescription);
                                hashMap.put("groupIcon", "" + p_downloadUri);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
                                ref.child(groupId).updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toast.makeText(GroupEditActivity.this, "Group Info Updated", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(GroupEditActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //failed to upload
                            progressDialog.dismiss();
                            Toast.makeText(GroupEditActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void loadGroupInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.orderByChild("groupId").equalTo(groupId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String groupId = "" + ds.child("groupId").getValue();
                    String groupTitle = "" + ds.child("groupTitle").getValue();
                    String groupDescription = "" + ds.child("groupDescription").getValue();
                    String groupIcon = "" + ds.child("groupIcon").getValue();
                    String createdBy = "" + ds.child("createdBy").getValue();
                    String timestamp = "" + ds.child("timestamp").getValue();

                    //convert time stamp to dd/mm/yyyy hh:mm am/pm
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal. setTimeInMillis(Long.parseLong(timestamp));
                    String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();

                    //set group info
                    setSupportActionBar(toolbar);
                    toolbar.setTitle(groupTitle);

                    try{
                        Picasso.get().load(groupIcon).placeholder(R.drawable.ic_baseline_group_24).into(groupChatIcon);
                    }
                    catch (Exception e){
                        groupChatIcon.setImageResource(R.drawable.ic_baseline_group_24);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showImagePickDialog() {
        //options to pick image from
        String[] options = {"Camera", "Gallery"};
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image:").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0 ){
                    //camera clicked
                    if(!checkCameraPermissions()){
                        requestCameraPermissions();
                    } else {
                        pickFromCamera();;
                    }
                } else {
                    //gallery clicked
                    if(!checkStoragePermissions()){
                        requestStoragePermissions();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        }).show();
    }

    private void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera(){
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Group Image Icon Title");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Group Image Icon Description");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);

    }

    private boolean checkStoragePermissions(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermissions(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermissions(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {
            toolbar.setSubtitle(user.getEmail());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //handle permission result
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                Log.d("MASOK GAK NI ", "MASOK");
                if(grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    Log.d("ACCEPT NOT ACCEPT 1 = ", Boolean.toString(cameraAccepted));
                    Log.d("ACCEPT NOT ACCEPT 2 = ", Boolean.toString(storageAccepted));
                    if(cameraAccepted && storageAccepted){
                        //permission allowed
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Camera and storage permissions are required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length > 0) {
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        //permission allowed
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Storage permissions required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //handle image pick result
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //was picked from gallery
                image_uri = data.getData();
                //set to imgview
                groupChatIcon.setImageURI(image_uri);
            } else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //was picked from camera
                //set to imgview
                groupChatIcon.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}