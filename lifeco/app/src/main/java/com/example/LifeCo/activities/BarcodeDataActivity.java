package com.example.LifeCo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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

    }

    private void initialize() {
        barcodeData_profile_pict = findViewById(R.id.barcodeData_profile_pict);
        barcodeData_nama = findViewById(R.id.barcodeData_nama);

        bundle = getIntent().getExtras();
        if (bundle == null) {
            userID = null;
        } else {
            userID = bundle.getString("userID");
        }

    }
}