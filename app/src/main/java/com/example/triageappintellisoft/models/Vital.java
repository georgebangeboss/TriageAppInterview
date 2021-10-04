package com.example.triageappintellisoft.models;

public class Vital {
    private double height;
    private double weight;
    private double bmi;
    private int patient_fk;

    public Vital(double height, double weight, double bmi, int patientFK) {
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.patient_fk = patientFK;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public int getPatientFK() {
        return patient_fk;
    }

    public void setPatientFK(int patientFK) {
        this.patient_fk = patientFK;
    }
}
