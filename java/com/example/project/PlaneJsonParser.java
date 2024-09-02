package com.example.project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlaneJsonParser {

    static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // date format

    public static List<Flight> getObjectFromJson(String json) {
        List<Flight> flights;
        try {
            JSONArray jsonArray = new JSONArray(json);
            flights = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Flight flight = new Flight();

                flight.setFlightNumber(jsonObject.getString("flight_number"));
                flight.setDeparturePlace(jsonObject.getString("departure_place"));
                flight.setDestination(jsonObject.getString("destination"));


                String departureDateTime = jsonObject.getString("departure_date") + " " + jsonObject.getString("departure_time");
                Date dateTime  = dateTimeFormat.parse(departureDateTime); // Convert String to Date
                flight.setDepartureDateTime(dateTime);

                flight.setArrivalDate(jsonObject.getString("arrival_date"));
                flight.setArrivalTime(jsonObject.getString("arrival_time"));
                flight.setDuration(jsonObject.getString("duration"));
                flight.setAircraftModel(jsonObject.getString("aircraft_model"));
                flight.setCurrentReservations(jsonObject.getInt("current_reservations"));
                flight.setMaxSeats(jsonObject.getInt("max_seats"));
                flight.setMissedFlightCount(jsonObject.getInt("missed_flight_count"));
                flight.setBookingOpenDate(jsonObject.getString("booking_open_date"));
                flight.setPriceEconomy(jsonObject.getDouble("price_economy"));
                flight.setPriceBusiness(jsonObject.getDouble("price_business"));
                flight.setPriceExtraBaggage(jsonObject.getDouble("price_extra_baggage"));
                flight.setIsRecurrent(jsonObject.getString("is_recurrent"));

                flights.add(flight);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }
}