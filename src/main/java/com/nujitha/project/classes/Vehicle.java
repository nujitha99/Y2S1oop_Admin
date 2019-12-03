package com.nujitha.project.classes;

import java.io.Serializable;
import java.util.Objects;

public abstract class Vehicle implements Comparable<Vehicle>, Serializable {
    //class attributes
    private String plateNo;
    private String make;
    private String model;
    private int odoMeter;
    private boolean availability;

    //Constructor
    public Vehicle() {

    }

    public Vehicle(String plateNo, String make, String model, int odoMeter, boolean availability) {
        super();
        this.plateNo = plateNo;
        this.make = make;
        this.model = model;
        this.odoMeter = odoMeter;
        this.availability = availability;
    }

    //getters and setters
    public abstract String getVehicleType();

    public String getPlateNo() {
        return plateNo;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getOdoMeter() {
        return odoMeter;
    }

    public void setOdoMeter(int odoMeter) {
        this.odoMeter = odoMeter;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return odoMeter == vehicle.odoMeter &&
                availability == vehicle.availability &&
                Objects.equals(plateNo, vehicle.plateNo) &&
                Objects.equals(make, vehicle.make) &&
                Objects.equals(model, vehicle.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plateNo, make, model, odoMeter, availability);
    }

    @Override
    public String toString() {
        return "plateNo='" + plateNo + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", odoMeter=" + odoMeter +
                ", availability=" + availability ;
    }

    @Override
    public int compareTo(Vehicle other) {
        return make.compareTo(other.make);
    }

}
