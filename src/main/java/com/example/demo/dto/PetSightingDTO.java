package com.example.demo.dto;

import java.util.UUID;

public class PetSightingDTO {
    private String message;
    private double latitude;
    private double longitude;
    private UUID petId;
    private String userEmail;

    // Getters y setters
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public UUID getPetId() {
        return petId;
    }
    public void setPetId(UUID petId) {
        this.petId = petId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
