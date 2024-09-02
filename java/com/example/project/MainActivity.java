package com.example.project;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        setProgress(false); // we haven't read API yet

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void fillFlights(List<Flight> flights) {
        // Sort the flights by departure date and time
        Collections.sort(flights, new Comparator<Flight>() {
            @Override
            public int compare(Flight f1, Flight f2) {
                return f1.getDepartureDateTime().compareTo(f2.getDepartureDateTime());
            }
        });

        // Get the top 5 closest flights
        List<Flight> topFlights = flights.subList(0, Math.min(flights.size(), 5));

        // Display these flights in the ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        FlightAdapter adapter = new FlightAdapter(this, topFlights);
        listView.setAdapter(adapter);
    }

    public void setProgress(boolean progress) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}

//link to api : "https://mocki.io/v1/02979e2f-71d1-4632-82cb-bcea8e9ddde2"
// new link to api: "https://mocki.io/v1/465b854e-2c25-4e3e-8822-f03b431a60fa"