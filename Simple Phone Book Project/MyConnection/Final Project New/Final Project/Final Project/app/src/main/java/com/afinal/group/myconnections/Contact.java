package com.afinal.group.myconnections;

public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private String note;
    private String company;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String phoneNo, String email, String note, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.note = note;
        this.company = company;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
