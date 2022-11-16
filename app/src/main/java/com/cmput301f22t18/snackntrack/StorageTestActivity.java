package com.cmput301f22t18.snackntrack;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cmput301f22t18.snackntrack.databinding.ActivityStoragefragmenttestingBinding;

import java.util.ArrayList;

public class StorageTestActivity extends AppCompatActivity {

    private ActivityStoragefragmenttestingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStoragefragmenttestingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Storage storage = new Storage();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, StorageFragment.newInstance()).commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_storagefragmenttesting);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}