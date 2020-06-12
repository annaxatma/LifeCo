package com.example.LifeCo.fragments;

import android.content.Context;
import android.net.Uri;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lifeco.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;


public class EditAkunFragment extends Fragment {

    Spinner spinnerGolDarah, spinnerJenisKelamin;
    TextInputLayout inpNama, inpEmail, inpPassword, inpAlamat, inpNoHP, inpNoBPJS, inpNoKTP, inpTekananDarah, inpGulaDarah, inpNoAsuransi;
    EditText inpPenyakitSendiri, inpPenyakitKeluarga, inpKeluhanUtama, inpObat, inpAlergiObat, inpAlergiMakanan, inpTanggalLahir;

    Toolbar toolbar;
    Button btnUpdateAkun;

    String userNama, userEmail, userPassword, userAlamat, userNoHP, userNoBPJS, userNoKTP, userTekananDarah, userGulaDarah, userPenyakitSendiri, userPenyakitKeluarga, userKeluhanUtama, userObat, userAlergiObat, userAlergiMakanan, userTanggalLahir, userGolDarah, userJenisKelamin, userNoAsuransi;

    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseFirestore fStore;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbar = getActivity().findViewById(R.id.toolbar_main);
        toolbar.setTitle("Edit Akun");
        return inflater.inflate(R.layout.fragment_edit_akun, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        spinnerGolDarah = view.findViewById(R.id.spinnerGolDarahEdit);

        ArrayAdapter<String> adapterGolDarah = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inpGolDarahGroup));
        adapterGolDarah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGolDarah.setAdapter(adapterGolDarah);

        spinnerJenisKelamin = view.findViewById(R.id.spinnerJenisKelaminEdit);

        ArrayAdapter<String> adapterJenisKelamin = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inpJenisKelaminGroup));
        adapterJenisKelamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisKelamin.setAdapter(adapterJenisKelamin);

        inpNama = view.findViewById(R.id.inpNamaEdit);
        inpEmail = view.findViewById(R.id.inpEmailEdit);
        inpPassword = view.findViewById(R.id.inpPasswordEdit);
        inpAlamat = view.findViewById(R.id.inpAlamatEdit);
        inpNoHP = view.findViewById(R.id.inpNoHPEdit);
        inpTanggalLahir = view.findViewById(R.id.inpTanggalLahirEdit);
        inpNoBPJS = view.findViewById(R.id.inpNoBPJSEdit);
        inpNoKTP = view.findViewById(R.id.inpNoKTPEdit);
        inpTekananDarah = view.findViewById(R.id.inpTekananDarahEdit);
        inpGulaDarah = view.findViewById(R.id.inpGulaDarahEdit);
        inpPenyakitSendiri = view.findViewById(R.id.inpPenyakitSendiriEdit);
        inpPenyakitKeluarga = view.findViewById(R.id.inpPenyakitKeluargaEdit);
        inpKeluhanUtama = view.findViewById(R.id.inpKeluhanUtamaEdit);
        inpObat = view.findViewById(R.id.inpObatEdit);
        inpAlergiObat = view.findViewById(R.id.inpAlergiObatEdit);
        inpAlergiMakanan = view.findViewById(R.id.inpAlergiMakananEdit);
        inpNoAsuransi = view.findViewById(R.id.inpNoAsuransiEdit);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();



        btnUpdateAkun = view.findViewById(R.id.btnUpdateAkun);
        btnUpdateAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userNama = inpNama.getEditText().getText().toString().trim();
                userEmail = inpEmail.getEditText().getText().toString().trim();
                userPassword = inpPassword.getEditText().getText().toString().trim();
                userAlamat = inpAlamat.getEditText().getText().toString().trim();
                userNoHP = inpNoHP.getEditText().getText().toString().trim();
                userNoBPJS = inpNoBPJS.getEditText().getText().toString().trim();
                userNoKTP = inpNoKTP.getEditText().getText().toString().trim();
                userTekananDarah = inpTekananDarah.getEditText().getText().toString().trim();
                userGulaDarah = inpGulaDarah.getEditText().getText().toString().trim();
                userGolDarah = spinnerGolDarah.getSelectedItem().toString();
                userJenisKelamin = spinnerJenisKelamin.getSelectedItem().toString();
                userPenyakitSendiri = inpPenyakitSendiri.getText().toString().trim();
                userPenyakitKeluarga = inpPenyakitKeluarga.getText().toString().trim();
                userKeluhanUtama = inpKeluhanUtama.getText().toString().trim();
                userObat = inpObat.getText().toString().trim();
                userAlergiObat = inpAlergiObat.getText().toString().trim();
                userAlergiMakanan = inpAlergiMakanan.getText().toString().trim();
                userTanggalLahir = inpTanggalLahir.getText().toString().trim();
                userNoAsuransi = inpNoAsuransi.getEditText().getText().toString().trim();
                userId = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userId);
                documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                        if(userNama == null){
                            userNama = documentSnapshot.getString("nama");
                        }
                        if(userEmail.equalsIgnoreCase("")){
                            userEmail = documentSnapshot.getString("email");
                        }
                        if(userPassword == null){
                            userPassword = documentSnapshot.getString("password");
                        }
                        if(userAlamat == null){
                            userAlamat = documentSnapshot.getString("alamat");
                        }
                        if(userNoHP == null){
                            userNoHP = documentSnapshot.getString("noHP");
                        }
                        if(userNoBPJS == null){
                            userNoBPJS = documentSnapshot.getString("noBPJS");
                        }
                        if(userNoKTP == null){
                            userNoKTP = documentSnapshot.getString("noKTP");
                        }
                        if(userTekananDarah == null){
                            userTekananDarah = documentSnapshot.getString("tekananDarah");
                        }
                        if(userGulaDarah == null){
                            userGulaDarah = documentSnapshot.getString("gulaDarah");
                        }
                        if(userGolDarah == null){
                            userGolDarah = documentSnapshot.getString("golDarah");
                        }
                        if(userJenisKelamin == null){
                            userJenisKelamin = documentSnapshot.getString("jenisKelamin");
                        }
                        if(userPenyakitSendiri == null){
                            userPenyakitSendiri = documentSnapshot.getString("penyakitSendiri");
                        }
                        if(userPenyakitKeluarga == null){
                            userPenyakitKeluarga = documentSnapshot.getString("penyakitKeluarga");
                        }
                        if(userKeluhanUtama == null){
                            userKeluhanUtama = documentSnapshot.getString("keluhanUtama");
                        }
                        if(userObat == null){
                            userObat = documentSnapshot.getString("obat");
                        }
                        if(userAlergiObat == null){
                            userAlergiObat = documentSnapshot.getString("alergiObat");
                        }
                        if(userAlergiMakanan == null){
                            userAlergiMakanan = documentSnapshot.getString("alergiMakanan");
                        }
                        if(userTanggalLahir == null){
                            userTanggalLahir = documentSnapshot.getString("tanggalLahir");
                        }
                        if(userNoAsuransi == null){
                            userNoAsuransi = documentSnapshot.getString("noAsuransi");
                        }
                    }
                });

                updateDocument();



            }
        });

    }

    public void updateDocument(){

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(userId);

        Map<String, Object> map = new HashMap<>();

        map.put("nama", userNama);
        map.put("email", userEmail);
        map.put("password", userPassword);
        map.put("alamat", userAlamat);
        map.put("noHP", userNoHP);
        map.put("noBPJS", userNoBPJS);
        map.put("noKTP", userNoKTP);
        map.put("tekananDarah", userTekananDarah);
        map.put("gulaDarah", userGulaDarah);
        map.put("golDarah", userGolDarah);
        map.put("jenisKelamin", userJenisKelamin);
        map.put("penyakitSendiri", userPenyakitSendiri);
        map.put("penyakitKeluarga", userPenyakitKeluarga);
        map.put("keluhanUtama", userKeluhanUtama);
        map.put("obat", userObat);
        map.put("alergiObat", userAlergiObat);
        map.put("alergiMakanan", userAlergiMakanan);
        map.put("tanggalLahir", userTanggalLahir);
        map.put("noAsuransi", userNoAsuransi);

        docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("Update Success", "OnSuccess: Successfully updated data ->" + userId);
                Fragment fragment = new AccountFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Failed to Update", "OnFailure: ", e);
            }
        });
    }
}

