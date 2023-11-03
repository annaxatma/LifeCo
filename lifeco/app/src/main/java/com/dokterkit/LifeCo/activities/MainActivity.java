package com.dokterkit.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.dokterkit.LifeCo.fragments.AccountDriverFragment;
import com.dokterkit.LifeCo.fragments.AccountFragment;
import com.dokterkit.LifeCo.fragments.BarcodeFragment;
import com.dokterkit.LifeCo.fragments.HistoryFragment;
import com.dokterkit.LifeCo.fragments.HomeFragment;
import com.dokterkit.LifeCo.fragments.MaterialFragment;
import com.dokterkit.lifeco.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_main);

        bottomNavigationView = findViewById(R.id.bottom_nav_main);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {

                    case R.id.menu_history:
                        toolbar.setTitle(R.string.menu_history);
                        setSupportActionBar(toolbar);
                        fragment = new HistoryFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.menu_materials:
                        toolbar.setTitle("Materials");
                        setSupportActionBar(toolbar);
                        fragment = new MaterialFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.menu_home:
                        toolbar.setTitle(R.string.menu_home);
                        setSupportActionBar(toolbar);
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.menu_barcode:
                        toolbar.setTitle(R.string.menu_barcode);
                        setSupportActionBar(toolbar);
                        fragment = new BarcodeFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.menu_account:
                        toolbar.setTitle(R.string.menu_account);
                        setSupportActionBar(toolbar);
                        Intent i = getIntent();
                        //what is wrooooong with this oneee
                        if (i.getStringExtra("account").equalsIgnoreCase("pasien")) {
                            fragment = new AccountFragment();
                        } else {
                            fragment = new AccountDriverFragment();
                        }
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main, fragment);
        transaction.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        toolbar.setTitle(R.string.menu_home);
        setSupportActionBar(toolbar);
        bottomNavigationView.setSelectedItemId(R.id.menu_home); /*mulai dimana saat run aplikasi */
//        Fragment fragment = new HomeFragment();
//        loadFragment(fragment);
    }


}
