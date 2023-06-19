package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.propertymasters.R;
import com.example.propertymasters.SharedPreferenceManager;
import com.example.propertymasters.fragments.FavoritesFragment;
import com.example.propertymasters.fragments.HomeFragment;
import com.example.propertymasters.fragments.ProfileFragment;
import com.example.propertymasters.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Button logoutBtn;

    private int selectedMenuItemId;
    private FrameLayout fragmentContainer;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPreferenceManager.getInstance(getApplicationContext()).isAdmin()) {
            startActivity(new Intent(MainActivity.this, AdminDashboardActivity.class));
            MainActivity.this.finish();
        } else {

            bottomNavigationView = findViewById(R.id.bottom_navigation);
            ;


            fragmentContainer = findViewById(R.id.fragment_container);


            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

                selectedMenuItemId = item.getItemId();
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        loadFragment(new HomeFragment());
                        Toast.makeText(MainActivity.this, "home clicked", Toast.LENGTH_SHORT).show();
                        return true;
//                case R.id.menu_search:
//                    loadFragment(new SearchFragment());
//                    Toast.makeText(MainActivity.this,"search clicked",Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.menu_favorites:
//                    loadFragment(new FavoritesFragment());
//                    Toast.makeText(MainActivity.this,"favs clicked",Toast.LENGTH_SHORT).show();
//                    return true;
                    case R.id.menu_profile:
                        loadFragment(new ProfileFragment());
                        Toast.makeText(MainActivity.this, "profile clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            });

            loadFragment(new HomeFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}