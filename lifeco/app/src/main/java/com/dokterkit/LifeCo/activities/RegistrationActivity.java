package com.dokterkit.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.dokterkit.LifeCo.model.History;
import com.dokterkit.LifeCo.model.Patient;
import com.dokterkit.LifeCo.model.Users;
import com.dokterkit.lifeco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    Button btnDaftarAkun;
    Spinner spinnerGolDarah, spinnerJenisKelamin;
    TextInputLayout inpNama, inpEmail, inpPassword, inpAlamat, inpNoHP, inpNoBPJS, inpNoKTP, inpTekananDarah, inpGulaDarah, inpNoAsuransi, inpUsername;
    EditText inpPenyakitSendiri, inpPenyakitKeluarga, inpKeluhanUtama, inpObat, inpAlergiObat, inpAlergiMakanan, inpTanggalLahir;
    String tipeakun;

    String userNama, userEmail, userPassword, userAlamat, userNoHP, userNoBPJS, userNoKTP, userTekananDarah, userGulaDarah, userPenyakitSendiri, userPenyakitKeluarga, userKeluhanUtama, userObat, userAlergiObat, userAlergiMakanan, userTanggalLahir, userGolDarah, userJenisKelamin, userNoAsuransi, userUsername;
    String userID;
    String status, search, imageURL;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseFirestore fStore;
    CollectionReference histRef;
    Patient patient;
    Users users;

    //    private DatabaseReference PassengerDatabaseRef;
//    private String OnlinePassengerID;
//    private DatabaseReference DriverDatabaseRef;
//    private String OnlineDriverID;
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
        inpUsername = findViewById(R.id.inpUsernameRegis);


        users = new Users();
        patient = new Patient();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


        btnDaftarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNama = inpNama.getEditText().getText().toString().trim();
                userEmail = inpEmail.getEditText().getText().toString().trim();
                userPassword = inpPassword.getEditText().getText().toString().trim();
//                userAlamat = inpAlamat.getEditText().getText().toString().trim();
//                userNoHP = inpNoHP.getEditText().getText().toString().trim();
//                userNoBPJS = inpNoBPJS.getEditText().getText().toString().trim();
//                userNoKTP = inpNoKTP.getEditText().getText().toString().trim();
//                userTekananDarah = inpTekananDarah.getEditText().getText().toString().trim();
//                userGulaDarah = inpGulaDarah.getEditText().getText().toString().trim();
//                userGolDarah = spinnerGolDarah.getSelectedItem().toString();
//                userJenisKelamin = spinnerJenisKelamin.getSelectedItem().toString();
//                userPenyakitSendiri = inpPenyakitSendiri.getText().toString().trim();
//                userPenyakitKeluarga = inpPenyakitKeluarga.getText().toString().trim();
//                userKeluhanUtama = inpKeluhanUtama.getText().toString().trim();
//                userObat = inpObat.getText().toString().trim();
//                userAlergiObat = inpAlergiObat.getText().toString().trim();
//                userAlergiMakanan = inpAlergiMakanan.getText().toString().trim();
//                userTanggalLahir = inpTanggalLahir.getText().toString().trim();
//                userNoAsuransi = inpNoAsuransi.getEditText().getText().toString().trim();
//                userNoAsuransi = inpNoAsuransi.getEditText().getText().toString().trim();
//                userUsername = inpUsername.getEditText().getText().toString().trim();


                if (TextUtils.isEmpty(userNama)) {
                    inpNama.setError("Silahkan isi nama Anda.");
                    return;
                }
                if (TextUtils.isEmpty(userEmail)) {
                    inpEmail.setError("Silahkan isi email Anda.");
                    return;
                }
                if (TextUtils.isEmpty(userPassword)) {
                    inpPassword.setError("Silahkan isi password Anda.");
                    return;
                }
