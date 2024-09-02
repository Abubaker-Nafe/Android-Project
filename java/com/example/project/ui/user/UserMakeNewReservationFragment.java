package com.example.project.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.DatabaseHelper;
import com.example.project.Flight;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserMakeNewReservationFragment extends Fragment {

    private String userEmail;
    Spinner FlightClassSpinner, ExtraBagsSpinner, FoodPreferencesSpinner;
    TextView emailTextView , reservationSummaryTextView;
    EditText FlightNumberEditText;
    Button ButtonConfirmReservation;

    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_make_new_reservation, container, false);

        // Initializations
        emailTextView = root.findViewById(R.id.textViewEmail);
        FlightClassSpinner = root.findViewById(R.id.spinnerFlightClass);
        ExtraBagsSpinner = root.findViewById(R.id.spinnerExtraBags);
        FlightNumberEditText = root.findViewById(R.id.textViewFlightNumber);
        FoodPreferencesSpinner = root.findViewById(R.id.spinnerFoodPreferences);
        ButtonConfirmReservation = root.findViewById(R.id.buttonConfirmReservation);
        reservationSummaryTextView = root.findViewById(R.id.textViewReservationSummary); // Reservation summary

        dbHelper = new DatabaseHelper(getContext());

        if (getArguments() != null) {
            userEmail = getArguments().getString("user_email");
        }

        // display the user email on the screen
        emailTextView.setText(userEmail);

        // Handle the Confirm Reservation button click
        ButtonConfirmReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReservation();
            }
        });

        return root;
    }

    private void handleReservation() {
        String flightNumber = FlightNumberEditText.getText().toString().trim();
        String flightClass = FlightClassSpinner.getSelectedItem().toString();
        int extraBags = Integer.parseInt(ExtraBagsSpinner.getSelectedItem().toString());
        String foodPreference = FoodPreferencesSpinner.getSelectedItem().toString();

        // Check if flight number is empty
        if (flightNumber.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a flight number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the flight number exists in the flight table
        if (!dbHelper.isFlightExist(flightNumber)) {
            Toast.makeText(getContext(), "Flight number does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        we need to get the economy price, business price, extra bags prices
        and other details using the flightnumber sent to the database
         */
        // this date
        String reservationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        Object[] flightDetails = dbHelper.getFlightPricesAndDetails(flightNumber);
        double basePrice = flightClass.equalsIgnoreCase("economy") ? (double) flightDetails[0] : (double) flightDetails[1];
        double totalCost = basePrice + (extraBags * (double) flightDetails[2]);
        String aircraftModel = (String) flightDetails[3];
        String departureDateTime = (String) flightDetails[4];

        long daysUntilDeparture = 0;
        try {
            Date departureDate = dateFormat.parse(departureDateTime);
            Date reservationDateParsed = dateFormat.parse(reservationDate);
            long diff = departureDate.getTime() - reservationDateParsed.getTime();
            daysUntilDeparture = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert reservation into the database
        long reservationId = dbHelper.insertReservation(userEmail, flightNumber, flightClass, extraBags, reservationDate);

        if (reservationId > 0) {
            // Increment current reservations for the flight
            dbHelper.incrementCurrentReservations(flightNumber);

            // Update user's food preference if selected
            if (!foodPreference.isEmpty()) {
                dbHelper.updateUserFoodPreference(userEmail, foodPreference);
            }
            // Display the reservation summary
            displayReservationSummary(totalCost, aircraftModel, daysUntilDeparture);
        } else {
            Toast.makeText(getContext(), "Failed to make reservation. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayReservationSummary(double totalCost, String aircraftModel, long daysUntilDeparture) {
        String summary = "Reservation successful!\n" +
                "Aircraft Model: " + aircraftModel + "\n" +
                "Days until Departure: " + daysUntilDeparture + " days\n" +
                "Total Cost: $" + totalCost + "\n" +
                "Thank you for choosing our airline!";
        reservationSummaryTextView.setText(summary);
        reservationSummaryTextView.setVisibility(View.VISIBLE);
    }

}
