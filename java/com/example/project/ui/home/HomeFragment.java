package com.example.project.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.DatabaseHelper;
import com.example.project.Flight;
import com.example.project.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the database helper
        dbHelper = new DatabaseHelper(getContext());

        // Fetch the closest flights
        List<Flight> closestFlights = null;
        try {
            closestFlights = dbHelper.getClosestFlights();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Convert the list of flights into a list of maps for the SimpleAdapter
        List<HashMap<String, String>> data = new ArrayList<>();
        for (Flight flight : closestFlights) {
            HashMap<String, String> map = new HashMap<>();
            map.put("flightNumber", flight.getFlightNumber());
            map.put("departurePlace", flight.getDeparturePlace());
            map.put("destination", flight.getDestination());
            map.put("departureDate", flight.getDepartureDateTime().toString());
            data.add(map);
        }

        // Set up the ListView and SimpleAdapter
        ListView listView = root.findViewById(R.id.listViewFlights);
        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                data,
                R.layout.flight_list_item, // This layout should be created to display each flight item
                new String[]{"flightNumber", "departurePlace", "destination", "departureDate"},
                new int[]{R.id.textFlightNumber, R.id.textDeparturePlace, R.id.textDestination, R.id.textDepartureDate}
        );
        listView.setAdapter(adapter);

        return root;
    }
}
