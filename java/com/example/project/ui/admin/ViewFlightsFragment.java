package com.example.project.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.DatabaseHelper;
import com.example.project.Flight;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class ViewFlightsFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private Spinner spinnerAvailability;
    private ListView listViewFlights;
    private ArrayAdapter<String> listAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_flights, container, false);

        dbHelper = new DatabaseHelper(getContext());
        spinnerAvailability = root.findViewById(R.id.spinnerAvailability);
        listViewFlights = root.findViewById(R.id.listViewFlights);
        Button btnShowFlights = root.findViewById(R.id.btnShowFlights);

        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        listViewFlights.setAdapter(listAdapter);

        btnShowFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFlights();
            }
        });

        return root;
    }

    private void showFlights() {
        String selectedOption = spinnerAvailability.getSelectedItem().toString();
        List<Flight> flights = dbHelper.getFlightsByAvailability(selectedOption.equals("available"));
        List<String> flightNumbers = new ArrayList<>();

        for (Flight flight : flights) {
            flightNumbers.add(flight.getFlightNumber());
        }

        listAdapter.clear();
        listAdapter.addAll(flightNumbers);
        listAdapter.notifyDataSetChanged();
    }
}
