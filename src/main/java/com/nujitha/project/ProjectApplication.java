package com.nujitha.project;

import com.nujitha.project.classes.RentalVehicleManager;
import com.nujitha.project.classes.WestminsterRentalVehicleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        SpringApplication.run(ProjectApplication.class, args);
        RentalVehicleManager rentalVehicleManager = new WestminsterRentalVehicleManager();
        rentalVehicleManager.runMenu();
    }

}
