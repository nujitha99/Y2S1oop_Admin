package com.nujitha.project.classes;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface RentalVehicleManager {
    void addVehicle() throws ExecutionException, InterruptedException, IOException;
    void removeVehicle() throws ExecutionException, InterruptedException;
    void printList() throws ExecutionException, InterruptedException;
    void runMenu() throws ExecutionException, InterruptedException, IOException;
    void save();
}