//                if (TextUtils.isEmpty(userAlamat)) {
//                    inpAlamat.setError("Silahkan isi alamat rumah Anda.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userNoHP)) {
//                    inpNoHP.setError("Silahkan isi nomor HP Anda.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userNoBPJS)) {
//                    inpNoBPJS.setError("Silahkan isi nomor BPJS Anda.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userNoKTP)) {
//                    inpNoKTP.setError("Silahkan isi nomor KTP Anda.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userTekananDarah)) {
//                    inpTekananDarah.setError("Silahkan isi tekanan darah Anda (paling terakhir).");
//                    return;
//                }
//                if (TextUtils.isEmpty(userGulaDarah)) {
//                    inpGulaDarah.setError("Silahkan isi gula darah Anda (paling terakhir).");
//                    return;
//                }
//                if (TextUtils.isEmpty(userGolDarah)) {
//                    Toast.makeText(RegistrationActivity.this, "Pilih golongan darah Anda!", Toast.LENGTH_SHORT).show();
//                }
//                if (TextUtils.isEmpty(userJenisKelamin)) {
//                    Toast.makeText(RegistrationActivity.this, "Pilih salah satu jenis kelamin!", Toast.LENGTH_SHORT).show();
//                }
//                if (TextUtils.isEmpty(userPenyakitSendiri)) {
//                    inpPenyakitSendiri.setError("Silahkan isi riwayat penyakit Anda.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userPenyakitKeluarga)) {
//                    inpPenyakitKeluarga.setError("Silahkan isi riwayat penyakit keluarga Anda.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userKeluhanUtama)) {
//                    inpKeluhanUtama.setError("Silahkan isi keluhan-keluhan utama Anda.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userObat)) {
//                    inpObat.setError("Silahkan isi obat-obatan yang pernah Anda konsumsi.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userAlergiObat)) {
//                    inpAlergiObat.setError("Silahkan isi alergi obat yang Anda ketahui.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userAlergiMakanan)) {
//                    inpAlergiMakanan.setError("Silahkan isi alergi makanan yang Anda ketahui.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userTanggalLahir)) {
//                    inpTanggalLahir.setError("Silahkan isi tanggal lahir Anda.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userNoAsuransi)) {
//                    inpNoAsuransi.setError("Silahkan isi nomor asuransi Anda.");
//                    return;
//                }
//                if (TextUtils.isEmpty(userUsername)) {
//                    inpUsername.setError("Silahkan isi username Anda.");
//                    return;
//                }

                if (userPassword.length() < 6) {
                    Toast.makeText(RegistrationActivity.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                } else {

                    register(userNama, userEmail, userPassword, userAlamat, userNoHP, userNoBPJS, userNoKTP, userTekananDarah, userGulaDarah, userGolDarah, userJenisKelamin, userPenyakitSendiri, userPenyakitKeluarga, userKeluhanUtama, userObat, userAlergiObat, userAlergiMakanan, userTanggalLahir, userNoAsuransi, userUsername);
                }


            }
        });

    }

    private void register(final String nama, final String email, final String password, final String alamat, final String noHP, final String noBPJS, final String noKTP, final String tekananDarah, final String gulaDarah, final String golDarah, final String jenisKelamin, final String penyakitSendiri, final String penyakitKeluarga, final String keluhanUtama, final String obat, final String alergiObat, final String alergiMakanan, final String tanggalLahir, final String noAsuransi, final String username) {

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("Users").document(userID);
                    tipeakun = "patient";
                    Map<String, Object> user = new HashMap<>();
                    user.put("email", email);
                    user.put("password", password);
                    user.put("name", nama);
//                    user.put("gender", jenisKelamin);
//                    user.put("birthdate", tanggalLahir);
//                    user.put("bloodType", golDarah);
//                    user.put("bloodPressure", tekananDarah);
//                    user.put("bloodSugar", gulaDarah);
//                    user.put("medicineIntake", obat);
//                    user.put("foodAllergy", alergiMakanan);
//                    user.put("medicineAllergy", alergiObat);
//                    user.put("address", alamat);
//                    user.put("phoneNumber", noHP);
//                    user.put("KTPNumber", noKTP);
//                    user.put("insuranceNumber", noAsuransi);
//                    user.put("BPJSNumber", noBPJS);
//                    user.put("complaint", keluhanUtama);
//                    user.put("geneticDisease", penyakitKeluarga);
//                    user.put("ownDisease", penyakitSendiri);
                    user.put("search", search);
                    user.put("status", status);
                    user.put("account", tipeakun);
                    user.put("imageURL", "default");
                    user.put("g", "-");

                    user.put("gender", "-");
                    user.put("birthdate", "-");
                    user.put("bloodType", "-");
                    user.put("bloodPressure", "-");
                    user.put("bloodSugar", "-");
                    user.put("medicineIntake", "-");
                    user.put("foodAllergy", "-");
                    user.put("medicineAllergy", "-");
                    user.put("address", "-");
                    user.put("phoneNumber", "-");
                    user.put("KTPNumber", "-");
                    user.put("insuranceNumber", "-");
                    user.put("BPJSNumber", "-");
                    user.put("complaint", "-");
                    user.put("geneticDisease", "-");
                    user.put("ownDisease", "-");

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("New User", "onSuccess: New User Registered for " + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Failed to Register", "onFailure: " + e.toString());
                        }
                    });

                    FirebaseUser firebaseUser = fAuth.getCurrentUser();
                    assert firebaseUser != null;
                    final String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    Patient patient = new Patient();

                    patient.setUid(userid);
                    patient.setName(nama);
                    patient.setEmail(email);
                    patient.setPassword(password);
