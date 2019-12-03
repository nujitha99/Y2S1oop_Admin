package com.nujitha.project.classes;

import java.util.Objects;

public class Car extends Vehicle {
    private int noOfDoors;
    private boolean hybridFlag;
    private String vehicleType;

    public Car(){}

    public Car(int noOfDoors, boolean hybridFlag) {
        this.noOfDoors = noOfDoors;
        this.hybridFlag = hybridFlag;
    }

    public Car(String plateNo, String make, String model, int odoMeter, boolean availability, int noOfDoors, boolean hybridFlag) {
        super(plateNo, make, model, odoMeter, availability);
        this.noOfDoors = noOfDoors;
        this.hybridFlag = hybridFlag;
    }

    //getters and setters
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String getVehicleType() {
        return vehicleType = "Car";
    }

    public int getNoOfDoors() {
        return noOfDoors;
    }

    public void setNoOfDoors(int noOfDoors) {
        this.noOfDoors = noOfDoors;
    }

    public boolean isHybridFlag() {
        return hybridFlag;
    }

    public void setHybridFlag(boolean hybridFlag) {
        this.hybridFlag = hybridFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return noOfDoors == car.noOfDoors &&
                hybridFlag == car.hybridFlag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), noOfDoors, hybridFlag);
    }

    //toString method
    @Override
    public String toString() {
        return  super.toString() + " noOfDoors=" + noOfDoors +
                ", hybridFlag=" + hybridFlag +
                ", vehicleType='" + vehicleType + '\'';
    }
}
