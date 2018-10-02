package com.example.jacob.locateus.Data;

public class User {
    private String email, fullname, password, phoneNumber;


    public User() {
    }

    public User(String email,String fullname, String password, String phoneNumber) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.phoneNumber = phoneNumber;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
