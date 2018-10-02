package com.example.jacob.locateus.Data;

public class Location {
    String lastOnline;
    double longitude, latitude;

    public Location() {

    }

    public Location(String lastOnline, double longitude, double latitude) {
        this.lastOnline = lastOnline;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
