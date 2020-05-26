package com.neusoft.contactsapp.entities;

public class Contact {
    private int mID;
    private String mFirstName;
    private String mLastName;
    private String mPhoneNumber;
    public Contact(){}
    public Contact(int ID, String firstName, String lastName, String phoneNumber) {
        mID = ID;
        mFirstName = firstName;
        mLastName = lastName;
        mPhoneNumber = phoneNumber;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "mID=" + mID +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mPhoneNumber='" + mPhoneNumber + '\'' +
                '}';
    }
}
