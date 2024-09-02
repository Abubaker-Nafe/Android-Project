package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project.databinding.ActivityUserNavigationDrawerBinding;
import com.google.android.material.snackbar.Snackbar;

public class UserNavigationDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityUserNavigationDrawerBinding binding;
    private String userEmail; // To hold the passed user email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the email passed from the login activity
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("user_email");

        binding = ActivityUserNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarUserNavigationDrawer.toolbar);
        binding.appBarUserNavigationDrawer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Setting the user's email in the navigation header
        View headerView = navigationView.getHeaderView(0);
        TextView textViewEmail = headerView.findViewById(R.id.textView);
        textViewEmail.setText(userEmail);

        // Initialize the NavHostFragment and NavController
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_user_navigation_drawer);
        NavController navController = navHostFragment.getNavController();

        // Configure the navigation drawer
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search_flights, R.id.nav_make_reservation,R.id.nav_current_reservations)
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Adding listeners
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavController navController = Navigation.findNavController(UserNavigationDrawer.this, R.id.nav_host_fragment_content_user_navigation_drawer);

                if (item.getItemId() == R.id.nav_make_reservation) {
                    // Pass the email as an argument
                    Bundle bundle = new Bundle();
                    bundle.putString("user_email", userEmail);
                    navController.navigate(R.id.nav_make_reservation, bundle);
                    return true;
                }

                if (item.getItemId() == R.id.nav_current_reservations) {
                    // Pass the email as an argument
                    Bundle bundle = new Bundle();
                    bundle.putString("user_email", userEmail);
                    navController.navigate(R.id.nav_current_reservations, bundle);
                    return true;
                }

                if (item.getItemId() == R.id.nav_logout) {
                    logout();
                    return true;
                }

                return NavigationUI.onNavDestinationSelected(item, navController)
                        || UserNavigationDrawer.super.onOptionsItemSelected(item);
            }
        });
    }

    private void logout() {
        Intent intent = new Intent(UserNavigationDrawer.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user_navigation_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
