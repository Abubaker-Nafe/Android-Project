package com.example.project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserSignupActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextFirstName, editTextLastName, editTextPhoneNumber;
    private EditText editTextPassportNumber, editTextPassportIssueDate, editTextPassportIssuePlace, editTextPassportExpirationDate, editTextDateOfBirth;
    private Spinner spinnerFoodPreference, spinnerNationality;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        // Additional Fields for user
        editTextPassportNumber = findViewById(R.id.editTextPassportNumber);
        editTextPassportIssueDate = findViewById(R.id.editTextPassportIssueDate);
        editTextPassportIssuePlace = findViewById(R.id.editTextPassportIssuePlace);
        editTextPassportExpirationDate = findViewById(R.id.editTextPassportExpirationDate);
        spinnerFoodPreference = findViewById(R.id.spinnerFoodPreference);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        spinnerNationality = findViewById(R.id.spinnerNationality);

        dbHelper = new DatabaseHelper(this);

        Button btnSignup = findViewById(R.id.btnUserSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        Button btnGoBack = findViewById(R.id.btnUserGoBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(UserSignupActivity.this, LoginActivity.class);
                startActivity(goBack);
            }
        });
    }

    private void signUp() {
        resetFieldColors();  // Reset field colors to default before validation

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        // Additional Fields
        String passportNumber = editTextPassportNumber.getText().toString().trim();
        String passportIssueDate = editTextPassportIssueDate.getText().toString().trim();
        String passportIssuePlace = editTextPassportIssuePlace.getText().toString().trim();
        String passportExpirationDate = editTextPassportExpirationDate.getText().toString().trim();
        String foodPreference = spinnerFoodPreference.getSelectedItem().toString();
        String dateOfBirth = editTextDateOfBirth.getText().toString().trim();
        String nationality = spinnerNationality.getSelectedItem().toString();

        // Validate inputs
        boolean valid = true;

        if (!isValidEmail(email)) {
            editTextEmail.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            editTextPhoneNumber.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (!isValidName(firstName)) {
            editTextFirstName.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Invalid First Name", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (!isValidName(lastName)) {
            editTextLastName.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Invalid Last Name", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (!isValidPassword(password)) {
            editTextPassword.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (valid) {
            User user = new User(email, password, firstName, lastName, phoneNumber, "user",
                    passportNumber, passportIssueDate, passportIssuePlace, passportExpirationDate,
                    foodPreference, dateOfBirth, nationality);
            long result = dbHelper.insertUser(user);
            if (result == -1) {
                Toast.makeText(this, "Registration failed, please try again", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "User Registered Successfully!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetFieldColors() {
        int defaultColor = Color.WHITE;
        editTextEmail.setBackgroundColor(defaultColor);
        editTextPassword.setBackgroundColor(defaultColor);
        editTextConfirmPassword.setBackgroundColor(defaultColor);
        editTextFirstName.setBackgroundColor(defaultColor);
        editTextLastName.setBackgroundColor(defaultColor);
        editTextPhoneNumber.setBackgroundColor(defaultColor);
        editTextPassportNumber.setBackgroundColor(defaultColor);
        editTextPassportIssueDate.setBackgroundColor(defaultColor);
        editTextPassportIssuePlace.setBackgroundColor(defaultColor);
        editTextPassportExpirationDate.setBackgroundColor(defaultColor);
        editTextDateOfBirth.setBackgroundColor(defaultColor);
        spinnerFoodPreference.setBackgroundColor(defaultColor);
        spinnerNationality.setBackgroundColor(defaultColor);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return android.util.Patterns.PHONE.matcher(phoneNumber).matches() && phoneNumber.length() >= 10 && phoneNumber.length() <= 15;
    }

    private boolean isValidName(String name) {
        return name.length() >= 3 && name.length() <= 20;
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,15}$";
        return password.matches(passwordPattern);
    }
}
