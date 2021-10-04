package com.example.triageappintellisoft.models;

public class Visit {
    private String health_status;
    private String on_diet;
    private int patient_fk;
    private String comments;

    public Visit(String healthStatus, String onDiet, int patientFK, String comments) {
        this.health_status = healthStatus;
        this.on_diet = onDiet;
        this.patient_fk = patientFK;
        this.comments = comments;
    }

    public String getHealthStatus() {
        return health_status;
    }

    public void setHealthStatus(String healthStatus) {
        this.health_status = healthStatus;
    }

    public String getOnDiet() {
        return on_diet;
    }

    public void setOnDiet(String onDiet) {
        this.on_diet = onDiet;
    }

    public int getPatientFK() {
        return patient_fk;
    }

    public void setPatientFK(int patientFK) {
        this.patient_fk = patientFK;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
