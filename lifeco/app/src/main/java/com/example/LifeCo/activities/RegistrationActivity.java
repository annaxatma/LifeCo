package com.example.LifeCo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.LifeCo.api.BackgroundWorker;
import com.example.lifeco.R;
import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {

    Button btnDaftarAkun;
    Spinner spinnerGolDarah, spinnerJenisKelamin;
    TextInputLayout inpNama, inpEmail, inpPassword, inpAlamat, inpNoHP, inpNoBPJS, inpNoKTP, inpTekananDarah, inpGulaDarah, inpNoAsuransi;
    EditText inpPenyakitSendiri, inpPenyakitKeluarga, inpKeluhanUtama, inpObat, inpAlergiObat, inpAlergiMakanan, inpTanggalLahir;

    String userNama, userEmail, userPassword, userAlamat, userNoHP, userNoBPJS, userNoKTP, userTekananDarah, userGulaDarah, userPenyakitSendiri, userPenyakitKeluarga, userKeluhanUtama, userObat, userAlergiObat, userAlergiMakanan, userTanggalLahir, userGolDarah, userJenisKelamin, userNoAsuransi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        spinnerGolDarah = findViewById(R.id.spinnerGolDarah);

        ArrayAdapter<String> adapterGolDarah = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inpGolDarahGroup));
        adapterGolDarah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGolDarah.setAdapter(adapterGolDarah);

        spinnerJenisKelamin = findViewById(R.id.spinnerJenisKelamin);

        ArrayAdapter<String> adapterJenisKelamin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inpJenisKelaminGroup));
        adapterJenisKelamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisKelamin.setAdapter(adapterJenisKelamin);

        inpNama = findViewById(R.id.inpNameRegis);
        inpEmail = findViewById(R.id.inpEmailRegis);
        inpPassword = findViewById(R.id.inpPasswordRegis);
        inpAlamat = findViewById(R.id.inpAlamatRegis);
        inpNoHP = findViewById(R.id.inpNoHP);
        inpTanggalLahir = findViewById(R.id.inpTanggalLahirRegis);
        inpNoBPJS = findViewById(R.id.inpNoBPJSRegis);
        inpNoKTP = findViewById(R.id.inpNoKTPRegis);
        inpTekananDarah = findViewById(R.id.inpTekananDarahRegis);
        inpGulaDarah = findViewById(R.id.inpGulaDarah);
        inpPenyakitSendiri = findViewById(R.id.inpPenyakitSendiriRegis);
        inpPenyakitKeluarga = findViewById(R.id.inpPenyakitKeluargaRegis);
        inpKeluhanUtama = findViewById(R.id.inpKeluhanUtamaRegis);
        inpObat = findViewById(R.id.inpObatRegis);
        inpAlergiObat = findViewById(R.id.inpAlergiObatRegis);
        inpAlergiMakanan = findViewById(R.id.inpAlergiMakananRegis);
        inpNoAsuransi = findViewById(R.id.inpNoAsuransiRegis);

        btnDaftarAkun = findViewById(R.id.btnDaftarAkun);
        btnDaftarAkun.setOnClickListener(new View.OnClickListener() {
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
                String activityType = "DaftarAkun";

                BackgroundWorker backgroundWorker = new BackgroundWorker(v.getContext());
                backgroundWorker.execute(activityType,userNama,userEmail, userPassword, userAlamat, userNoHP, userNoBPJS, userNoKTP, userTekananDarah, userGulaDarah, userGolDarah, userJenisKelamin, userPenyakitSendiri, userPenyakitKeluarga, userKeluhanUtama, userObat, userAlergiObat, userAlergiMakanan, userTanggalLahir, userNoAsuransi);

                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }



}
