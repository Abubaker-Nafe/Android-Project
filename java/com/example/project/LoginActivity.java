package com.example.project;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private CheckBox checkBoxRememberMe;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setProgress(false); // we haven't read API yet
        dbHelper = new DatabaseHelper(this);

        // read from the API and store the data in the flights database
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(LoginActivity.this, dbHelper);
        connectionAsyncTask.execute("https://mocki.io/v1/465b854e-2c25-4e3e-8822-f03b431a60fa");

        // animation for plane (moving diagonal)
        ImageView plane = findViewById(R.id.plane);
        Animation planeAnimation = AnimationUtils.loadAnimation(this, R.anim.plane_move);
        plane.startAnimation(planeAnimation);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        // Load saved email if Remember Me was checked
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedEmail = prefs.getString("email", null);
        if (savedEmail != null) {
            editTextEmail.setText(savedEmail);
            checkBoxRememberMe.setChecked(true);
        }

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        Button btnSignUp = findViewById(R.id.AdminbtnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        Button UserbtnSignUp = findViewById(R.id.UserbtnSignUp);
        UserbtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UserSignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate credentials
        if (dbHelper.validateAdmin(email, password)) {
            // Admin login successful
            Toast.makeText(this, "Admin Login Successful!", Toast.LENGTH_SHORT).show();
            saveEmailToSharedPreferences(email);
            // Since we know it is an admin who logged in, we move the admin to it's activity (Navigation Drawer)
            Intent intent = new Intent(LoginActivity.this, AdminNavigationDrawer.class);
            intent.putExtra("admin_email",email);
            startActivity(intent);
            finish();
        }else if (dbHelper.validateUser(email, password)) {
            // User login successful
            Toast.makeText(this, "User Login Successful!", Toast.LENGTH_SHORT).show();
            saveEmailToSharedPreferences(email);

            // Check for upcoming flights and send notifications
            List<String> upcomingFlights = dbHelper.getFlightsDepartingNextDay(email);
            if (!upcomingFlights.isEmpty()) {
                for (String flightNumber : upcomingFlights) {
                    sendNotificationForFlight(flightNumber);
                }
            }

            // Since we know it is an user who logged in, we move the user to it's activity
            Intent intent = new Intent(LoginActivity.this, UserNavigationDrawer.class);
            intent.putExtra("user_email",email);
            startActivity(intent);
            finish();
        } else {
            // Login failed
            Toast.makeText(this, "Invalid Credentials, User/Admin does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to save email to SharedPreferences
    private void saveEmailToSharedPreferences(String email) {
        if (checkBoxRememberMe.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
            editor.putString("email", email);
            editor.apply(); // save the email in SharedPreferences
        }
    }

    public void setProgress(boolean progress) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void sendNotificationForFlight(String flightNumber) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "flight_reminder_channel";
        String channelName = "Flight Reminder";

        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Flight Reminder")
                .setContentText("Your flight " + flightNumber + " is tomorrow. Please make sure you're prepared.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(flightNumber.hashCode(), builder.build());
    }
}
