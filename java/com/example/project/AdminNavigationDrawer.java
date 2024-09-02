package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.databinding.ActivityAdminNavigationDrawerBinding;

public class AdminNavigationDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAdminNavigationDrawerBinding binding;
    private String userEmail; // To hold the passed admin email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the email passed from the login activity
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("admin_email");

        binding = ActivityAdminNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarAdminNavigationDrawer.toolbar);
        binding.appBarAdminNavigationDrawer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Set the user's email in the navigation header
        View headerView = navigationView.getHeaderView(0);
        TextView textViewEmail = headerView.findViewById(R.id.textView);
        textViewEmail.setText(userEmail);

        // Getting the NavHostFragment explicitly
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_admin_navigation_drawer);

        // Ensure navHostFragment is not null
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_CreateNewFlight, R.id.nav_EditFlight, R.id.nav_ViewFlights, R.id.nav_ViewReservations
            , R.id.nav_FlightArchive).setOpenableLayout(drawer).build();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        } else {
            // Handle the case where the fragment is not found (should not happen in normal cases)
            throw new IllegalStateException("NavHostFragment not found");
        }

        // Adding listener for the logout item
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_logout) {
                    logout();
                    return true;
                }

                return NavigationUI.onNavDestinationSelected(item, navHostFragment.getNavController())
                        || AdminNavigationDrawer.super.onOptionsItemSelected(item);
            }
        });
    }


    private void logout() {
        // Clear any user data if necessary and navigate to LoginActivity
        Intent intent = new Intent(AdminNavigationDrawer.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_admin_navigation_drawer);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();
        } else {
            return super.onSupportNavigateUp();
        }
    }
}