//                    patient.setAddress(alamat);
//                    patient.setPhoneNumber(noHP);
//                    patient.setBPJSNumber(noBPJS);
//                    patient.setKTPNumber(noKTP);
//                    patient.setBloodPressure(tekananDarah);
//                    patient.setBloodSugar(gulaDarah);
//                    patient.setBloodType(golDarah);
//                    patient.setGender(jenisKelamin);
//                    patient.setOwnDisease(penyakitSendiri);
//                    patient.setGeneticDisease(penyakitKeluarga);
//                    patient.setComplaint(keluhanUtama);
//                    patient.setMedicineIntake(obat);
//                    patient.setMedicineAllergy(alergiObat);
//                    patient.setFoodAllergy(alergiMakanan);
//                    patient.setBirthdate(tanggalLahir);
//                    patient.setInsuranceNumber(noAsuransi);
                    patient.setAccount("patient");
                    patient.setImageURL("default");
                    patient.setStatus("-");
                    patient.setSearch("-");

                    patient.setAddress("-");
                    patient.setPhoneNumber("-");
                    patient.setBPJSNumber("-");
                    patient.setKTPNumber("-");
                    patient.setBloodPressure("-");
                    patient.setBloodSugar("-");
                    patient.setBloodType("-");
                    patient.setGender("-");
                    patient.setOwnDisease("-");
                    patient.setGeneticDisease("-");
                    patient.setComplaint("-");
                    patient.setMedicineIntake("-");
                    patient.setMedicineAllergy("-");
                    patient.setFoodAllergy("-");
                    patient.setBirthdate("-");
                    patient.setInsuranceNumber("-");

                    reference.setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("New User - Realtime", "onSuccess: New User Registered for " + userid);
                            }
                        }
                    });
                    buatAkun();
//                    Intent intent = new Intent(RegistrationActivity.this, RegistrationDriverActivity.class);
//                    String account = "pasien";
//                    intent.putExtra("account", account);
                    Intent intent = new Intent(RegistrationActivity.this, welcome.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegistrationActivity.this, "Tidak dapat mendaftarkan akun.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
//    private void registerRealtime(final String username, String email, String password){
//        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    FirebaseUser firebaseUser = fAuth.getCurrentUser();
//                    assert firebaseUser != null;
//                    final String userid = firebaseUser.getUid();
//
//                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
//
//                    HashMap<String, String> hashMap = new HashMap<>();
//                    hashMap.put("id", userid);
//                    hashMap.put("username", username);
//                    hashMap.put("imageURL", "default");
//                    hashMap.put("search", username.toLowerCase());
//
//                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                Log.d("New User - Realtime","onSuccess: New User Registered for " + userid);
//                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                                finish();
//                            }
//                        }
//                    });
//                }else{
//                    Toast.makeText(RegistrationActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    public void buatAkun() {

        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        assert firebaseUser != null;
        final String userid = firebaseUser.getUid();

        String aktivitas = "Mendaftarkan akun";
        String tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String waktu = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        History history = new History(aktivitas, tanggal, waktu);

        histRef = fStore.collection("Users");

        histRef.document(userid).collection("History").add(history);

//        OnlinePassengerID = fAuth.getCurrentUser().getUid();
//        PassengerDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers").child(OnlinePassengerID);
//        PassengerDatabaseRef.setValue(true);
//
//        OnlineDriverID = fAuth.getCurrentUser().getUid();
//        DriverDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(OnlineDriverID);
//        DriverDatabaseRef.setValue(true);
    }

}
