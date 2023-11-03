package com.dokterkit.LifeCo.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dokterkit.LifeCo.activities.LoginActivity;
import com.dokterkit.lifeco.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class AccountFragment extends Fragment {

    TextView nama, email, jeniskelamin, goldarah, alamat, nohp, noktp, nobpjs, noasuransi, tanggallahir, penyakitsendiri, penyakitkeluarga, keluhanutama, obat, alergiobat, alergimakanan, tekanandarah, guladarah;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FloatingActionButton btnEditAkun;
    Toolbar toolbar;
    Button btnLogOut;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbar = getActivity().findViewById(R.id.toolbar_main);
        toolbar.setTitle("Account");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nama = view.findViewById(R.id.txtNama);
        email = view.findViewById(R.id.txtEmail);
        jeniskelamin = view.findViewById(R.id.txtJenisKelamin);
        goldarah = view.findViewById(R.id.txtGolDarah);
        alamat = view.findViewById(R.id.txtAlamat);
        nohp = view.findViewById(R.id.txtNoHP);
        noktp = view.findViewById(R.id.txtNoKTP);
        nobpjs = view.findViewById(R.id.txtNoBPJS);
        noasuransi = view.findViewById(R.id.txtNoAsuransi);
        tanggallahir = view.findViewById(R.id.txtTanggalLahir);
        penyakitsendiri = view.findViewById(R.id.txtPenyakitSendiri);
        penyakitkeluarga = view.findViewById(R.id.txtPenyakitKeluarga);
        keluhanutama = view.findViewById(R.id.txtKeluhanUtama);
        obat = view.findViewById(R.id.txtObat);
        alergiobat = view.findViewById(R.id.txtAlergiObat);
        alergimakanan = view.findViewById(R.id.txtAlergiMakanan);
        tekanandarah = view.findViewById(R.id.txtTekananDarah);
        guladarah = view.findViewById(R.id.txtGulaDarah);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();


//        DocumentReference documentReference = fStore.collection("Users").document(userId);
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
//                nama.setText(documentSnapshot.getString("name"));
//                email.setText(documentSnapshot.getString("email"));
//                jeniskelamin.setText(documentSnapshot.getString("gender"));
//                goldarah.setText(documentSnapshot.getString("bloodType"));
//                alamat.setText(documentSnapshot.getString("address"));
//                nohp.setText(documentSnapshot.getString("phoneNumber"));
//                noktp.setText(documentSnapshot.getString("KTPNumber"));
//                nobpjs.setText(documentSnapshot.getString("BPJSNumber"));
//                noasuransi.setText(documentSnapshot.getString("insuranceNumber"));
//                tanggallahir.setText(documentSnapshot.getString("birthdate"));
//                penyakitsendiri.setText(documentSnapshot.getString("ownDisease"));
//                penyakitkeluarga.setText(documentSnapshot.getString("geneticDisease"));
//                keluhanutama.setText(documentSnapshot.getString("complaint"));
//                obat.setText(documentSnapshot.getString("medicineIntake"));
//                alergiobat.setText(documentSnapshot.getString("medicineAllergy"));
//                alergimakanan.setText(documentSnapshot.getString("foodAllergy"));
//                tekanandarah.setText(documentSnapshot.getString("bloodPressure"));
//                guladarah.setText(documentSnapshot.getString("bloodSugar"));
//            }
//        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("checking 1", "hi");
                if (dataSnapshot.exists()) {
                    Log.d("checking 2", "hi " + dataSnapshot.child("account").getValue());
                    nama.setText((dataSnapshot.child("name").getValue()).toString());
                    email.setText((dataSnapshot.child("email").getValue()).toString());
                    jeniskelamin.setText((dataSnapshot.child("gender").getValue()).toString());
                    goldarah.setText((dataSnapshot.child("bloodType").getValue()).toString());
                    alamat.setText((dataSnapshot.child("address").getValue()).toString());
                    nohp.setText((dataSnapshot.child("phoneNumber").getValue()).toString());
                    noktp.setText((dataSnapshot.child("ktpnumber").getValue()).toString());
                    nobpjs.setText((dataSnapshot.child("bpjsnumber").getValue()).toString());
                    noasuransi.setText((dataSnapshot.child("insuranceNumber").getValue()).toString());
                    tanggallahir.setText((dataSnapshot.child("birthdate").getValue()).toString());
                    penyakitsendiri.setText((dataSnapshot.child("ownDisease").getValue()).toString());
                    penyakitkeluarga.setText((dataSnapshot.child("geneticDisease").getValue()).toString());
                    keluhanutama.setText((dataSnapshot.child("complaint").getValue()).toString());
                    obat.setText((dataSnapshot.child("medicineIntake").getValue()).toString());
                    alergiobat.setText((dataSnapshot.child("medicineAllergy").getValue()).toString());
                    alergimakanan.setText((dataSnapshot.child("foodAllergy").getValue()).toString());
                    tekanandarah.setText((dataSnapshot.child("bloodPressure").getValue()).toString());
                    guladarah.setText((dataSnapshot.child("bloodSugar").getValue()).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Cannot get info", "Null");
            }
        });


        btnEditAkun = view.findViewById(R.id.btnEditProfile);
        btnEditAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditAkunFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                FirebaseFirestore.getInstance().terminate();
                if (FirebaseAuth.getInstance().getUid() == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }
}
