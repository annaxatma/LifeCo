package com.example.LifeCo.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class AccountFragment extends Fragment {

    TextView nama, email, jeniskelamin, goldarah, alamat, nohp, noktp, nobpjs, noasuransi, tanggallahir, penyakitsendiri, penyakitkeluarga, keluhanutama, obat, alergiobat, alergimakanan, tekanandarah, guladarah;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                nama.setText(documentSnapshot.getString("nama"));
                email.setText(documentSnapshot.getString("email"));
                jeniskelamin.setText(documentSnapshot.getString("jenisKelamin"));
                goldarah.setText(documentSnapshot.getString("golDarah"));
                alamat.setText(documentSnapshot.getString("alamat"));
                nohp.setText(documentSnapshot.getString("noHP"));
                noktp.setText(documentSnapshot.getString("noKTP"));
                nobpjs.setText(documentSnapshot.getString("noBPJS"));
                noasuransi.setText(documentSnapshot.getString("noAsuransi"));
                tanggallahir.setText(documentSnapshot.getString("tanggalLahir"));
                penyakitsendiri.setText(documentSnapshot.getString("penyakitSendiri"));
                penyakitkeluarga.setText(documentSnapshot.getString("penyakitKeluarga"));
                keluhanutama.setText(documentSnapshot.getString("keluhanUtama"));
                obat.setText(documentSnapshot.getString("obat"));
                alergiobat.setText(documentSnapshot.getString("alergiObat"));
                alergimakanan.setText(documentSnapshot.getString("alergiMakanan"));
                tekanandarah.setText(documentSnapshot.getString("tekananDarah"));
                guladarah.setText(documentSnapshot.getString("gulaDarah"));
            }
        });
    }
}
