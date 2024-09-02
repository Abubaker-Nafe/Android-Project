package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FlightAdapter extends ArrayAdapter<Flight> {
    private Context context;
    private List<Flight> flights;

    public FlightAdapter(Context context, List<Flight> flights) {
        super(context, 0, flights);
        this.context = context;
        this.flights = flights;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Flight flight = flights.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(flight.toString());
        return convertView;
    }
}
