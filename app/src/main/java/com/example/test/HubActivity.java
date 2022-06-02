package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.test.hubActivityFragments.CategoriesFragment;
import com.example.test.hubActivityFragments.HomeFragment;
import com.example.test.hubActivityFragments.MapFragment;
import com.example.test.hubActivityFragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class HubActivity extends AppCompatActivity {
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_activity);
        id = getIntent().getExtras().getLong("id");
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setItemIconTintList(null);
        bottomNav.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, HomeFragment.newInstance(id)).commit();
    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.main_home:
                    selectedFragment = HomeFragment.newInstance(id);
                    break;
                case R.id.search:
                    selectedFragment = SearchFragment.newInstance(id);
                    break;
                case R.id.categories:
                    selectedFragment = CategoriesFragment.newInstance(id);
                    break;
                case R.id.map_view:
                    selectedFragment = MapFragment.newInstance(id);
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };

}