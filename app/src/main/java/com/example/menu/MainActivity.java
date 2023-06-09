package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.menu.fragment.BooksFragment;
import com.example.menu.fragment.DrinksFragment;
import com.example.menu.fragment.FoodFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new DrinksFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.action_drinks) {
                    selectedFragment = new DrinksFragment();
                } else if (item.getItemId() == R.id.action_food) {
                    // Replace with your FoodFragment class
                    selectedFragment = new FoodFragment();
                } else if (item.getItemId() == R.id.action_books) {
                    // Replace with your BooksFragment class
                    selectedFragment = new BooksFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
                return true;
            };
}
