package com.example.LifeCo.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.lifeco.R;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BarcodeDataActivity extends AppCompatActivity {

    private ImageView barcodeData_profile_pict;
    private TextView barcodeData_nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_data);

        initialize();

    }

    private void initialize() {
        barcodeData_profile_pict = findViewById(R.id.barcodeData_profile_pict);
        barcodeData_nama = findViewById(R.id.barcodeData_nama);
    }
}