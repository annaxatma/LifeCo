package com.example.LifeCo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BarcodeDataActivity extends AppCompatActivity {

    private ImageView barcodeData_profile_pict;
    private TextView barcodeData_nama, barcodeData_email, barcodeData_jeniskelamin, barcodeData_goldarah, barcodeData_alamat, barcodeData_nohp, barcodeData_noktp, barcodeData_nobpjs, barcodeData_noasuransi, barcodeData_tanggallahir, barcodeData_penyakitsendiri, barcodeData_penyakitkeluarga, barcodeData_keluhanutama, barcodeData_obat, barcodeData_alergiobat, barcodeData_alergimakanan, barcodeData_tekanandarah, barcodeData_guladarah;

    private String userID;

    private Intent intent;
    private Bundle bundle;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_data);

        initialize();
        setText();

    }

    private void setText() {
        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                barcodeData_nama.setText(documentSnapshot.getString("nama"));
                barcodeData_email.setText(documentSnapshot.getString("email"));
                barcodeData_jeniskelamin.setText(documentSnapshot.getString("jenisKelamin"));
                barcodeData_goldarah.setText(documentSnapshot.getString("golDarah"));
                barcodeData_alamat.setText(documentSnapshot.getString("alamat"));
                barcodeData_nohp.setText(documentSnapshot.getString("noHP"));
                barcodeData_noktp.setText(documentSnapshot.getString("noKTP"));
                barcodeData_nobpjs.setText(documentSnapshot.getString("noBPJS"));
                barcodeData_noasuransi.setText(documentSnapshot.getString("noAsuransi"));
                barcodeData_tanggallahir.setText(documentSnapshot.getString("tanggalLahir"));
                barcodeData_penyakitsendiri.setText(documentSnapshot.getString("penyakitSendiri"));
                barcodeData_penyakitkeluarga.setText(documentSnapshot.getString("penyakitKeluarga"));
                barcodeData_keluhanutama.setText(documentSnapshot.getString("keluhanUtama"));
                barcodeData_obat.setText(documentSnapshot.getString("obat"));
                barcodeData_alergiobat.setText(documentSnapshot.getString("alergiObat"));
                barcodeData_alergimakanan.setText(documentSnapshot.getString("alergiMakanan"));
                barcodeData_tekanandarah.setText(documentSnapshot.getString("tekananDarah"));
                barcodeData_guladarah.setText(documentSnapshot.getString("gulaDarah"));
            }
        });
    }

    private void initialize() {
        barcodeData_profile_pict = findViewById(R.id.barcodeData_profile_pict);
        barcodeData_nama = findViewById(R.id.barcodeData_nama);
        barcodeData_email = findViewById(R.id.barcodeData_email);
        barcodeData_jeniskelamin = findViewById(R.id.barcodeData_jeniskelamin);
        barcodeData_goldarah = findViewById(R.id.barcodeData_goldarah);
        barcodeData_alamat = findViewById(R.id.barcodeData_alamat);
        barcodeData_nohp = findViewById(R.id.barcodeData_nohp);
        barcodeData_noktp = findViewById(R.id.barcodeData_noktp);
        barcodeData_nobpjs = findViewById(R.id.barcodeData_nobpjs);
        barcodeData_noasuransi = findViewById(R.id.barcodeData_noasuransi);
        barcodeData_tanggallahir = findViewById(R.id.barcodeData_tanggallahir);
        barcodeData_penyakitsendiri = findViewById(R.id.barcodeData_penyakitsendiri);
        barcodeData_penyakitkeluarga = findViewById(R.id.barcodeData_penyakitkeluarga);
        barcodeData_keluhanutama = findViewById(R.id.barcodeData_keluhanutama);
        barcodeData_obat = findViewById(R.id.barcodeData_obat);
        barcodeData_alergiobat = findViewById(R.id.barcodeData_alergiobat);
        barcodeData_alergimakanan = findViewById(R.id.barcodeData_alergimakanan);
        barcodeData_tekanandarah = findViewById(R.id.barcodeData_tekanandarah);
        barcodeData_guladarah = findViewById(R.id.barcodeData_guladarah);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        bundle = getIntent().getExtras();
        if (bundle == null) {
            userID = null;
        } else {
            userID = bundle.getString("userID");
        }

    }
}