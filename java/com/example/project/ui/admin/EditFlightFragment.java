package com.example.project.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.DatabaseHelper;
import com.example.project.Flight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

    public class EditFlightFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private EditText flightNumber, departurePlace, destination, departureDate, departureTime, arrivalDate, arrivalTime, maxSeats, priceEconomy, priceBusiness, duration, aircraftModel, priceExtraPackage, BookingOpenDate;
    private Button btnSearchFlight, btnUpdateFlight, btnDeleteFlight;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_flight, container, false);

        dbHelper = new DatabaseHelper(getContext());
        flightNumber = root.findViewById(R.id.editTextFlightNumber);
        departurePlace = root.findViewById(R.id.editTextDeparturePlace);
        destination = root.findViewById(R.id.editTextDestination);
        departureDate = root.findViewById(R.id.editTextDepartureDate);
        departureTime = root.findViewById(R.id.editTextDepartureTime);
        arrivalDate = root.findViewById(R.id.editTextArrivalDate);
        arrivalTime = root.findViewById(R.id.editTextArrivalTime);
        maxSeats = root.findViewById(R.id.editTextMaxSeats);
        priceEconomy = root.findViewById(R.id.editTextPriceEconomy);
        priceBusiness = root.findViewById(R.id.editTextPriceBusiness);
        duration = root.findViewById(R.id.editTextDuration);
        aircraftModel = root.findViewById(R.id.editTextAircraftModel);
        priceExtraPackage = root.findViewById(R.id.editTextPriceExtraPackage);
        BookingOpenDate = root.findViewById(R.id.editTextBookingOpenDate);

        // Buttons
        btnSearchFlight = root.findViewById(R.id.btnSearchFlight);
        btnUpdateFlight = root.findViewById(R.id.btnUpdateFlight);
        btnDeleteFlight = root.findViewById(R.id.btnDeleteFlight);


        btnSearchFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFlight();
            }
        });

        btnUpdateFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateFlight();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnDeleteFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFlight();
            }
        });

        return root;
    }

    private void searchFlight() {
        String flightNum = flightNumber.getText().toString().trim();
        Flight flight = dbHelper.getFlightByNumber(flightNum);

        if (flight != null) {
            departurePlace.setText(flight.getDeparturePlace());
            destination.setText(flight.getDestination());
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            departureDate.setText(dateTimeFormat.format(flight.getDepartureDateTime()).split(" ")[0]);
            departureTime.setText(dateTimeFormat.format(flight.getDepartureDateTime()).split(" ")[1]);
            arrivalDate.setText(flight.getArrivalDate());
            arrivalTime.setText(flight.getArrivalTime());
            maxSeats.setText(String.valueOf(flight.getMaxSeats()));
            priceEconomy.setText(String.valueOf(flight.getPriceEconomy()));
            priceBusiness.setText(String.valueOf(flight.getPriceBusiness()));
            duration.setText(flight.getDuration());
            aircraftModel.setText(flight.getAircraftModel());
            priceExtraPackage.setText(String.valueOf(flight.getPriceExtraBaggage()));
            BookingOpenDate.setText(String.valueOf(flight.getBookingOpenDate()));
        } else {
            Toast.makeText(getContext(), "Flight not found", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void updateFlight() throws ParseException {
        Flight flight = new Flight();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        flight.setFlightNumber(flightNumber.getText().toString());
        flight.setDeparturePlace(departurePlace.getText().toString());
        flight.setDestination(destination.getText().toString());

        // Date
        String dateTimeString = departureDate.getText().toString() + " " + departureTime.getText().toString();
        Date departureDateTime = dateTimeFormat.parse(dateTimeString);
        flight.setDepartureDateTime(departureDateTime);

        flight.setArrivalDate(arrivalDate.getText().toString());
        flight.setArrivalTime(arrivalTime.getText().toString());
        flight.setMaxSeats(Integer.parseInt(maxSeats.getText().toString()));
        flight.setPriceEconomy(Double.parseDouble(priceEconomy.getText().toString()));
        flight.setPriceBusiness(Double.parseDouble(priceBusiness.getText().toString()));
        flight.setDuration(duration.getText().toString());
        flight.setAircraftModel(aircraftModel.getText().toString());
        flight.setPriceExtraBaggage(Double.parseDouble(priceExtraPackage.getText().toString()));
        flight.setBookingOpenDate(BookingOpenDate.getText().toString());

        int result = dbHelper.updateFlight(flight);
        if (result > 0) {
            Toast.makeText(getContext(), "Flight updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error updating flight. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFlight() {
        String flightNum = flightNumber.getText().toString().trim();
        int result = dbHelper.deleteFlight(flightNum);

        if (result > 0) {
            Toast.makeText(getContext(), "Flight deleted successfully!", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(getContext(), "Error deleting flight. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        flightNumber.setText("");
        departurePlace.setText("");
        destination.setText("");
        departureDate.setText("");
        departureTime.setText("");
        arrivalDate.setText("");
        arrivalTime.setText("");
        maxSeats.setText("");
        priceEconomy.setText("");
        priceBusiness.setText("");
        duration.setText("");
        aircraftModel.setText("");
        priceExtraPackage.setText("");
    }
}
