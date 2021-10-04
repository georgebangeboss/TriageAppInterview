package com.example.triageappintellisoft.models;

public class Patient {
    private String first_name;
    private String last_name;
    private String dob;
    private String gender;

    public Patient(String firstName, String lastName, String dob, String gender) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.dob = dob;
        this.gender = gender;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
