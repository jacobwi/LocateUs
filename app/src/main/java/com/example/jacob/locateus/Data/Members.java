package com.example.jacob.locateus.Data;

public class Members {
    private boolean admin;
    private String phoneNumber;

    public Members() {
    }

    public Members(boolean admin, String phoneNumber) {
        this.admin = admin;
        this.phoneNumber = phoneNumber;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
