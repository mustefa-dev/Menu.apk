package com.example.menu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.menu.Permissions.StoragePermissionManager;
import com.example.menu.R;
import com.example.menu.fragment.HomeFragment;
import com.example.menu.fragment.MenuFragment;
import com.example.menu.fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    StoragePermissionManager storagePermissionManager;

    private HomeFragment homeFragment;
    private MenuFragment menuFragment;
    private SettingsFragment settingsFragment;

    private static final int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);

        homeFragment = new HomeFragment();
        menuFragment = new MenuFragment();
        settingsFragment = new SettingsFragment();

        storagePermissionManager = new StoragePermissionManager(this);

        if (!storagePermissionManager.isStoragePermissionGranted()) {
            storagePermissionManager.requestStoragePermission();
        } else {
            showFragment(homeFragment);
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.nav_home) {
                        selectedFragment = homeFragment;
                    } else if (item.getItemId() == R.id.nav_menu) {
                        selectedFragment = menuFragment;
                    } else if (item.getItemId() == R.id.nav_settings) {
                        selectedFragment = settingsFragment;
                    }

                    showFragment(selectedFragment);

                    return true;
                }
            };

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                showFragment(homeFragment);
            } else {
                // Permission denied
            }
        }
    }
}
