package com.example.project.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.DatabaseHelper;
import com.example.project.R;
import com.example.project.User;

import java.util.List;

public class ViewReservationsFragment extends Fragment {

    private EditText editTextFlightNumber;
    private Button btnViewReservations;
    private ListView listViewReservations;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_reservations, container, false);

        // Initialize views
        editTextFlightNumber = root.findViewById(R.id.editTextFlightNumber);
        btnViewReservations = root.findViewById(R.id.btnViewReservations);
        listViewReservations = root.findViewById(R.id.listViewReservations);
        dbHelper = new DatabaseHelper(getContext());

        // Set button click listener
        btnViewReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flightNumber = editTextFlightNumber.getText().toString().trim();

                if (flightNumber.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a flight number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Fetch the list of users who made reservations for the specified flight using the reservations table
                List<User> userList = dbHelper.getUsersByFlightNumber(flightNumber);

                if (userList.isEmpty()) {
                    Toast.makeText(getContext(), "No reservations found for this flight", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayAdapter<User> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, userList);
                    listViewReservations.setAdapter(adapter);
                }
            }
        });

        return root;
    }
}
