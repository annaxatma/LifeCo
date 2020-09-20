package com.example.LifeCo.activities;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentTransaction;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;

        import com.example.LifeCo.fragments.AccountDriverFragment;
        import com.example.LifeCo.fragments.AccountFragment;
        import com.example.LifeCo.fragments.HistoryFragment;
        import com.example.LifeCo.fragments.HomeFragment;
        import com.example.lifeco.R;
        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Boolean trial = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(getIntent().getStringExtra("whytinton").equalsIgnoreCase("dahell")){
//            trial = true;
//            Log.d("trialtinton", String.valueOf(trial));
//        }

        toolbar = findViewById(R.id.toolbar_main);


        bottomNavigationView = findViewById(R.id.bottom_nav_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){


                    case R.id.menu_history:
                        toolbar.setTitle(R.string.menu_history);
                        setSupportActionBar(toolbar);
                        fragment = new HistoryFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.menu_home:
                        toolbar.setTitle(R.string.menu_home);
                        setSupportActionBar(toolbar);
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.menu_account:
                        toolbar.setTitle(R.string.menu_account);
                        setSupportActionBar(toolbar);
                        Intent i = getIntent();
                        if(i.getStringExtra("account").equalsIgnoreCase("pasien") ){

                            fragment = new AccountFragment();
                            Bundle args = new Bundle();
                            args.putString("akun", "pasien");
                            fragment.setArguments(args);
                        } else {
                            fragment = new AccountDriverFragment();
                            Bundle args = new Bundle();
                            args.putString("akun", "ambulans");
                            fragment.setArguments(args);
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

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
        startActivity(intent);
        finish();

    }
}
