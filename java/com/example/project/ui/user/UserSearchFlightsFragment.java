package com.example.project.ui.user;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.DatabaseHelper;
import com.example.project.Flight;
import com.example.project.FlightAdapter;
import com.example.project.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserSearchFlightsFragment extends Fragment {

    private Spinner spinnerDepartureCity, spinnerArrivalCity, spinnerSortBy;
    private TextView textViewDepartureDate;
    private ListView listViewFlightResults;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_search_flights, container, false);

        dbHelper = new DatabaseHelper(getContext());

        spinnerDepartureCity = root.findViewById(R.id.spinnerDepartureCity);
        spinnerArrivalCity = root.findViewById(R.id.spinnerArrivalCity);
        spinnerSortBy = root.findViewById(R.id.spinnerSortBy);
        textViewDepartureDate = root.findViewById(R.id.textViewDepartureDate);
        Button buttonSearchFlights = root.findViewById(R.id.buttonSearchFlights);
        listViewFlightResults = root.findViewById(R.id.listViewFlightResults);

        // Spinners
        ArrayAdapter<CharSequence> adapterCities = ArrayAdapter.createFromResource(getContext(),
                R.array.cities_array, android.R.layout.simple_spinner_item);
        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartureCity.setAdapter(adapterCities);
        spinnerArrivalCity.setAdapter(adapterCities);

        ArrayAdapter<CharSequence> adapterSortBy = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        adapterSortBy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortBy.setAdapter(adapterSortBy);

        // Date picker dialog for departure date
        textViewDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Search button click listener
        buttonSearchFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    searchFlights();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Formatting the date to match the database format
                String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                textViewDepartureDate.setText(formattedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void searchFlights() throws ParseException {
        String departureCity = spinnerDepartureCity.getSelectedItem().toString();
        String arrivalCity = spinnerArrivalCity.getSelectedItem().toString();
        String departureDate = textViewDepartureDate.getText().toString();
        String sortBy = spinnerSortBy.getSelectedItem().toString();

        // Check if the selected departure date is before the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date selectedDate = dateFormat.parse(departureDate);
        Date currentDate = new Date(); // Current date

        if (selectedDate.before(dateFormat.parse(dateFormat.format(currentDate)))) {
            Toast.makeText(getContext(), "Please select a date that is not in the past.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Flight> flights = dbHelper.searchFlights(departureCity, arrivalCity, departureDate, sortBy);

        if (flights.isEmpty()) {
            Toast.makeText(getContext(), "No flights found", Toast.LENGTH_SHORT).show();
        }

        FlightAdapter adapter = new FlightAdapter(getContext(), flights);
        listViewFlightResults.setAdapter(adapter);
    }


}
