package com.nujitha.project.classes;

import java.util.Objects;

public class Motorbike extends Vehicle {
    private boolean helmetGiven;
    private String bikeType;
    private String vehicleType;

    public Motorbike(){}

    public Motorbike(boolean helmetGiven, String bikeType) {
        this.helmetGiven = helmetGiven;
        this.bikeType = bikeType;
    }

    public Motorbike(String plateNo, String make, String model, int odoMeter, boolean availability, boolean helmetGiven, String bikeType) {
        super(plateNo, make, model, odoMeter, availability);
        this.helmetGiven = helmetGiven;
        this.bikeType = bikeType;
    }

    @Override
    public String getVehicleType() {
        return vehicleType = "Motorbike";
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isHelmetGiven() {
        return helmetGiven;
    }

    public void setHelmetGiven(boolean helmetGiven) {
        this.helmetGiven = helmetGiven;
    }

    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        this.bikeType = bikeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Motorbike motorbike = (Motorbike) o;
        return helmetGiven == motorbike.helmetGiven &&
                Objects.equals(bikeType, motorbike.bikeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), helmetGiven, bikeType);
    }

    @Override
    public String toString() {
        return super.toString() + " helmetGiven=" + helmetGiven +
                ", bikeType='" + bikeType + '\'' +
                ", vehicleType='" + vehicleType + '\'';
    }
}
