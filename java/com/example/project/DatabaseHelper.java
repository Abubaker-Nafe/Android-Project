package com.example.project;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import java.text.*;
import java.util.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "airline.db";
    private static final int DATABASE_VERSION = 3;
    static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Specifying my date format

    // Table names
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_USER = "user";
    public static final String TABLE_FLIGHT = "flight";
    public static final String TABLE_RESERVATIONS = "reservations";

    // User/Admin common columns
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";

    // User only columns
    public static final String COLUMN_PASSPORT_NUMBER = "passport_number";
    public static final String COLUMN_ISSUE_DATE = "passport_issue_date";
    public static final String COLUMN_EXP_DATE = "passport_expiration_date";
    public static final String COLUMN_FOOD_PREF = "food_preference";
    public static final String COLUMN_DATE_BIRTH = "date_of_birth";
    public static final String COLUMN_NATIONALITY = "nationality";


    // Flight columns
    public static final String COLUMN_FLIGHT_NUMBER = "flight_number";
    public static final String COLUMN_DEPARTURE_PLACE = "departure_place";
    public static final String COLUMN_DESTINATION = "destination";
    public static final String COLUMN_DEPARTURE_DATE_TIME = "departure_date_time";
    public static final String COLUMN_ARRIVAL_DATE = "arrival_date";
    public static final String COLUMN_ARRIVAL_TIME = "arrival_time";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_AIRCRAFT_MODEL = "aircraft_model";
    public static final String COLUMN_CURRENT_RESERVATIONS = "current_reservations";
    public static final String COLUMN_MAX_SEATS = "max_seats";
    public static final String COLUMN_MISSED_FLIGHT_COUNT = "missed_flight_count";
    public static final String COLUMN_BOOKING_OPEN_DATE = "booking_open_date";
    public static final String COLUMN_PRICE_ECONOMY = "price_economy";
    public static final String COLUMN_PRICE_BUSINESS = "price_business";
    public static final String COLUMN_PRICE_EXTRA_BAGGAGE = "price_extra_baggage";
    public static final String COLUMN_IS_RECURRENT = "is_recurrent";

    // Reservations Column
    public static final String COLUMN_RESERVATION_ID = "reservation_id";
    public static final String COLUMN_RESERVATION_DATE = "reservation_date";
    public static final String COLUMN_CLASS = "class";
    public static final String COLUMN_EXTRA_BAGS = "extra_bags";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_ID = "user_id";

    // Admin table create statement
    private static final String CREATE_TABLE_ADMIN = "CREATE TABLE "
            + TABLE_ADMIN + "(" + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_FIRST_NAME + " TEXT,"
            + COLUMN_LAST_NAME + " TEXT,"
            + COLUMN_PHONE_NUMBER + " TEXT" + ")";

    // User table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_FIRST_NAME + " TEXT,"
            + COLUMN_LAST_NAME + " TEXT,"
            + COLUMN_PHONE_NUMBER + " TEXT,"
            + "passport_number TEXT,"
            + "passport_issue_date TEXT,"
            + "passport_expiration_date TEXT,"
            + "food_preference TEXT,"
            + "date_of_birth TEXT,"
            + "nationality TEXT" + ")";

    // Flight table create statement
    private static final String CREATE_TABLE_FLIGHT = "CREATE TABLE "
            + TABLE_FLIGHT + "("
            + COLUMN_FLIGHT_NUMBER + " TEXT PRIMARY KEY,"
            + COLUMN_DEPARTURE_PLACE + " TEXT,"
            + COLUMN_DESTINATION + " TEXT,"
            + COLUMN_DEPARTURE_DATE_TIME + " TEXT,"
            + COLUMN_ARRIVAL_DATE + " TEXT,"
            + COLUMN_ARRIVAL_TIME + " TEXT,"
            + COLUMN_DURATION + " TEXT,"
            + COLUMN_AIRCRAFT_MODEL + " TEXT,"
            + COLUMN_CURRENT_RESERVATIONS + " INTEGER,"
            + COLUMN_MAX_SEATS + " INTEGER,"
            + COLUMN_MISSED_FLIGHT_COUNT + " INTEGER,"
            + COLUMN_BOOKING_OPEN_DATE + " TEXT,"
            + COLUMN_PRICE_ECONOMY + " REAL,"
            + COLUMN_PRICE_BUSINESS + " REAL,"
            + COLUMN_PRICE_EXTRA_BAGGAGE + " REAL,"
            + COLUMN_IS_RECURRENT + " TEXT" + ")";

    // Reservations table create statement
    private static final String CREATE_TABLE_RESERVATIONS = "CREATE TABLE "
            + TABLE_RESERVATIONS + "("
            + COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_FLIGHT_NUMBER + " TEXT,"
            + COLUMN_CLASS + " TEXT,"
            + COLUMN_EXTRA_BAGS + " INTEGER,"
            + COLUMN_RESERVATION_DATE + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USER + "(" + COLUMN_EMAIL + "),"
            + "FOREIGN KEY(" + COLUMN_FLIGHT_NUMBER + ") REFERENCES " + TABLE_FLIGHT + "(" + COLUMN_FLIGHT_NUMBER + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ADMIN);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_FLIGHT);
        db.execSQL(CREATE_TABLE_RESERVATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
            db.execSQL(CREATE_TABLE_RESERVATIONS);
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        onCreate(db);
    }

    // Insert new admin
    public long insertAdmin(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        return db.insert(TABLE_ADMIN, null, values);
    }

    // Insert new user
    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        values.put(COLUMN_PASSPORT_NUMBER, user.getPassportNumber());
        values.put(COLUMN_ISSUE_DATE, user.getPassportIssueDate());
        values.put(COLUMN_EXP_DATE, user.getPassportExpirationDate());
        values.put(COLUMN_FOOD_PREF, user.getFoodPreference());
        values.put(COLUMN_DATE_BIRTH, user.getDateOfBirth());
        values.put(COLUMN_NATIONALITY, user.getNationality());
        return db.insert(TABLE_USER, null, values);
    }

    // Validate admin login
    public boolean validateAdmin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_ADMIN, null, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Validate user login
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_USER, null, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Method to insert flight data into the database
    public long insertFlight(Flight flight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FLIGHT_NUMBER, flight.getFlightNumber());
        values.put(COLUMN_DEPARTURE_PLACE, flight.getDeparturePlace());
        values.put(COLUMN_DESTINATION, flight.getDestination());
        values.put(COLUMN_DEPARTURE_DATE_TIME, dateTimeFormat.format(flight.getDepartureDateTime()));
        values.put(COLUMN_ARRIVAL_DATE, flight.getArrivalDate());
        values.put(COLUMN_ARRIVAL_TIME, flight.getArrivalTime());
        values.put(COLUMN_DURATION, flight.getDuration());
        values.put(COLUMN_AIRCRAFT_MODEL, flight.getAircraftModel());
        values.put(COLUMN_CURRENT_RESERVATIONS, flight.getCurrentReservations());
        values.put(COLUMN_MAX_SEATS, flight.getMaxSeats());
        values.put(COLUMN_MISSED_FLIGHT_COUNT, flight.getMissedFlightCount());
        values.put(COLUMN_BOOKING_OPEN_DATE, flight.getBookingOpenDate());
        values.put(COLUMN_PRICE_ECONOMY, flight.getPriceEconomy());
        values.put(COLUMN_PRICE_BUSINESS, flight.getPriceBusiness());
        values.put(COLUMN_PRICE_EXTRA_BAGGAGE, flight.getPriceExtraBaggage());
        values.put(COLUMN_IS_RECURRENT, flight.getIsRecurrent());
        return db.insert(TABLE_FLIGHT, null, values);
    }

    // Method to retrieve flights from the database
    public List<Flight> getFlights() throws ParseException {
        List<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FLIGHT, null, null, null, null, null, COLUMN_DEPARTURE_DATE_TIME + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Flight flight = new Flight();
                flight.setFlightNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_NUMBER)));
                flight.setDeparturePlace(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_PLACE)));
                flight.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION)));
                flight.setDepartureDateTime(dateTimeFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_DATE_TIME))));
                flight.setArrivalDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_DATE)));
                flight.setArrivalTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_TIME)));
                flight.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
                flight.setAircraftModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AIRCRAFT_MODEL)));
                flight.setCurrentReservations(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CURRENT_RESERVATIONS)));
                flight.setMaxSeats(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MAX_SEATS)));
                flight.setMissedFlightCount(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MISSED_FLIGHT_COUNT)));
                flight.setBookingOpenDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_OPEN_DATE)));
                flight.setPriceEconomy(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_ECONOMY)));
                flight.setPriceBusiness(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_BUSINESS)));
                flight.setPriceExtraBaggage(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_EXTRA_BAGGAGE)));
                flight.setIsRecurrent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IS_RECURRENT)));
                flights.add(flight);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return flights;
    }

    // Method to retrieve a flight by its flight number
    public Flight getFlightByNumber(String flightNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FLIGHT, null, COLUMN_FLIGHT_NUMBER + "=?", new String[]{flightNumber}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Flight flight = new Flight();
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

            flight.setFlightNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_NUMBER)));
            flight.setDeparturePlace(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_PLACE)));
            flight.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION)));
            try {
                flight.setDepartureDateTime(dateTimeFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_DATE_TIME))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            flight.setArrivalDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_DATE)));
            flight.setArrivalTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_TIME)));
            flight.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
            flight.setAircraftModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AIRCRAFT_MODEL)));
            flight.setMaxSeats(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MAX_SEATS)));
            flight.setPriceEconomy(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_ECONOMY)));
            flight.setPriceBusiness(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_BUSINESS)));
            flight.setPriceExtraBaggage(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_EXTRA_BAGGAGE)));
            flight.setBookingOpenDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_OPEN_DATE)));

            cursor.close();
            return flight;
        } else {
            return null;  // Flight not found
        }
    }

    // Method to update a flight in the database
    public int updateFlight(Flight flight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        values.put(COLUMN_DEPARTURE_PLACE, flight.getDeparturePlace());
        values.put(COLUMN_DESTINATION, flight.getDestination());
        values.put(COLUMN_DEPARTURE_DATE_TIME, dateTimeFormat.format(flight.getDepartureDateTime()));
        values.put(COLUMN_ARRIVAL_DATE, flight.getArrivalDate());
        values.put(COLUMN_ARRIVAL_TIME, flight.getArrivalTime());
        values.put(COLUMN_DURATION, flight.getDuration());
        values.put(COLUMN_AIRCRAFT_MODEL, flight.getAircraftModel());
        values.put(COLUMN_MAX_SEATS, flight.getMaxSeats());
        values.put(COLUMN_PRICE_ECONOMY, flight.getPriceEconomy());
        values.put(COLUMN_PRICE_BUSINESS, flight.getPriceBusiness());
        values.put(COLUMN_PRICE_EXTRA_BAGGAGE, flight.getPriceExtraBaggage());

        return db.update(TABLE_FLIGHT, values, COLUMN_FLIGHT_NUMBER + "=?", new String[]{flight.getFlightNumber()});
    }

    public int deleteFlight(String flightNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FLIGHT, COLUMN_FLIGHT_NUMBER + " = ?", new String[]{flightNumber});
    }

    public List<Flight> getFlightsByAvailability(boolean available) {
        List<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = available ? COLUMN_BOOKING_OPEN_DATE + " <= ?" : COLUMN_BOOKING_OPEN_DATE + " > ?";

        // Get the current date and time
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String currentDateTime = dateTimeFormat.format(new Date());

        String[] selectionArgs = { currentDateTime };

        Cursor cursor = db.query(TABLE_FLIGHT, null, selection, selectionArgs, null, null, COLUMN_DEPARTURE_DATE_TIME + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Flight flight = new Flight();
                // Populate the flight object as before
                flight.setFlightNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_NUMBER)));
                // Other flight details
                flights.add(flight);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return flights;
    }

    public List<Flight> getArchivedFlights() {
        List<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        String selection = COLUMN_ARRIVAL_DATE + " < ?";
        String[] selectionArgs = { currentDate };

        Cursor cursor = db.query(TABLE_FLIGHT, null, selection, selectionArgs, null, null, COLUMN_ARRIVAL_DATE + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Flight flight = new Flight();
                flight.setFlightNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_NUMBER)));
                flight.setDeparturePlace(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_PLACE)));
                flight.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION)));
                // Parsing and setting other fields as needed
                flight.setDepartureDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_DATE_TIME)).split(" ")[0]);
                flight.setArrivalDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_DATE)));
                flight.setArrivalTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_TIME)));
                flight.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
                flight.setAircraftModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AIRCRAFT_MODEL)));
                flight.setCurrentReservations(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CURRENT_RESERVATIONS)));
                flight.setMaxSeats(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MAX_SEATS)));
                flight.setMissedFlightCount(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MISSED_FLIGHT_COUNT)));
                flight.setBookingOpenDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_OPEN_DATE)));
                flight.setPriceEconomy(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_ECONOMY)));
                flight.setPriceBusiness(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_BUSINESS)));
                flight.setPriceExtraBaggage(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_EXTRA_BAGGAGE)));
                flight.setIsRecurrent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IS_RECURRENT)));
                flights.add(flight);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return flights;
    }

    public List<User> getUsersByFlightNumber(String flightNumber) {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT u.* FROM " + TABLE_USER + " u " +
                "JOIN " + TABLE_RESERVATIONS + " r ON u." + COLUMN_EMAIL + " = r." + COLUMN_USER_EMAIL +
                " WHERE r." + COLUMN_FLIGHT_NUMBER + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{flightNumber});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
                user.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER)));
                user.setPassportNumber(cursor.getString(cursor.getColumnIndexOrThrow("passport_number")));
                user.setPassportIssueDate(cursor.getString(cursor.getColumnIndexOrThrow("passport_issue_date")));
                user.setPassportExpirationDate(cursor.getString(cursor.getColumnIndexOrThrow("passport_expiration_date")));
                user.setFoodPreference(cursor.getString(cursor.getColumnIndexOrThrow("food_preference")));
                user.setDateOfBirth(cursor.getString(cursor.getColumnIndexOrThrow("date_of_birth")));
                user.setNationality(cursor.getString(cursor.getColumnIndexOrThrow("nationality")));
                users.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return users;
    }

    public long insertReservation(String userEmail, String flightNumber, String flightClass, int extraBags, String reservationDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, userEmail);
        values.put(COLUMN_FLIGHT_NUMBER, flightNumber);
        values.put(COLUMN_CLASS, flightClass);
        values.put(COLUMN_EXTRA_BAGS, extraBags);
        values.put(COLUMN_RESERVATION_DATE, reservationDate);

        return db.insert(TABLE_RESERVATIONS, null, values);
    }

    // for user
    public List<Flight> searchFlights(String departureCity, String arrivalCity, String departureDate, String sortBy) throws ParseException {
        List<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query that extracts the date part from departureDateTime and compares it with the provided departureDate
        String selection = COLUMN_DEPARTURE_PLACE + "=? AND " + COLUMN_DESTINATION + "=? AND "
                + "strftime('%Y-%m-%d', " + COLUMN_DEPARTURE_DATE_TIME + ") = ?";
        String[] selectionArgs = {departureCity, arrivalCity, departureDate};
        String orderBy = sortBy.equals("Lowest Cost") ? COLUMN_PRICE_ECONOMY : COLUMN_DURATION;

        Cursor cursor = db.query(TABLE_FLIGHT, null, selection, selectionArgs, null, null, orderBy);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Flight flight = new Flight();
                // Populate flight object from cursor
                flight.setFlightNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_NUMBER)));
                flight.setDeparturePlace(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_PLACE)));
                flight.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION)));
                flight.setDepartureDateTime(dateTimeFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_DATE_TIME))));
                flight.setPriceEconomy(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_ECONOMY)));
                flight.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
                flights.add(flight);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return flights;
    }

    public int updateUserFoodPreference(String userEmail, String foodPreference) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("food_preference", foodPreference);

        return db.update(TABLE_USER, values, COLUMN_EMAIL + "=?", new String[]{userEmail});
    }

    // Method to check if a flight exists in the database
    public boolean isFlightExist(String flightNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FLIGHT, null, COLUMN_FLIGHT_NUMBER + "=?", new String[]{flightNumber}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Method to retrieve the flight prices by flight number
    public double[] getFlightPrices(String flightNumber) {
        double[] prices = new double[3];
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_PRICE_ECONOMY + ", " + COLUMN_PRICE_BUSINESS + ", " + COLUMN_PRICE_EXTRA_BAGGAGE +
                " FROM " + TABLE_FLIGHT +
                " WHERE " + COLUMN_FLIGHT_NUMBER + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{flightNumber});

        if (cursor != null && cursor.moveToFirst()) {
            prices[0] = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_ECONOMY));
            prices[1] = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_BUSINESS));
            prices[2] = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_EXTRA_BAGGAGE));
            cursor.close();
        }
        return prices;
    }

    // Method to retrieve the flight prices, aircraft_model, and departure_date by flight number
    public Object[] getFlightPricesAndDetails(String flightNumber) {
        Object[] flightDetails = new Object[5]; // 0 --> economy, 1 --> business, 2 --> bags, 3 --> aircraft_model, 4 --> departure_date
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_PRICE_ECONOMY + ", " + COLUMN_PRICE_BUSINESS + ", " + COLUMN_PRICE_EXTRA_BAGGAGE +
                ", " + COLUMN_AIRCRAFT_MODEL + ", " + COLUMN_DEPARTURE_DATE_TIME +
                " FROM " + TABLE_FLIGHT +
                " WHERE " + COLUMN_FLIGHT_NUMBER + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{flightNumber});

        if (cursor != null && cursor.moveToFirst()) {
            flightDetails[0] = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_ECONOMY));
            flightDetails[1] = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_BUSINESS));
            flightDetails[2] = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_EXTRA_BAGGAGE));
            flightDetails[3] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AIRCRAFT_MODEL));
            flightDetails[4] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_DATE_TIME));
            cursor.close();
        }
        return flightDetails;
    }

    public void incrementCurrentReservations(String flightNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_FLIGHT +
                " SET " + COLUMN_CURRENT_RESERVATIONS + " = " + COLUMN_CURRENT_RESERVATIONS + " + 1" +
                " WHERE " + COLUMN_FLIGHT_NUMBER + " = ?";
        db.execSQL(query, new String[]{flightNumber});
    }

    // Method to get current reservations for a user
    public List<Flight> getCurrentReservations(String userEmail) throws ParseException {
        List<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the current date
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        String query = "SELECT f.* FROM " + TABLE_FLIGHT + " f " +
                "INNER JOIN " + TABLE_RESERVATIONS + " r ON f." + COLUMN_FLIGHT_NUMBER + " = r." + COLUMN_FLIGHT_NUMBER +
                " WHERE r." + COLUMN_USER_EMAIL + " = ?" +
                " AND f." + COLUMN_ARRIVAL_DATE + " >= ?";

        Cursor cursor = db.rawQuery(query, new String[]{userEmail, currentDate});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Flight flight = new Flight();
                flight.setFlightNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_NUMBER)));
                flight.setDeparturePlace(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_PLACE)));
                flight.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION)));
                flight.setDepartureDateTime(dateTimeFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_DATE_TIME))));
                flight.setArrivalDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_DATE)));
                flight.setArrivalTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_TIME)));
                flight.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
                flight.setAircraftModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AIRCRAFT_MODEL)));
                flight.setPriceEconomy(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_ECONOMY)));
                flight.setPriceBusiness(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_BUSINESS)));
                flight.setPriceExtraBaggage(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_EXTRA_BAGGAGE)));
                flights.add(flight);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return flights;
    }

    // Method to get previous reservations for a user
    public List<Flight> getPreviousReservations(String userEmail) throws ParseException {
        List<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the current date
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        String query = "SELECT f.* FROM " + TABLE_FLIGHT + " f " +
                "INNER JOIN " + TABLE_RESERVATIONS + " r ON f." + COLUMN_FLIGHT_NUMBER + " = r." + COLUMN_FLIGHT_NUMBER +
                " WHERE r." + COLUMN_USER_EMAIL + " = ?" +
                " AND f." + COLUMN_ARRIVAL_DATE + " < ?";

        Cursor cursor = db.rawQuery(query, new String[]{userEmail, currentDate});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Flight flight = new Flight();
                flight.setFlightNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_NUMBER)));
                flight.setDeparturePlace(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_PLACE)));
                flight.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION)));
                flight.setDepartureDateTime(dateTimeFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_DATE_TIME))));
                flight.setArrivalDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_DATE)));
                flight.setArrivalTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_TIME)));
                flight.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
                flight.setAircraftModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AIRCRAFT_MODEL)));
                flight.setPriceEconomy(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_ECONOMY)));
                flight.setPriceBusiness(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_BUSINESS)));
                flight.setPriceExtraBaggage(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_EXTRA_BAGGAGE)));
                flights.add(flight);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return flights;
    }

    // Method to get flights departing the next day
    public List<String> getFlightsDepartingNextDay(String userEmail) {
        List<String> flightNumbers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT r." + COLUMN_FLIGHT_NUMBER + " FROM " + TABLE_RESERVATIONS + " r " +
                "JOIN " + TABLE_FLIGHT + " f ON r." + COLUMN_FLIGHT_NUMBER + " = f." + COLUMN_FLIGHT_NUMBER +
                " WHERE r." + COLUMN_USER_EMAIL + " = ? AND " +
                "date(f." + COLUMN_DEPARTURE_DATE_TIME + ") = date('now', '+1 day')";

        Cursor cursor = db.rawQuery(query, new String[]{userEmail});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                flightNumbers.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_NUMBER)));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return flightNumbers;
    }

    public List<Flight> getClosestFlights() throws ParseException {
        List<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select flights sorted by the closest departure date-time
        String query = "SELECT * FROM " + TABLE_FLIGHT + " WHERE " + COLUMN_DEPARTURE_DATE_TIME + " >= datetime('now') ORDER BY " + COLUMN_DEPARTURE_DATE_TIME + " ASC LIMIT 10";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Flight flight = new Flight();
                flight.setFlightNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_NUMBER)));
                flight.setDeparturePlace(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_PLACE)));
                flight.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION)));
                flight.setDepartureDateTime(dateTimeFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_DATE_TIME))));
                flight.setArrivalDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_DATE)));
                flight.setArrivalTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_TIME)));
                flight.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
                flight.setAircraftModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AIRCRAFT_MODEL)));
                flights.add(flight);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return flights;
    }
    
}

