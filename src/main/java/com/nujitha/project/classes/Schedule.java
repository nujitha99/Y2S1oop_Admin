package com.nujitha.project.classes;

import java.util.Date;

public class Schedule {
    //class attributes
    private String plateNo;
    private String pickUpDate;
    private String dropOffDate;
    private String customerName;
    private int contactNo;

    //constructor
    public Schedule() {}

    //getters and setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(String dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    @Override
    public String toString() {
        return plateNo + "    " + pickUpDate + "    " + dropOffDate + "    " + customerName;
    }
}
