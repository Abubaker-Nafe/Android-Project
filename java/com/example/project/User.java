package com.example.project;

public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userType; // "admin" or "passenger"

    // Additional fields for passengers
    private String passportNumber;
    private String passportIssueDate;
    private String passportIssuePlace;
    private String passportExpirationDate;
    private String foodPreference;
    private String dateOfBirth;
    private String nationality;

    // Empty Constructor
    public User() {
    }

    // Constructor for Admins
    public User(String email, String password, String firstName, String lastName, String phoneNumber, String userType) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

    // Constructor for Passengers
    public User(String email, String password, String firstName, String lastName, String phoneNumber, String userType,
                String passportNumber, String passportIssueDate, String passportIssuePlace, String passportExpirationDate,
                String foodPreference, String dateOfBirth, String nationality) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.passportNumber = passportNumber;
        this.passportIssueDate = passportIssueDate;
        this.passportIssuePlace = passportIssuePlace;
        this.passportExpirationDate = passportExpirationDate;
        this.foodPreference = foodPreference;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportIssueDate() {
        return passportIssueDate;
    }

    public void setPassportIssueDate(String passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public String getPassportIssuePlace() {
        return passportIssuePlace;
    }

    public void setPassportIssuePlace(String passportIssuePlace) {
        this.passportIssuePlace = passportIssuePlace;
    }

    public String getPassportExpirationDate() {
        return passportExpirationDate;
    }

    public void setPassportExpirationDate(String passportExpirationDate) {
        this.passportExpirationDate = passportExpirationDate;
    }

    public String getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(String foodPreference) {
        this.foodPreference = foodPreference;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userType='" + userType + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", passportIssueDate='" + passportIssueDate + '\'' +
                ", passportIssuePlace='" + passportIssuePlace + '\'' +
                ", passportExpirationDate='" + passportExpirationDate + '\'' +
                ", foodPreference='" + foodPreference + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
