package com.example.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.LifeCo.model.Users;
import com.example.lifeco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    Button btnDaftarAkun;
    Spinner spinnerGolDarah, spinnerJenisKelamin;
    TextInputLayout inpNama, inpEmail, inpPassword, inpAlamat, inpNoHP, inpNoBPJS, inpNoKTP, inpTekananDarah, inpGulaDarah, inpNoAsuransi;
    EditText inpPenyakitSendiri, inpPenyakitKeluarga, inpKeluhanUtama, inpObat, inpAlergiObat, inpAlergiMakanan, inpTanggalLahir;

    String userNama, userEmail, userPassword, userAlamat, userNoHP, userNoBPJS, userNoKTP, userTekananDarah, userGulaDarah, userPenyakitSendiri, userPenyakitKeluarga, userKeluhanUtama, userObat, userAlergiObat, userAlergiMakanan, userTanggalLahir, userGolDarah, userJenisKelamin, userNoAsuransi, userId;
    String userID;
    String status, search, imageURL;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseFirestore fStore;

    Users users;

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

        users = new Users();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


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

//                register(userNama, userEmail, userPassword, userAlamat, userNoHP, userNoBPJS, userNoKTP, userTekananDarah, userGulaDarah, userGolDarah, userJenisKelamin, userPenyakitSendiri, userPenyakitKeluarga, userKeluhanUtama, userObat, userAlergiObat, userAlergiMakanan, userTanggalLahir, userNoAsuransi);
//
//                users.setNama(userNama);
//                users.setEmail(userEmail);
//                users.setPassword(userPassword);
//                users.setAlamat(userAlamat);
//                users.setNoHP(userNoHP);
//                users.setNoBPJS(userNoBPJS);
//                users.setNoKTP(userNoKTP);
//                users.setTekananDarah(userTekananDarah);
//                users.setGulaDarah(userGulaDarah);
//                users.setGolDarah(userGolDarah);
//                users.setJenisKelamin(userJenisKelamin);
//                users.setPenyakitSendiri(userPenyakitSendiri);
//                users.setPenyakitKeluarga(userPenyakitKeluarga);
//                users.setKeluhanUtama(userKeluhanUtama);
//                users.setObat(userObat);
//                users.setAlergiObat(userAlergiObat);
//                users.setAlergiMakanan(userAlergiMakanan);
//                users.setTanggalLahir(userTanggalLahir);
//                users.setNoAsuransi(userNoAsuransi);
//
//                reference.push().setValue(users);
//                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//
//                if(userNama.equals("") && userEmail.equals("") && userPassword.equals("") && userAlamat.equals("") && userNoHP.equals("") && userNoBPJS.equals("") && userNoKTP.equals("") && userTekananDarah.equals("") && userGulaDarah.equals("") && userGolDarah.equals("") && userJenisKelamin.equals("") && userPenyakitSendiri.equals("") && userPenyakitKeluarga.equals("") && userKeluhanUtama.equals("") && userObat.equals("") && userAlergiObat.equals("") && userAlergiMakanan.equals("")  && userTanggalLahir.equals("") && userNoAsuransi.equals("")){
//                    btnDaftarAkun.setEnabled(false);
//                } else {
//                    btnDaftarAkun.setEnabled(true);
//                    String activityType = "DaftarAkun";
//
//                    userId = generateUserID();
//                    Log.d("UserID", userId);
//
//                    BackgroundWorker backgroundWorker = new BackgroundWorker(v.getContext());
//                    backgroundWorker.execute(activityType,userNama,userEmail, userPassword, userAlamat, userNoHP, userNoBPJS, userNoKTP, userTekananDarah, userGulaDarah, userGolDarah, userJenisKelamin, userPenyakitSendiri, userPenyakitKeluarga, userKeluhanUtama, userObat, userAlergiObat, userAlergiMakanan, userTanggalLahir, userNoAsuransi, userId);
//
//                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }

                if(TextUtils.isEmpty(userNama)){
                    inpNama.setError("Silahkan isi nama Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userEmail)){
                    inpEmail.setError("Silahkan isi email Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userPassword)){
                    inpPassword.setError("Silahkan isi password Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userAlamat)){
                    inpAlamat.setError("Silahkan isi alamat rumah Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userNoHP)){
                    inpNoHP.setError("Silahkan isi nomor HP Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userNoBPJS)){
                    inpNoBPJS.setError("Silahkan isi nomor BPJS Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userNoKTP)){
                    inpNoKTP.setError("Silahkan isi nomor KTP Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userTekananDarah)){
                    inpTekananDarah.setError("Silahkan isi tekanan darah Anda (paling terakhir).");
                    return;
                }
                if(TextUtils.isEmpty(userGulaDarah)){
                    inpGulaDarah.setError("Silahkan isi gula darah Anda (paling terakhir).");
                    return;
                }
                if(TextUtils.isEmpty(userGolDarah)){
                    Toast.makeText(RegistrationActivity.this, "Pilih golongan darah Anda!", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(userJenisKelamin)){
                    Toast.makeText(RegistrationActivity.this, "Pilih salah satu jenis kelamin!", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(userPenyakitSendiri)){
                    inpPenyakitSendiri.setError("Silahkan isi riwayat penyakit Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userPenyakitKeluarga)){
                    inpPenyakitKeluarga.setError("Silahkan isi riwayat penyakit keluarga Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userKeluhanUtama)){
                    inpKeluhanUtama.setError("Silahkan isi keluhan-keluhan utama Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userObat)){
                    inpObat.setError("Silahkan isi obat-obatan yang pernah Anda konsumsi.");
                    return;
                }
                if(TextUtils.isEmpty(userAlergiObat)){
                    inpAlergiObat.setError("Silahkan isi alergi obat yang Anda ketahui.");
                    return;
                }
                if(TextUtils.isEmpty(userAlergiMakanan)){
                    inpAlergiMakanan.setError("Silahkan isi alergi makanan yang Anda ketahui.");
                    return;
                }
                if(TextUtils.isEmpty(userTanggalLahir)){
                    inpTanggalLahir.setError("Silahkan isi tanggal lahir Anda.");
                    return;
                }
                if(TextUtils.isEmpty(userNoAsuransi)){
                    inpNoAsuransi.setError("Silahkan isi nomor asuransi Anda.");
                    return;
                }

                if(userPassword.length() < 6){
                    Toast.makeText(RegistrationActivity.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                }else{

//                    Firebase Realtime Database:

//                    users.setNama(userNama);
//                    users.setEmail(userEmail);
//                    users.setPassword(userPassword);
//                    users.setAlamat(userAlamat);
//                    users.setNoHP(userNoHP);
//                    users.setNoBPJS(userNoBPJS);
//                    users.setNoKTP(userNoKTP);
//                    users.setTekananDarah(userTekananDarah);
//                    users.setGulaDarah(userGulaDarah);
//                    users.setGolDarah(userGolDarah);
//                    users.setJenisKelamin(userJenisKelamin);
//                    users.setPenyakitSendiri(userPenyakitSendiri);
//                    users.setPenyakitKeluarga(userPenyakitKeluarga);
//                    users.setKeluhanUtama(userKeluhanUtama);
//                    users.setObat(userObat);
//                    users.setAlergiObat(userAlergiObat);
//                    users.setAlergiMakanan(userAlergiMakanan);
//                    users.setTanggalLahir(userTanggalLahir);
//                    users.setNoAsuransi(userNoAsuransi);
//
//                    reference.push().setValue(users);

                    register(userNama, userEmail, userPassword, userAlamat, userNoHP, userNoBPJS, userNoKTP, userTekananDarah, userGulaDarah, userGolDarah, userJenisKelamin, userPenyakitSendiri, userPenyakitKeluarga, userKeluhanUtama, userObat, userAlergiObat, userAlergiMakanan, userTanggalLahir, userNoAsuransi);
                }



            }
        });

    }
    private void register(final String nama, final String email, final String password, final String alamat, final String noHP, final String noBPJS, final String noKTP, final String tekananDarah, final String gulaDarah, final String golDarah, final String jenisKelamin, final String penyakitSendiri, final String penyakitKeluarga, final String keluhanUtama, final String obat, final String alergiObat, final String alergiMakanan, final String tanggalLahir, final String noAsuransi) {

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("Users").document(userID);

                    Map<String, Object> user = new HashMap<>();
                    user.put("nama", nama);
                    user.put("email", email);
                    user.put("password", password);
                    user.put("alamat", alamat);
                    user.put("noHP", noHP);
                    user.put("noBPJS", noBPJS);
                    user.put("noKTP", noKTP);
                    user.put("tekananDarah", tekananDarah);
                    user.put("gulaDarah", gulaDarah);
                    user.put("golDarah", golDarah);
                    user.put("jenisKelamin", jenisKelamin);
                    user.put("penyakitSendiri", penyakitSendiri);
                    user.put("penyakitKeluarga", penyakitKeluarga);
                    user.put("keluhanUtama", keluhanUtama);
                    user.put("obat", obat);
                    user.put("alergiObat", alergiObat);
                    user.put("alergiMakanan", alergiMakanan);
                    user.put("tanggalLahir", tanggalLahir);
                    user.put("noAsuransi", noAsuransi);
                    user.put("imageURL", imageURL);
                    user.put("status", status);
                    user.put("search", search);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("New User","onSuccess: New User Registered for " + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Failed to Register","onFailure: " + e.toString());
                        }
                    });

                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(RegistrationActivity.this, "Tidak dapat mendaftarkan akun.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
