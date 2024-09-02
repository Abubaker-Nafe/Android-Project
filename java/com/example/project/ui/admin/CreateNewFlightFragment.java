package com.example.project.ui.admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateNewFlightFragment extends Fragment {


    private DatabaseHelper dbHelper;
    private EditText flightNumber, departurePlace, destination, departureDate, departureTime, arrivalDate, arrivalTime, maxSeats, priceEconomy, priceBusiness, Duration, Aircraft_model, Price_extra_baggage;
    private Calendar calendar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_new_flight, container, false);
        // initializations
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
        Duration = root.findViewById(R.id.editTextDuration);
        Aircraft_model = root.findViewById(R.id.editTextAircraftModel);
        Price_extra_baggage = root.findViewById(R.id.editTextPriceExtraPackage);

        calendar = Calendar.getInstance();

        departureDate.setOnClickListener(v -> showDatePickerDialog(departureDate));
        departureTime.setOnClickListener(v -> showTimePickerDialog(departureTime));
        arrivalDate.setOnClickListener(v -> showDatePickerDialog(arrivalDate));
        arrivalTime.setOnClickListener(v -> showTimePickerDialog(arrivalTime));

        Button btnCreateFlight = root.findViewById(R.id.btnCreateFlight);
        btnCreateFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createFlight();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return root;
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String selectedDate = sdf.format(calendar.getTime());

                    // Check if selected date is before the current date
                    if (calendar.getTime().before(new Date())) {
                        Toast.makeText(getContext(), "Selected date cannot be before the current date", Toast.LENGTH_SHORT).show();
                    } else {
                        editText.setText(selectedDate);

                        // If this is the arrival date, validate against the departure date
                        if (editText.getId() == R.id.editTextArrivalDate) {
                            validateArrivalDate();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void validateArrivalDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date departure = sdf.parse(departureDate.getText().toString());
            Date arrival = sdf.parse(arrivalDate.getText().toString());

            if (arrival.before(departure)) {
                Toast.makeText(getContext(), "Arrival date cannot be before the departure date", Toast.LENGTH_SHORT).show();
                arrivalDate.setText(""); // Clear the incorrect date
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showTimePickerDialog(final EditText editText) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    editText.setText(sdf.format(calendar.getTime()));
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void createFlight() throws ParseException {
        // تعريفات
        Flight flight = new Flight();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        // inserting data of the new flight into the database
        flight.setFlightNumber(flightNumber.getText().toString());
        flight.setDeparturePlace(departurePlace.getText().toString());
        flight.setDestination(destination.getText().toString());

        //date
        String dateTimeString = departureDate.getText().toString() + " " + departureTime.getText().toString();
        Date departureDateTime = dateTimeFormat.parse(dateTimeString);
        flight.setDepartureDateTime(departureDateTime);

        flight.setArrivalDate(arrivalDate.getText().toString());
        flight.setArrivalTime(arrivalTime.getText().toString());
        flight.setMaxSeats(Integer.parseInt(maxSeats.getText().toString()));
        flight.setPriceEconomy(Double.parseDouble(priceEconomy.getText().toString()));
        flight.setPriceBusiness(Double.parseDouble(priceBusiness.getText().toString()));
        flight.setDuration(Duration.getText().toString());
        flight.setAircraftModel(Aircraft_model.getText().toString());
        flight.setPriceExtraBaggage(Double.parseDouble(Price_extra_baggage.getText().toString()));

        //manually
        flight.setCurrentReservations(0);
        flight.setIsRecurrent("no");
        flight.setMissedFlightCount(0);
        flight.setBookingOpenDate("2024-09-01");

        long result = dbHelper.insertFlight(flight);
        if (result != -1) {
            Toast.makeText(getContext(), "Flight created successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error creating flight. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
