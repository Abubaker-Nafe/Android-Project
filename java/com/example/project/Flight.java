package com.example.project;

import java.util.Date;

public class Flight {
    private String flightNumber;
    private String departurePlace;
    private String destination;
    private String departureDate;
    private String departureTime;
    private Date departureDateTime;  // Combine date and time for sorting
    private String arrivalDate;
    private String arrivalTime;
    private String duration;
    private String aircraftModel;
    private int currentReservations;
    private int maxSeats;
    private int missedFlightCount;
    private String bookingOpenDate;
    private double priceEconomy;
    private double priceBusiness;
    private double priceExtraBaggage;
    private String isRecurrent;

//    // Constructor
//    public Flight(String flightNumber, String departurePlace, String destination, String departureDate,
//                  String departureTime, String arrivalDate, String arrivalTime, String duration,
//                  String aircraftModel, int currentReservations, int maxSeats, int missedFlightCount,
//                  String bookingOpenDate, double priceEconomy, double priceBusiness,
//                  double priceExtraBaggage, String isRecurrent) {
//        this.flightNumber = flightNumber;
//        this.departurePlace = departurePlace;
//        this.destination = destination;
//        this.departureDate = departureDate;
//        this.departureTime = departureTime;
//        this.arrivalDate = arrivalDate;
//        this.arrivalTime = arrivalTime;
//        this.duration = duration;
//        this.aircraftModel = aircraftModel;
//        this.currentReservations = currentReservations;
//        this.maxSeats = maxSeats;
//        this.missedFlightCount = missedFlightCount;
//        this.bookingOpenDate = bookingOpenDate;
//        this.priceEconomy = priceEconomy;
//        this.priceBusiness = priceBusiness;
//        this.priceExtraBaggage = priceExtraBaggage;
//        this.isRecurrent = isRecurrent;
//    }

    // Getters and Setters
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public int getCurrentReservations() {
        return currentReservations;
    }

    public void setCurrentReservations(int currentReservations) {
        this.currentReservations = currentReservations;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getMissedFlightCount() {
        return missedFlightCount;
    }

    public void setMissedFlightCount(int missedFlightCount) {
        this.missedFlightCount = missedFlightCount;
    }

    public String getBookingOpenDate() {
        return bookingOpenDate;
    }

    public void setBookingOpenDate(String bookingOpenDate) {
        this.bookingOpenDate = bookingOpenDate;
    }

    public double getPriceEconomy() {
        return priceEconomy;
    }

    public void setPriceEconomy(double priceEconomy) {
        this.priceEconomy = priceEconomy;
    }

    public double getPriceBusiness() {
        return priceBusiness;
    }

    public void setPriceBusiness(double priceBusiness) {
        this.priceBusiness = priceBusiness;
    }

    public double getPriceExtraBaggage() {
        return priceExtraBaggage;
    }

    public void setPriceExtraBaggage(double priceExtraBaggage) {
        this.priceExtraBaggage = priceExtraBaggage;
    }

    public String getIsRecurrent() {
        return isRecurrent;
    }

    public void setIsRecurrent(String isRecurrent) {
        this.isRecurrent = isRecurrent;
    }

    // Getters and Setters
    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(Date departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    @Override
    public String toString() {
        return "Flight{" + "\n" +
                "  flightNumber='" + flightNumber + '\'' + "\n" +
                "  departurePlace='" + departurePlace + '\'' + "\n" +
                "  destination='" + destination + '\'' + "\n" +
                "  departureDateTime=" + departureDateTime + "\n" +
                "  arrivalDate='" + arrivalDate + '\'' + "\n" +
                "  arrivalTime='" + arrivalTime + '\'' + "\n" +
                "  duration='" + duration + '\'' + "\n" +
                "  aircraftModel='" + aircraftModel + '\'' + "\n" +
                "  currentReservations=" + currentReservations + "\n" +
                "  maxSeats=" + maxSeats + "\n" +
                "  missedFlightCount=" + missedFlightCount + "\n" +
                "  bookingOpenDate='" + bookingOpenDate + '\'' + "\n" +
                "  priceEconomy=" + priceEconomy + "\n" +
                "  priceBusiness=" + priceBusiness + "\n" +
                "  priceExtraBaggage=" + priceExtraBaggage + "\n" +
                "  isRecurrent='" + isRecurrent + '\'' + "\n" +
                '}' + "\n";
    }


    public String toStringForUser() {
        return "Flight Number: " + flightNumber + "\n" +
                "Departure: " + departurePlace + "\n" +
                "Destination: " + destination + "\n" +
                "Departure Date: " + departureDateTime;
    }


}
