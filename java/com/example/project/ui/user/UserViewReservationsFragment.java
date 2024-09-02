package com.example.project.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.DatabaseHelper;
import com.example.project.Flight;
import com.example.project.R;

import java.text.ParseException;
import java.util.List;

public class UserViewReservationsFragment extends Fragment {

    private String userEmail;
    private DatabaseHelper dbHelper;
    private ListView listView;
    private Spinner spinnerReservationType;
    private Button buttonShowReservations;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_current_reservations, container, false);

        // Initializations
        dbHelper = new DatabaseHelper(getContext());
        listView = root.findViewById(R.id.listViewReservations);
        spinnerReservationType = root.findViewById(R.id.spinnerReservationType);
        buttonShowReservations = root.findViewById(R.id.buttonShowReservations);

        if (getArguments() != null) {
            userEmail = getArguments().getString("user_email");
        }

        // Handle button click
        buttonShowReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showReservations();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return root;
    }

    private void showReservations() throws ParseException {
        String selectedType = spinnerReservationType.getSelectedItem().toString();
        List<Flight> flights;

        if (selectedType.equalsIgnoreCase("Current Reservations")) {
            flights = dbHelper.getCurrentReservations(userEmail);
        } else {
            flights = dbHelper.getPreviousReservations(userEmail);
        }

        if(flights != null){
            ArrayAdapter<Flight> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, flights);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(getContext(), "There are no reservations for this user!", Toast.LENGTH_SHORT).show();
        }
    }
}
