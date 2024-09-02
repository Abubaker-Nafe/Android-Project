package com.example.project.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.DatabaseHelper;
import com.example.project.Flight;
import com.example.project.R;

import java.util.List;

public class FlightArchiveFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private ListView listViewArchivedFlights;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flight_archive, container, false);

        dbHelper = new DatabaseHelper(getContext());
        listViewArchivedFlights = root.findViewById(R.id.listViewArchivedFlights);

        // Fetch archived flights and display them
        // the comparison is done using the arrival_date
        List<Flight> archivedFlights = dbHelper.getArchivedFlights();
        ArrayAdapter<Flight> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, archivedFlights);
        listViewArchivedFlights.setAdapter(adapter);

        return root;
    }
}
